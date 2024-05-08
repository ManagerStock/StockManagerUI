package Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransitionPanel extends JPanel implements ActionListener {
    private JButton addTransitionButton;

    private JButton deleteTransitionButton;
    private JButton allTransitionsButton; // Button for displaying all transitions
    private TransitionTable transitionTable; // Reference to the transition table panel
    private JPanel contentPanel;
    private CardLayout contentLayout;

    public TransitionPanel() {
        // Initialize buttons
        addTransitionButton = new JButton("Add Transition");

        deleteTransitionButton = new JButton("Delete Transition");
        allTransitionsButton = new JButton("All Transitions");

        // Add action listeners to buttons
        addTransitionButton.addActionListener(this);

        deleteTransitionButton.addActionListener(this);
        allTransitionsButton.addActionListener(this);

        // Add buttons to a sub-panel for better layout control
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.add(addTransitionButton);

        buttonPanel.add(deleteTransitionButton);
        buttonPanel.add(allTransitionsButton);


        // Initialize the content panel and layout
        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Create and add the transition table panel
        transitionTable = new TransitionTable();
        JScrollPane scrollPane = new JScrollPane(transitionTable);
        contentPanel.add(scrollPane, "TransitionTable");

        // Set the layout of this panel
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH); // Add button panel to the top
        add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Transition":
                AddTransitionForm addTransitionForm = new AddTransitionForm(transitionTable);
                addTransitionForm.setVisible(true);
                break;
            case "Update Transition":
                // Handle Update Transition button click
                break;
            case "Delete Transition":
                DeleteTransition deleteTransition = new DeleteTransition(transitionTable);
                deleteTransition.setVisible(true);
                break;
            case "All Transitions":
                // Show the transition table panel when the "All Transitions" button is clicked
                contentLayout.show(contentPanel, "TransitionTable");
                transitionTable.refreshTableData(); // Refresh the transition table data
                break;
            default:
                break;
        }
    }

}