package article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteArticle extends JFrame {
    private JTextField articleIdField;
    private ArticleTable articleTable; // Reference to the ArticleTable panel

    public DeleteArticle(ArticleTable articleTable) {
        this.articleTable = articleTable; // Set the reference to the ArticleTable panel
        setTitle("Delete Article");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create label and text field for article ID
        JLabel articleIdLabel = new JLabel("Article ID:");
        articleIdField = new JTextField(15);

        // Create delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteArticle();
            }
        });

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(articleIdLabel);
        panel.add(articleIdField);
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(deleteButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    private void deleteArticle() {
        String articleId = articleIdField.getText();
        try {
            URL url = new URL("http://localhost:2018/api/v1/article/delete/" + articleId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                JOptionPane.showMessageDialog(this, "Article deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                articleIdField.setText(""); // Clear the text field after successful deletion
                articleTable.refreshTableData(); // Refresh the article table
                dispose(); // Close the frame
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete article", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send DELETE request", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}