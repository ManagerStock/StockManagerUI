package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteUser extends JFrame {
    private JTextField userIdField;

    public DeleteUser() {
        setTitle("Delete User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create label and text field for user ID
        JLabel userIdLabel = new JLabel("User ID:");
        userIdField = new JTextField(15);

        // Create delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(userIdLabel);
        panel.add(userIdField);
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(deleteButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    private void deleteUser() {
        String userId = userIdField.getText();
        try {
            URL url = new URL("http://localhost:2018/api/v1/user/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                JOptionPane.showMessageDialog(this, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                userIdField.setText(""); // Clear the text field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send DELETE request", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
