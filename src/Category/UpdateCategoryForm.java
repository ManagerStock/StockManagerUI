package Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Category;

public class UpdateCategoryForm extends JFrame implements ActionListener {
    private long categoryId; // Variable to store the category ID
    private CategoryTable categoryTable;
    private JTextField nameField;
    private JTextField descriptionField;

    public UpdateCategoryForm(long categoryId, CategoryTable categoryTable) {
        this.categoryId = categoryId;
        this.categoryTable = categoryTable;

        setTitle("Update Category");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Create labels and fields
        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");

        // Initialize text fields
        nameField = new JTextField(20);
        descriptionField = new JTextField(20);

        // Create submit button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(descriptionLabel);
        panel.add(descriptionField);

        panel.add(updateButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Fetch category data using the category ID
        fetchCategoryData();
    }

    private void fetchCategoryData() {
        try {
            URL url = new URL("http://localhost:2018/api/v1/category/" + categoryId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode categoryNode = mapper.readTree(response.toString());

            // Extract category details from JSON
            String name = categoryNode.get("name").asText();
            String description = categoryNode.get("description").asText();

            // Set retrieved category data to the fields
            nameField.setText(name);
            descriptionField.setText(description);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch category details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Update".equals(e.getActionCommand())) {
            try {
                // Create a new Category object with updated data
                Category updatedCategory = new Category(nameField.getText(), descriptionField.getText());

                // Send PUT request to update the category
                if (sendPutRequest(updatedCategory)) {
                    JOptionPane.showMessageDialog(this, "Category updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    categoryTable.refreshTableData();
                    dispose(); // Close the form after updating category
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error updating category. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendPutRequest(Category updatedCategory) {
        try {
            URL url = new URL("http://localhost:2018/api/v1/category/update/" + categoryId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            String categoryJson = objectMapper.writeValueAsString(updatedCategory);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = categoryJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update category", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to send PUT request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}