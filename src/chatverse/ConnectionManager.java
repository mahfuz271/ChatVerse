package chatverse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {

    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream output;

    public static void initializeConnection(String host, int port) throws IOException {
        if (socket == null) {
            socket = new Socket(host, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }
    }

    public static DataInputStream getInput() {
        return input;
    }

    public static DataOutputStream getOutput() {
        return output;
    }

    public static void closeConnection() throws IOException {
        if (socket != null) {
            socket.close();
            socket = null;
            input = null;
            output = null;
        }
    }
}
