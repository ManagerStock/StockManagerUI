
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
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Description", "Price", "Brand"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        fetchArticles();
    }

    public void refreshTableData() {
        model.setRowCount(0); // Clear table
        fetchArticles(); // Fetch updated data
    }

    private void fetchArticles() {
        try {
            URL url = new URL("http://localhost:2018/api/v1/article/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonNode arrNode = new ObjectMapper().readTree(response.toString());
            if (arrNode.isArray()) {
                for (JsonNode objNode : arrNode) {
                    model.addRow(new Object[]{
                            objNode.get("id").asLong(),
                            objNode.get("name").asText(),
                            objNode.get("description").asText(),
                            objNode.get("price").asDouble(),
                            objNode.get("brand").asText()
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch articles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
