package article;

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

public class AddArticleForm extends JFrame implements ActionListener {
    private JTextField categoryIdField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField descriptionField = new JTextField(20);
    private JTextField priceField = new JTextField(20);
    private JTextField brandField = new JTextField(20);
    private ArticleTable articleTable;

    public AddArticleForm(ArticleTable articleTable) {
        this.articleTable = articleTable;
        setTitle("Add Article");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create labels and fields
        JLabel categoryIdLabel = new JLabel("Category ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel brandLabel = new JLabel("Brand:");

        // Create submit button
        JButton submitButton = new JButton("Add Article");
        submitButton.addActionListener(this);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(categoryIdLabel);
        panel.add(categoryIdField);

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(descriptionLabel);
        panel.add(descriptionField);

        panel.add(priceLabel);
        panel.add(priceField);

        panel.add(brandLabel);
        panel.add(brandField);

        // Add submit button to the panel
        panel.add(submitButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Add Article".equals(e.getActionCommand())) {
            try {
                Article article = new Article(
                        nameField.getText(),
                        descriptionField.getText(),
                        Double.parseDouble(priceField.getText()),
                        brandField.getText()
                );
                ObjectMapper objectMapper = new ObjectMapper();
                String articleJson = objectMapper.writeValueAsString(article);
                if (sendPostRequest(articleJson)) {
                    JOptionPane.showMessageDialog(this, "Article added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    articleTable.refreshTableData();
                    dispose(); // Close the form after adding article
                }
            } catch (NumberFormatException | JsonProcessingException ex) {
                JOptionPane.showMessageDialog(this, "Error adding article. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendPostRequest(String articleJson) {
        try {
            long categoryId = Long.parseLong(categoryIdField.getText());
            URL url = new URL("http://localhost:2018/api/v1/article/add/" + categoryId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = articleJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add article", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}