package Transition;

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
import enums.TransitionType;
import model.Transition;

public class AddTransitionForm extends JFrame {
    private JTextField totalAmountField, idClientField, idArticleField;;
    private JComboBox<TransitionType> transitionTypeComboBox;
    private TransitionTable transitionTable; // Reference to the transition table

    public AddTransitionForm(TransitionTable transitionTable) {
        this.transitionTable = transitionTable; // Set the reference to the transition table
        setTitle("Add Transition");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create labels and fields
        JLabel totalAmountLabel = new JLabel("Total Amount:");
        totalAmountField = new JTextField(20);

        JLabel transitionTypeLabel = new JLabel("Transition Type:");
        transitionTypeComboBox = new JComboBox<>(TransitionType.values());

        JLabel idClientLabel = new JLabel("Client ID:");
        idClientField = new JTextField(20);

        JLabel idArticleLabel = new JLabel("Article ID:");
        idArticleField = new JTextField(20);

        // Create submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransition();
            }
        });

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(transitionTypeLabel);
        panel.add(transitionTypeComboBox);
        panel.add(idClientLabel);
        panel.add(idClientField);
        panel.add(idArticleLabel);
        panel.add(idArticleField);

        // Add panel and button to the frame
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void addTransition() {
        try {
            Transition transition = new Transition();
            transition.setTransitionType((TransitionType) transitionTypeComboBox.getSelectedItem());

            // Convert Transition object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String transitionJson = objectMapper.writeValueAsString(transition);

            // Send POST request
            if (sendPostRequest(transitionJson)) {
                JOptionPane.showMessageDialog(this, "Transition added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                transitionTable.refreshTableData(); // Refresh transition table after adding a transition
                dispose(); // Close the form after adding a transition
            }
        } catch (NumberFormatException | JsonProcessingException ex) {
            JOptionPane.showMessageDialog(this, "Error adding transition. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean sendPostRequest(String transitionJson) {
        try {
            Long idClient = Long.valueOf(idClientField.getText());
            Long idArticle = Long.valueOf(idArticleField.getText());

            String apiUrl = "http://localhost:2018/api/v1/transition/add/" + idClient + "/" + idArticle;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = transitionJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add transition", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send POST request", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Main method for testing

}