package User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel implements ActionListener {
    private JButton addUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;


    public UserPanel() {
        // Initialize buttons
        addUserButton = new JButton("Add User");
        updateUserButton = new JButton("Update User");
        deleteUserButton = new JButton("Delete User");


        // Add action listeners to buttons
        addUserButton.addActionListener(this);
        updateUserButton.addActionListener(this);
        deleteUserButton.addActionListener(this);

        // Add buttons to panel
        add(addUserButton);
        add(updateUserButton);
        add(deleteUserButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add User":
                // Handle Add Article button click
                break;
            case "Update User":
                // Handle Update Article button click
                break;
            case "Delete User":
                // Handle Delete Article button click
                break;

            default:
                break;
        }
    }
}
