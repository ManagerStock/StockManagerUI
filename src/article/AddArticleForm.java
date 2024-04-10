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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Article;


public class AddArticleForm extends JPanel implements ActionListener {
    private JTextField categoryIdField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField descriptionField = new JTextField(20);
    private JTextField priceField = new JTextField(20);
    private JTextField brandField = new JTextField(20);
    private ArticleTable articleTable;

    public AddArticleForm(ArticleTable articleTable) {
        this.articleTable = articleTable;
        setLayout(new GridLayout(5, 2, 10, 10));
        setBackground(new Color(245, 245, 245)); // Light grey
        addField("Category ID :",  categoryIdField);
        addField("Name:", nameField);
        addField("Description:", descriptionField);
        addField("Price:", priceField);
        addField("Brand:", brandField);

        JButton addButton = new JButton("Add Article");
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
                }
            } catch (NumberFormatException | JsonProcessingException ex) {
                JOptionPane.showMessageDialog(this, "Error adding article. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean sendPostRequest(String articleJson) {
        try {
            Long categoryId = Long.parseLong(categoryIdField.getText());
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


