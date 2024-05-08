
package article;


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
import model.Article;

public class UpdateArticleForm extends JFrame implements ActionListener {
    private JTextField idField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField descriptionField = new JTextField(20);
    private JTextField priceField = new JTextField(20);
    private JTextField brandField = new JTextField(20);
    private ArticleTable articleTable;

    public UpdateArticleForm(ArticleTable articleTable, long articleId) {
        this.articleTable = articleTable;
        setTitle("Update Article");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Fetch article data from the backend
        Article article = fetchArticle(articleId);

        // Populate fields with article data
        idField.setText(String.valueOf(articleId));
        nameField.setText(article.getName());
        descriptionField.setText(article.getDescription());
        priceField.setText(String.valueOf(article.getPrice()));
        brandField.setText(article.getBrand());

        // Create labels and fields
        JLabel idLabel = new JLabel("ID:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel descriptionLabel = new JLabel("Description:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel brandLabel = new JLabel("Brand:");

        // Create submit button
        JButton submitButton = new JButton("Update Article");
        submitButton.addActionListener(this);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(idLabel);
        panel.add(idField);

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
        if ("Update Article".equals(e.getActionCommand())) {
            try {
                Article updatedArticle = new Article(
                        nameField.getText(),
                        descriptionField.getText(),
                        Double.parseDouble(priceField.getText()),
                        brandField.getText()
                );
                // Extract the ID from the text field
                long articleId = Long.parseLong(idField.getText());
                // Send the updated article to the backend
                if (sendPutRequest(articleId, updatedArticle)) {
                    JOptionPane.showMessageDialog(this, "Article updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    articleTable.refreshTableData();
                    dispose(); // Close the form after updating article
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error updating article. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private Article fetchArticle(long articleId) {
        try {
            URL url = new URL("http://localhost:2018/api/v1/article/" + articleId);
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
            JsonNode articleNode = mapper.readTree(response.toString());

            // Extract article details from JSON
            String name = articleNode.get("name").asText();
            String description = articleNode.get("description").asText();
            double price = articleNode.get("price").asDouble();
            String brand = articleNode.get("brand").asText();

            return new Article(name, description, price, brand);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch article details", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private boolean sendPutRequest(long articleId, Article updatedArticle) {
        try {
            URL url = new URL("http://localhost:2018/api/v1/article/update/" + articleId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper objectMapper = new ObjectMapper();
            String articleJson = objectMapper.writeValueAsString(updatedArticle);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = articleJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update article", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to send PUT request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
