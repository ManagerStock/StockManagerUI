package article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class ArticleTable extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public ArticleTable() {
        // Initialize table model and table as before
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Price");
        model.addColumn("Brand");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Initial fetch to populate table
        fetchArticles();
    }

    public void refreshTableData() {
        model.setRowCount(0); // Clear existing data
        fetchArticles(); // Fetch and repopulate data
    }

    private void fetchArticles() {

        // Implementation as before to fetch and populate table data
        try {
            // URL of your backend endpoint to fetch articles
            URL url = new URL("http://localhost:2018/api/v1/article/all");

            // Make HTTP GET request
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // Parse JSON response using Jackson
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.toString());

            // Iterate over each JSON object in the array
            for (JsonNode articleNode : root) {
                // Extract article data
                long id = articleNode.get("id").asLong();
                String name = articleNode.get("name").asText();
                String description = articleNode.get("description").asText();
                double price = articleNode.get("price").asDouble();
                String brand = articleNode.get("brand").asText();

                // Add article data to table model
                model.addRow(new Object[]{id, name, description, price, brand});
            }

            // Close connection
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch articles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }
