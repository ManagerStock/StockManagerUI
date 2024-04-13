package pdf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfPanel extends JPanel {

    private JTextField clientIdField;
    private JButton downloadButton;

    public PdfPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create components
        clientIdField = new JTextField(10);
        downloadButton = new JButton("Download PDF");

        // Add action listener to the download button
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadPDF();
            }
        });

        // Add components to the panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Client ID:"));
        inputPanel.add(clientIdField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(downloadButton);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void downloadPDF() {
        String clientId = clientIdField.getText();
        if (clientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a client ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            URL url = new URL("http://localhost:2018/api/v1/pdf/download/" + clientId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "client_details.pdf";
                String saveFilePath = "./" + fileName;

                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead;
                byte[] buffer = new byte[1024];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                JOptionPane.showMessageDialog(this, "PDF downloaded successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to download PDF. Server returned HTTP error code: " + responseCode,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to download PDF: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("PDF Download");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new PdfPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}