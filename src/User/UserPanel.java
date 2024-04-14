package User;

import pdf.PdfPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel implements ActionListener {
    private JButton addUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;
    private JButton allUsersButton; // Button for displaying all users
    private JButton downloadPDF;
    private UserTable userTable; // Reference to the user table panel
    private JPanel contentPanel;
    private CardLayout contentLayout;

    public UserPanel() {
        // Initialize buttons
        addUserButton = new JButton("Add User");
        updateUserButton = new JButton("Update User");
        deleteUserButton = new JButton("Delete User");
        allUsersButton = new JButton("All Users");
        downloadPDF = new JButton("Download Pdf");

        // Add action listeners to buttons
        addUserButton.addActionListener(this);
        updateUserButton.addActionListener(this);
        deleteUserButton.addActionListener(this);
        allUsersButton.addActionListener(this);
        downloadPDF.addActionListener(this);

        // Add buttons to a sub-panel for better layout control
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.add(addUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(allUsersButton);
        buttonPanel.add(downloadPDF);

        // Initialize the content panel and layout
        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Create and add the user table panel
        userTable = new UserTable();
        contentPanel.add(userTable, "UserTable");

        // Set the layout of this panel
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH); // Add button panel to the top
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add User":
                AddUserForm addUserForm = new AddUserForm(userTable);
                addUserForm.setVisible(true);
                break;
            case "Update User":
                // Handle Update User button click
                break;
            case "Delete User":
                DeleteUser deleteUserForm = new DeleteUser(userTable);
                deleteUserForm.setVisible(true);
                break;
            case "All Users":
                // Show the user table panel when the "All Users" button is clicked
                contentLayout.show(contentPanel, "UserTable");
                break;
            case "Download Pdf" :
                JFrame frame = new JFrame("PDF Download");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new PdfPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            default:
                break;
        }
    }
}