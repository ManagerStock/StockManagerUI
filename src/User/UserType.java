package User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserType extends JFrame {
private JButton adminButton, clientButton, fournisseurButton;

public  UserType() {
        setTitle("Add User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null); // Center the frame on screen

        // Create buttons for user types
        adminButton = new JButton("Admin");
        clientButton = new JButton("Client");
        fournisseurButton = new JButton("Fournisseur");

        // Add action listeners to the buttons
        adminButton.addActionListener(new UserTypeButtonListener("Admin"));
        clientButton.addActionListener(new UserTypeButtonListener("Client"));
        fournisseurButton.addActionListener(new UserTypeButtonListener("Fournisseur"));

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        // Add buttons to the panel
        panel.add(adminButton);
        panel.add(clientButton);
        panel.add(fournisseurButton);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
        }

private class UserTypeButtonListener implements ActionListener {
    private String userType;

    public UserTypeButtonListener(String userType) {
        this.userType = userType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Display the AddUserForm based on the selected user type
        switch (userType) {
            case "Admin":
                displayUserForm("Admin");
                break;
            case "Client":
                displayUserForm("Client");
                break;
            case "Fournisseur":
                displayUserForm("Fournisseur");
                break;
        }
    }

    private void displayUserForm(String userType) {
        // Create and display the AddUserForm for the selected user type
        //AddUserFormForType form = new AddUserFormForType(userType);
        //form.setVisible(true);
        dispose(); // Close the UserType selection form
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddUserForm form = new AddUserForm();
            form.setVisible(true);
        });
    }
}