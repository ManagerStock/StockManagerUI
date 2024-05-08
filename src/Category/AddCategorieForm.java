package Category;

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
import model.Article;
import model.Category;

public class AddCategorieForm extends JPanel implements ActionListener {
    private JTextField nameField = new JTextField(20);
    private JTextField descriptionField = new JTextField(20);
    private CategoryTable categoryTable;
    private CategoryPanel categoryPanel;
    public AddCategorieForm(CategoryTable categoryTable, CategoryPanel categoryPanel) {
        this.categoryTable = categoryTable;
        this.categoryPanel = categoryPanel;
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(new Color(245, 245, 245)); // Light grey

        addField("Name:", nameField);
        addField("Description:", descriptionField);

        JButton addButton = new JButton("Add Category");
        addButton.setBackground(new Color(40, 167, 69)); // Green
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(this);
        add(new JLabel("")); // Placeholder for grid alignment
        add(addButton);
    }

    private void addField(String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(33, 37, 41)); // Dark grey
        add(label);
        add(textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Add Category".equals(e.getActionCommand())) {
            try {
                Category category = new Category(
                        nameField.getText(),
                        descriptionField.getText()
                );
                ObjectMapper objectMapper = new ObjectMapper();
                String categoryJson = objectMapper.writeValueAsString(category);
                if (sendPostRequest(categoryJson)) {
                    JOptionPane.showMessageDialog(this, "Category added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    categoryTable.refreshTableData();
                    categoryPanel.categoryAdded();
                }
            } catch (NumberFormatException | JsonProcessingException ex) {
                JOptionPane.showMessageDialog(this, "Error adding category. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendPostRequest(String categoryJson) {
        try {
            String apiUrl = "http://localhost:2018/api/v1/category/add";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = categoryJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}