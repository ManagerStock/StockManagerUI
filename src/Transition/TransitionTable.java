package Transition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransitionTable extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public TransitionTable() {
        model = new DefaultTableModel(new Object[]{"ID", "Client ID", "Article ID", "Total Amount"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        fetchTransitions();
    }

    public void refreshTableData() {
        model.setRowCount(0); // Clear table
        fetchTransitions(); // Fetch updated data
    }

    private void fetchTransitions() {
        try {
            URL url = new URL("http://localhost:2018/api/v1/transition/all");
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
                    Long id = objNode.has("id") ? objNode.get("id").asLong() : null;
                    Long clientId = objNode.has("clientId") ? objNode.get("clientId").asLong() : null;
                    Long articleId = objNode.has("articleId") ? objNode.get("articleId").asLong() : null;
                    Double totalAmount = objNode.has("totalAmount") ? objNode.get("totalAmount").asDouble() : null;

                    model.addRow(new Object[]{id, clientId, articleId, totalAmount});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch transitions", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
