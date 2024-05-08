package Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPanel extends JPanel implements ActionListener {
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;
    private JButton searchCategoryButton;
    private JTextField searchCategoryTextField;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private CategoryTable categoryTable;
    private AddCategorieForm addCategoryForm;
    private DeleteCategorie deleteCategory;
    public void categoryAdded() {
        // Reload the category table or update the display to reflect the addition of the new category
        contentLayout.show(contentPanel, "CategoryTable");
        categoryTable.refreshTableData(); // Assuming refreshTableData() method exists in CategoryTable
    }
    public void categoryDeleted() {
        // Reload the category table or update the display to reflect the deletion of the category
        contentLayout.show(contentPanel, "CategoryTable");
        categoryTable.refreshTableData(); // Assuming refreshTableData() method exists in CategoryTable
    }

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initializeButtonPanel();

        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Pass a reference to CategoryTable into AddCategoryForm for refresh
        categoryTable = new CategoryTable();

        addCategoryForm = new AddCategorieForm(categoryTable,this);
        deleteCategory = new DeleteCategorie(this);

        contentPanel.add(categoryTable, "CategoryTable");
        contentPanel.add(addCategoryForm, "CategoryForm");
        contentPanel.add(deleteCategory, "DeleteCategory");

        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        addCategoryButton = new JButton("Add Category");
        deleteCategoryButton = new JButton("Delete Category");
        searchCategoryButton = new JButton("Search");
        searchCategoryTextField = new JTextField(10);

        addCategoryButton.addActionListener(this);
        deleteCategoryButton.addActionListener(this);
        searchCategoryButton.addActionListener(this);

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(deleteCategoryButton);
        buttonPanel.add(new JLabel("Search Category by ID:"));
        buttonPanel.add(searchCategoryTextField);
        buttonPanel.add(searchCategoryButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Add Category".equals(command)) {
            contentLayout.show(contentPanel, "CategoryForm");
        } else if ("Delete Category".equals(command)) {
            contentLayout.show(contentPanel, "DeleteCategory");
        } else if ("Search".equals(command)) {
            // Retrieve category ID from the text field
            String categoryIdText = searchCategoryTextField.getText();
            try {
                long categoryId = Long.parseLong(categoryIdText);
                // Fetch category data from backend using the category ID
                // Display the Update Category Form with the retrieved category data
                UpdateCategoryForm updateCategoryForm = new UpdateCategoryForm(categoryId, categoryTable);
                updateCategoryForm.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid category ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}