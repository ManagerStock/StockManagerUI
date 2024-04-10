package Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteCategorie extends JPanel implements ActionListener {
    private JTextField idField = new JTextField(20);
    private JButton deleteButton = new JButton("Delete Category");
    private CategoryTable categoryTable;

    public DeleteCategorie(CategoryTable categoryTable) {
        this.categoryTable = categoryTable; // Reference to update the table if needed
        setLayout(new GridLayout(2, 1, 10, 10)); // Simple grid layout for form
        setBackground(new Color(245, 245, 245)); // Light grey background

        // Adding components to the panel
        add(new JLabel("Category ID:"));
        add(idField);
        add(deleteButton);
        deleteButton.addActionListener(this);

        // Styling the button
        deleteButton.setBackground(new Color(220, 53, 69)); // Bootstrap danger color
        deleteButton.setForeground(Color.WHITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            try {
                long categoryId = Long.parseLong(idField.getText());
                if (sendDeleteRequest(categoryId)) {
                    JOptionPane.showMessageDialog(this, "Category deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    categoryTable.refreshTableData(); // Refresh the article table to reflect deletion
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid category ID. Please enter a numeric ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendDeleteRequest(long categoryId) {
        try {
            URL url = new URL("http://localhost:2018/api/v1/category/delete/" + categoryId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete category. Response code: " + responseCode, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error communicating with the server.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
