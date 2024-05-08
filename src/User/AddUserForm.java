package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

public class AddUserForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, addressField, phoneNumberField, dateOfBirthField;
    private UserTable userTable; // Reference to the UserTable panel

    public AddUserForm(UserTable userTable) {
        this.userTable = userTable; // Set the reference to the UserTable panel
        setTitle("Add User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create labels and fields
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField(20);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(20);

        JLabel dateOfBirthLabel = new JLabel("Date of Birth (YYYY-MM-DD):");
        dateOfBirthField = new JTextField(20);

        // Create submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(firstNameLabel);
        panel.add(firstNameField);

        panel.add(lastNameLabel);
        panel.add(lastNameField);

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(addressLabel);
        panel.add(addressField);

        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);

        panel.add(dateOfBirthLabel);
        panel.add(dateOfBirthField);

        // Add panel and button to the frame
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void addUser() {
        try {
            User user = new User(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    phoneNumberField.getText(),
                    dateOfBirthField.getText() // Assuming date of birth is in text format
            );

            // Convert User object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(user);

            // Send POST request
            if (sendPostRequest(userJson)) {
                JOptionPane.showMessageDialog(this, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Reset fields after successful addition
                firstNameField.setText("");
                lastNameField.setText("");
                emailField.setText("");
                addressField.setText("");
                phoneNumberField.setText("");
                dateOfBirthField.setText("");
                // Refresh the user table
                userTable.refreshTableData();
                dispose();
            }
        } catch (JsonProcessingException ex) {
            JOptionPane.showMessageDialog(this, "Error processing user data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean sendPostRequest(String userJson) {
        try {
            String apiUrl = "http://localhost:2018/api/v1/user/user"; // Change this URL to match your backend endpoint
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = userJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

}