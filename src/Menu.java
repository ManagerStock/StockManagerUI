import Category.CategoryPanel;
import Transition.TransitionPanel;
import User.UserPanel;
import article.ArticlePanel;

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
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Menu extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Menu() {
        setTitle("Stock Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245)); // Light grey background

        // Show login panel initially
        showLoginPanel();
    }

    private void showLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Make HTTP POST request to backend for authentication
                boolean isAuthenticated = authenticate(username, password);

                if (isAuthenticated) {
                    // Remove the login panel and show the menu
                    getContentPane().removeAll();
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(Menu.this, "Invalid username or password. Please try again.");
                }
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for alignment
        loginPanel.add(loginButton);

        add(loginPanel);
        revalidate();
        repaint();
    }

    private void showMenu() {
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(60, 63, 65)); // Dark grey
        JMenu menu = new JMenu("Menu");
        menu.setForeground(Color.WHITE);

        // Menu items
        String[] itemLabels = {"Article", "Transition", "Category", "User", "EXIT"};
        for (String label : itemLabels) {
            JMenuItem menuItem = new JMenuItem(label);
            menuItem.setBackground(new Color(108, 117, 125)); // Medium grey
            menuItem.setForeground(Color.WHITE);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            if ("DISPOSE".equals(label)) {
                menu.addSeparator();
            }
        }

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Card layout for panel switching
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(new Color(233, 236, 239)); // Light grey

        // Adding the panels
        cardPanel.add(new ArticlePanel(), "ArticlePanel");
        cardPanel.add(new TransitionPanel(), "TransitionPanel");
        cardPanel.add(new CategoryPanel(), "CategoryPanel");
        cardPanel.add(new UserPanel(), "UserPanel");

        add(cardPanel);

        // Initial panel
        cardLayout.show(cardPanel, "ArticlePanel");

        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Article":
                cardLayout.show(cardPanel, "ArticlePanel");
                break;
            case "Transition":
                cardLayout.show(cardPanel, "TransitionPanel");
                break;
            case "Category":
                cardLayout.show(cardPanel, "CategoryPanel");
                break;
            case "User":
                cardLayout.show(cardPanel, "UserPanel");
                break;
            case "EXIT":
                dispose(); // Exit the application
                break;
        }
    }

    private boolean authenticate(String username, String password) {
        try {
            // Create the URL object with the backend's login endpoint
            URL url = new URL("http://localhost:2018/ok");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            conn.setRequestMethod("GET");

            // Create the Base64 encoded "username:password" string for Basic Authentication
            String userPass = username + ":" + password;
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes());

            // Include the Basic Authentication string in the Authorization header
            conn.setRequestProperty("Authorization", basicAuth);

            // Read the response from the server
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true; // Authentication successful
            } else {
                return false; // Authentication failed
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Menu();
            }
        });
    }
}