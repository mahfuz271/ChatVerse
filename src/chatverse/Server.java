
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Server {

    // SHA-256 is used here as a minimal upgrade from plain-text storage for this
    // demo. A production app should use a slow hash like BCrypt or Argon2.
    static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(4501);
        System.out.println("Server is running and waiting for clients...");

        while (true) {
            Socket s = ss.accept();
            System.out.println("Client connected: " + s);

            Thread t = new ClientHandler(s);
            t.start();
        }
    }
}

class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private Connection conn;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.input = new DataInputStream(clientSocket.getInputStream());
        this.output = new DataOutputStream(clientSocket.getOutputStream());

        // Establish database connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatverse", "root", "");
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Receive the operation type (e.g., "register", "login", "sendMessage", "loadMessages")
                String operation = input.readUTF();

                switch (operation) {
                    case "register" ->
                        handleRegister();
                    case "login" ->
                        handleLogin();
                    case "sendMessage" ->
                        handleSendMessage();
                    case "loadMessages" ->
                        handleLoadMessages();
                    default ->
                        output.writeUTF("Invalid operation");
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + clientSocket);
        } finally {
            try {
                clientSocket.close();
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException | SQLException e) {
            }
        }
    }

    private void handleRegister() throws IOException {
        try {
            String username = input.readUTF();
            String password = input.readUTF();

            // Check if username already exists
            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                output.writeUTF("Username already taken.");
            } else {
                // Insert new user
                query = "INSERT INTO user (username, password) VALUES (?, ?)";
                pst = conn.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, Server.hashPassword(password));
                pst.executeUpdate();

                output.writeUTF("success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output.writeUTF("Error: " + e.getMessage());
        }
    }

    private void handleLogin() throws IOException {
        try {
            String username = input.readUTF();
            String password = input.readUTF();

            // Validate login
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, Server.hashPassword(password));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                output.writeUTF("success");
            } else {
                output.writeUTF("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            output.writeUTF("Error: " + e.getMessage());
        }
    }

    private void handleSendMessage() throws IOException {
        try {
            String sender = input.readUTF();
            String receiver = input.readUTF();
            String message = input.readUTF();

            // Insert the message into the database
            String query = "INSERT INTO chat (user1, user2, text, first_msg) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, sender);
            pst.setString(2, receiver);
            pst.setString(3, message);
            pst.setInt(4, 0);  // 0 means it's not the first message
            pst.executeUpdate();

            output.writeUTF("Message sent successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            output.writeUTF("Error sending message: " + e.getMessage());
        }
    }

    private void handleLoadMessages() throws IOException {
        try {
            String sender = input.readUTF();
            String receiver = input.readUTF();

            // Query the database to retrieve messages between the sender and receiver
            String query = "SELECT * FROM chat WHERE (user1 = ? AND user2 = ?) OR (user1 = ? AND user2 = ?) ORDER BY id ASC";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, sender);
            pst.setString(2, receiver);
            pst.setString(3, receiver);
            pst.setString(4, sender);
            ResultSet rs = pst.executeQuery();

            boolean messagesFound = false;
            while (rs.next()) {
                String senderName = rs.getString("user1");
                String message = rs.getString("text");
                output.writeUTF(senderName + ": " + message);
                messagesFound = true;
            }

            if (!messagesFound) {
                output.writeUTF("No messages found.");
            }

            // Indicate the end of the message list
            output.writeUTF("END");
        } catch (SQLException e) {
            e.printStackTrace();
            output.writeUTF("Error loading messages: " + e.getMessage());
        }
    }
}
