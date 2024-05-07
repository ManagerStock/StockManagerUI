package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton submitButton;
    private LoginListener loginListener;

    public LoginForm() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create labels and fields
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Create submit button
        submitButton = new JButton("Login");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (verifyLogin()) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (loginListener != null) {
                        loginListener.loginPerformed(true); // Notify listener of successful login
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        // Add panel and button to the frame
        add(panel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    public boolean verifyLogin() {
        String name = nameField.getText();
        String password = new String(passwordField.getPassword());
        return name.equals("admin") && password.equals("admin");
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public void addLoginListener(LoginListener listener) {
        this.loginListener = listener;
    }

    public interface LoginListener {
        void loginPerformed(boolean success);
    }
}