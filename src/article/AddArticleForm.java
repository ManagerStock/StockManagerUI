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

public class AddArticleForm extends JPanel implements ActionListener {
    private JTextField nameField = new JTextField(20);
    private JTextField descriptionField = new JTextField(20);
    private JTextField priceField = new JTextField(20);
    private JTextField brandField = new JTextField(20);
    private ArticleTable articleTable; // Reference to ArticleTable for refreshing

    public AddArticleForm(ArticleTable articleTable) {
        this.articleTable = articleTable; // Initialize articleTable reference
        setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel brandLabel = new JLabel("Brand:");
        JButton addButton = new JButton("Add Article");
        addButton.addActionListener(this);

        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionField);
        add(priceLabel);
        add(priceField);
        add(brandLabel);
        add(brandField);
        add(new JLabel("")); // Placeholder for layout
        add(addButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Simplify action command check
        if ("Add Article".equals(e.getActionCommand())) {
            String name = nameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            String brand = brandField.getText();

            Article article = new Article(name, description, price, brand);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String articleJson = objectMapper.writeValueAsString(article);
                if (sendPostRequest(articleJson)) {
                    articleTable.refreshTableData(); // Refresh the article table on successful POST
                }
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to serialize article object", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendPostRequest(String articleJson) {
        try {
            String apiUrl = "http://localhost:2018/api/v1/article/add";
            URL url = new URL(apiUrl);
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
                JOptionPane.showMessageDialog(this, "Article added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true; // Return true on successful addition
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add article", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false; // Return false on failure
    }
}