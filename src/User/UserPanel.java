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
                AddUserForm addUserForm = new AddUserForm();
                addUserForm.setVisible(true);
                break;
            case "Update User":
                // Handle Update Article button click
                break;
            case "Delete User":
                DeleteUser deleteUserForm = new DeleteUser();
                deleteUserForm.setVisible(true);
                break;

            default:
                break;
        }
    }
}
