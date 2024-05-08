package Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteTransition extends JFrame implements ActionListener {
    private JTextField transitionIdField;
    private TransitionTable transitionTable; // Reference to the TransitionTable panel

    public DeleteTransition(TransitionTable transitionTable) {
        this.transitionTable = transitionTable; // Set the reference to the TransitionTable panel
        setTitle("Delete Transition");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create label and text field for transition ID
        JLabel transitionIdLabel = new JLabel("Transition ID:");
        transitionIdField = new JTextField(15);

        // Create delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(transitionIdLabel);
        panel.add(transitionIdField);
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(deleteButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Delete")) {
            deleteTransition();
        }
    }

    private void deleteTransition() {
        String transitionId = transitionIdField.getText();
        try {
            URL url = new URL("http://localhost:2018/api/v1/transition/" + transitionId);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                JOptionPane.showMessageDialog(this, "Transition deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                transitionIdField.setText(""); // Clear the text field after successful deletion
                dispose();
                // Refresh the transition table after deletion
                transitionTable.refreshTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete transition", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to send DELETE request", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}