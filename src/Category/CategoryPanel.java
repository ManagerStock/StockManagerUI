package Category;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPanel extends JPanel implements ActionListener {
    private JButton addCategoryButton;
    private JButton updateCategoryButton;
    private JButton deleteCategoryButton;
    private JButton addArticleToCategoryButton;

    public CategoryPanel() {
        // Initialize buttons
       addCategoryButton = new JButton("Add Category");
        updateCategoryButton = new JButton("Update Category");
        deleteCategoryButton = new JButton("Delete Category");
        addArticleToCategoryButton = new JButton("Add Article To Category");

        // Add action listeners to buttons
        addCategoryButton.addActionListener(this);
        updateCategoryButton.addActionListener(this);
        deleteCategoryButton.addActionListener(this);
        addArticleToCategoryButton.addActionListener(this);

        // Add buttons to panel
        add(addCategoryButton);
        add(updateCategoryButton);
        add(deleteCategoryButton);
        add(addArticleToCategoryButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Category":
                // Handle Add Article button click
                break;
            case "Update Category":
                // Handle Update Article button click
                break;
            case "Delete Category":
                // Handle Delete Article button click
                break;
            case "Add Article To Category":
                // Handle Add Article To Category button click
                break;
            default:
                break;
        }
    }
}
