package article;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddArticleForm extends JFrame implements ActionListener {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField brandField;

    public AddArticleForm() {
        setTitle("Add New Article");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create labels and text fields for the form
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        JLabel brandLabel = new JLabel("Brand:");
        brandField = new JTextField(20);

        // Create button to submit the form
        JButton addButton = new JButton("Add Article");
        addButton.addActionListener(this);

        // Create panel to hold the form components
        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);
        formPanel.add(brandLabel);
        formPanel.add(brandField);
        formPanel.add(new JLabel()); // Empty label for spacing
        formPanel.add(addButton);

        // Add form panel to frame
        add(formPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Article")) {
            // Get input values from the form fields
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            String brand = brandField.getText();

            // Create an Article object
            Article article = new Article(name, description, price, brand);

            // Send POST request to backend
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String articleJson = objectMapper.writeValueAsString(article);
                sendPostRequest(articleJson);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to serialize article object", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sendPostRequest(String articleJson) {
        // Send POST request to backend
        try {
            String apiUrl = "http://localhost:2018/api/v1/article/add"; // Replace with your backend API URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Write JSON data to request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = articleJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();

            // Handle response
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // Article added successfully
                JOptionPane.showMessageDialog(this, "Article added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Failed to add article
                JOptionPane.showMessageDialog(this, "Failed to add article", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddArticleForm form = new AddArticleForm();
            form.setVisible(true);
        });
    }
}