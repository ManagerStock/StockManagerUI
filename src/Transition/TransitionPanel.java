package Transition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransitionPanel extends JPanel implements ActionListener {
    private JButton addTransitionButton;
    private JButton updateTransitionButton;
    private JButton deleteTransitionButton;


    public TransitionPanel() {
        // Initialize buttons
        addTransitionButton = new JButton("Add Transition");
        updateTransitionButton = new JButton("Update Transition");
        deleteTransitionButton = new JButton("Delete Transition");


        // Add action listeners to buttons
        addTransitionButton.addActionListener(this);
        updateTransitionButton.addActionListener(this);
        deleteTransitionButton.addActionListener(this);

        // Add buttons to panel
        add(addTransitionButton);
        add(updateTransitionButton);
        add(deleteTransitionButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Transition":
                // Handle Add Article button click
                break;
            case "Update Transition":
                // Handle Update Article button click
                break;
            case "Delete Transition":
                // Handle Delete Article button click
                break;

            default:
                break;
        }
    }
}
