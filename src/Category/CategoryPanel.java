package Category;

import article.AddArticleForm;
import article.ArticleTable;
import article.DeleteArticle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPanel extends JPanel implements ActionListener {
    private JButton addCategoryButton;
    private JButton updateCategoryButton;
    private JButton deleteCategoryButton;
    private JButton allCategories;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private CategoryTable categoryTable;
    private AddCategorieForm addCategorieForm;
    private DeleteCategorie deleteCategorie ;

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initializeButtonPanel();

        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Pass a reference to ArticleTable into AddArticleForm for refresh
        categoryTable = new CategoryTable();
        addCategorieForm = new AddCategorieForm(categoryTable);
        deleteCategorie = new DeleteCategorie();

        contentPanel.add(categoryTable, "CategoryTable");
        contentPanel.add(addCategorieForm, "CategoryForm");
        contentPanel.add(deleteCategorie, "DeleteCategory");

        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        addCategoryButton = new JButton("Add Category");
        updateCategoryButton = new JButton("Update Category");
        deleteCategoryButton = new JButton("Delete Category");
        allCategories = new JButton("All Categories");

        addCategoryButton.addActionListener(this);
        updateCategoryButton.addActionListener(this);
        deleteCategoryButton.addActionListener(this);
        allCategories.addActionListener(this);

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(updateCategoryButton);
        buttonPanel.add(deleteCategoryButton);
        buttonPanel.add(allCategories);

        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Add Category".equals(command)) {
            contentLayout.show(contentPanel, "CategoryForm");
        } else if ("Update Category".equals(command)) {
            // Handle Update Article
        } else if ("Delete Category".equals(command)) {
            // Handle Delete Article
            contentLayout.show(contentPanel, "DeleteCategory");
        } else if (("All Categories ").equals(command)) {
            contentLayout.show(contentPanel, "CategoryPanel");
            // Handle Add Article To Category
        }
    }
}