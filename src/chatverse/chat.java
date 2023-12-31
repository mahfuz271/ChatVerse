package chatverse;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.Timer;

public class chat extends javax.swing.JFrame {

    PreparedStatement pst = null;
    String username = null;
    Connection conn = null;
    DefaultListModel chatlist = new DefaultListModel();
    DefaultListModel messages = new DefaultListModel();
    DefaultListModel users = new DefaultListModel();
    String sendTo = null;
    private int selectedIndex = -1;

    /**
     * Creates new form chat
     *
     * @param user
     */
    public chat(String user) {
        username = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatlist = new DefaultListModel();
        users = new DefaultListModel();
        conn = DB.ConnectDb();

        updateListModel();
        Timer timer = new Timer(1500, (ActionEvent e) -> {
            updateListModel();
        });

        timer.start();

        initComponents();
        displaywhenSender();
        startChat();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();

        setLocation(size.width
                / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);
    }

    private void displaywhenSender() {
        if (sendTo != null) {
            chatname.setText("You are chatting with: " + sendTo);
            chatname.setVisible(true);
            //chatArea.setVisible(true);
            jButton3.setVisible(true);
            msg.setVisible(true);
        } else {
            chatname.setVisible(false);
            //chatArea.setVisible(false);
            jButton3.setVisible(false);
            msg.setVisible(false);
        }
    }

    private void updateListModel() {

        //all user load
        String sql = "select * from user";
        try {
            ResultSet rec = conn.prepareStatement(sql).executeQuery();
            users.clear();
            while ((rec != null) && (rec.next())) {
                users.addElement(rec.getString("username"));
            }
            if (users.size() < 1) {
                users.addElement("No User Found.");
            }
        } catch (HeadlessException | SQLException e1) {
            JOptionPane.showMessageDialog(null, e1);
        }

        //chat list load
        sql = "select * from chat where (user1='" + username + "' or user2='" + username + "') and first_msg = 1";
        try {
            ResultSet rec = conn.prepareStatement(sql).executeQuery();
            chatlist.clear();
            while ((rec != null) && (rec.next())) {
                if (rec.getString("user2").equals(username)) {
                    chatlist.addElement(rec.getString("user1"));
                } else if (rec.getString("user1").equals(username)) {
                    chatlist.addElement(rec.getString("user2"));
                }
            }
            if (chatlist.size() < 1) {
                chatlist.addElement("No Chat Found.");
            } else {
                // Restore selected index
                if (selectedIndex >= 0 && selectedIndex < chatlist.size()) {
                    jList1.setSelectedIndex(selectedIndex);
                }
            }
        } catch (HeadlessException | SQLException e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
        loadmessages();
    }

    private void loadmessages() {
        //messages load
        if (sendTo != null) {
            displaywhenSender();
            String sql = "select * from chat where ((user1='" + sendTo + "' and user2='" + username + "') or (user1='" + username + "' and user2='" + sendTo + "')) order by id asc";
            try {
                ResultSet rec = conn.prepareStatement(sql).executeQuery();
                messages.clear();
                while ((rec != null) && (rec.next())) {
                    if (rec.getString("user1").equals(username)) {
                        messages.addElement("You: " + rec.getString("text"));
                    } else {
                        messages.addElement(rec.getString("user1") + ": " + rec.getString("text"));
                    }
                }
                if (messages.size() < 1) {
                    messages.addElement("New Chat.");
                } else {

                    // Auto-scroll down to the latest item
                    int lastIndex = messages.size() - 1;
                    if (lastIndex >= 0) {
                        chatArea.ensureIndexIsVisible(lastIndex);
                    }
                }
            } catch (HeadlessException | SQLException e2) {
                JOptionPane.showMessageDialog(null, e2);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        userlist = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JList<>();
        chatname = new javax.swing.JLabel();
        msg = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        jLabel5.setText("ID: 221015038");

        jDialog1.setMinimumSize(new java.awt.Dimension(258, 536));

        userlist.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        userlist.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        userlist.setModel(users);
        userlist.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        userlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userlistMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(userlist);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("All Users");

        jLabel2.setText("Click on the username to start chat");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel1))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jDialog1.setLocationRelativeTo(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 550));

        jList1.setBackground(new java.awt.Color(0, 122, 255));
        jList1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jList1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setModel(chatlist);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setBackground(new java.awt.Color(0, 122, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("New Chat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Trajan Pro", 1, 18)); // NOI18N
        jLabel6.setText("ChatVerse");

        jLabel7.setText("ID: 221015038");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Welcome, "+username);

        jButton2.setBackground(new java.awt.Color(153, 0, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        chatArea.setBackground(new java.awt.Color(248, 249, 251));
        chatArea.setBorder(null);
        chatArea.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        chatArea.setModel(messages);
        chatArea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(chatArea);

        chatname.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        msg.setBackground(new java.awt.Color(235, 235, 235));
        msg.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        msg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 122, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("SEND");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chatname)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(msg)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jLabel8))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(chatname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        String selected = (String) jList1.getSelectedValue();
        if ("No Chat Found.".equals(selected)) {
            sendTo = null;
        } else {
            selectedIndex = jList1.getSelectedIndex();
            sendTo = selected;
        }
        startChat();
    }//GEN-LAST:event_jList1MouseClicked

    private void startChat() {
        messages.clear();
        messages.addElement("Realtime Chat Application.");
        displaywhenSender();
        loadmessages();
    }

    private void userlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userlistMouseClicked
        String selected = (String) userlist.getSelectedValue();
        sendTo = selected;
        jDialog1.setVisible(false);
        startChat();
    }//GEN-LAST:event_userlistMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Account s = new Account();
        s.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String sql = "select * from chat where ((user1='" + sendTo + "' and user2='" + username + "') or (user1='" + username + "' and user2='" + sendTo + "')) and first_msg = 1";
        try {
            String first = "1";
            boolean allok = true;
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                first = "0";
            }

            if ("".equals(msg.getText())) {
                JOptionPane.showMessageDialog(null, "Please Enter Message");
                allok = false;
            }

            if (allok) {
                String sql2 = "insert into chat(user1, user2, text, first_msg) values(?,?,?,?)";
                pst = conn.prepareStatement(sql2);
                pst.setString(1, username);
                pst.setString(2, sendTo);
                pst.setString(3, msg.getText());
                pst.setString(4, first);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Sent.");
                msg.setText("");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void msgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msgActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> chatArea;
    private javax.swing.JLabel chatname;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField msg;
    private javax.swing.JList<String> userlist;
    // End of variables declaration//GEN-END:variables
}
