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
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200)); // Padding for the whole panel
        loginPanel.setBackground(new Color(240, 240, 240)); // Set background color

        JLabel titleLabel = new JLabel("Stock Manager Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increased title font size
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center title
        titleLabel.setForeground(new Color(51, 102, 204)); // Set title color
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 1, 5, 5)); // 3 rows: Username, Password, Error Message
        formPanel.setBackground(Color.WHITE); // Set background color
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Padding for the form

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased text field font size
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased password field font size
        JLabel errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED); // Set error message color

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased button font size
        loginButton.setBackground(new Color(51, 153, 51)); // Set button background color
        loginButton.setForeground(Color.WHITE); // Set button text color
        loginButton.setFocusPainted(false); // Remove focus border
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding to the button

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
                    // Show error message
                    errorMessageLabel.setText("Invalid username or password. Please try again.");
                }
            }
        });

        formPanel.add(usernameField);
        formPanel.add(passwordField);
        formPanel.add(errorMessageLabel);

        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(loginButton, BorderLayout.SOUTH); // Add login button at the bottom

        // Center the login panel
        add(loginPanel, BorderLayout.CENTER);
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
        String[] itemLabels = {"Article", "Transition", "Category", "User", "Exit"};
        for (String label : itemLabels) {
            JMenuItem menuItem = new JMenuItem(label);
            menuItem.setBackground(new Color(108, 117, 125)); // Medium grey
            menuItem.setForeground(Color.WHITE);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            if ("Exit".equals(label)) {
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
            case "Exit":
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