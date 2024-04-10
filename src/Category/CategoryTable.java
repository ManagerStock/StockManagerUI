package Category;
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
public class CategoryTable extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public CategoryTable() {
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Description"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        fetchCategories();
    }

    public void refreshTableData() {
        model.setRowCount(0); // Clear table
        fetchCategories(); // Fetch updated data
    }

    private void fetchCategories() {
        try {
            URL url = new URL("http://localhost:2018/api/v1/category/all");
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
                            objNode.get("description").asText()
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch categories", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}