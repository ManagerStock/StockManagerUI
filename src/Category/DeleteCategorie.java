package Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteCategorie extends JPanel implements ActionListener {
    private JTextField categoryIdField;
    private CategoryPanel categoryPanel; // Reference to CategoryPanel for callback

    public DeleteCategorie(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;

        // Create label and text field for category ID
        JLabel categoryIdLabel = new JLabel("Category ID:");
        categoryIdField = new JTextField(15);

        // Create delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(categoryIdLabel);
        panel.add(categoryIdField);
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(deleteButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Delete".equals(e.getActionCommand())) {
            deleteCategory();
        }
    }

    private void deleteCategory() {
        String categoryId = categoryIdField.getText();
        try {
            URL url = new URL("http://localhost:2018/api/v1/category/delete/" + categoryId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                JOptionPane.showMessageDialog(this, "Category deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                categoryIdField.setText(""); // Clear the text field after successful deletion
                categoryPanel.categoryDeleted(); // Notify CategoryPanel about the deletion
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete category", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send DELETE request", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}