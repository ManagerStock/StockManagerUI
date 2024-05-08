package User;
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

public class UserTable extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public UserTable() {
        model = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email", "Address", "Phone Number", "Date of Birth"}, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        fetchUsers();
    }

    public void refreshTableData() {
        model.setRowCount(0); // Clear table
        fetchUsers(); // Fetch updated data
    }

    private void fetchUsers() {
        try {
            URL url = new URL("http://localhost:2018/api/v1/user/all");
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
                    String firstName = objNode.has("firstName") && !objNode.get("firstName").isNull() ? objNode.get("firstName").asText() : "";
                    String lastName = objNode.has("lastName") && !objNode.get("lastName").isNull() ? objNode.get("lastName").asText() : "";
                    String email = objNode.has("email") && !objNode.get("email").isNull() ? objNode.get("email").asText() : "";
                    String address = objNode.has("address") && !objNode.get("address").isNull() ? objNode.get("address").asText() : "";
                    String phoneNumber = objNode.has("phoneNumber") && !objNode.get("phoneNumber").isNull() ? objNode.get("phoneNumber").asText() : "";

                    LocalDate dateOfBirth = null;
                    if (objNode.has("dateOfBirth") && !objNode.get("dateOfBirth").isNull()) {
                        String dateStr = objNode.get("dateOfBirth").asText();
                        dateOfBirth = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
                    }

                    model.addRow(new Object[]{id, firstName, lastName, email, address, phoneNumber, dateOfBirth});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}