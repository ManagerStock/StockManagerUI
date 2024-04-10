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
    private JButton addArticleToCategoryButton;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private CategoryTable categoryTable;
    private AddCategorieForm addCategorieForm;
    private DeleteArticle deleteArticle ;

    public CategoryPanel() {
        setLayout(new BorderLayout());
        initializeButtonPanel();

        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Pass a reference to ArticleTable into AddArticleForm for refresh
        categoryTable = new CategoryTable();
        addCategorieForm = new AddCategorieForm(categoryTable);
        //deleteArticle = new DeleteArticle(categoryTable);

        contentPanel.add(categoryTable, "CategoryTable");
        contentPanel.add(addCategorieForm, "CategoryForm");
        //contentPanel.add(deleteArticle, "DeleteArticle");

        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        addCategoryButton = new JButton("Add Category");
        updateCategoryButton = new JButton("Update Category");
        deleteCategoryButton = new JButton("Delete Category");
        addArticleToCategoryButton = new JButton("Add Article To Category");

        addCategoryButton.addActionListener(this);
        updateCategoryButton.addActionListener(this);
        deleteCategoryButton.addActionListener(this);
        addArticleToCategoryButton.addActionListener(this);

        buttonPanel.add(addCategoryButton);
        buttonPanel.add(updateCategoryButton);
        buttonPanel.add(deleteCategoryButton);
        buttonPanel.add(addArticleToCategoryButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Add Category".equals(command)) {
            contentLayout.show(contentPanel, "CategoryForm");
        } else if ("Update Article".equals(command)) {
            // Handle Update Article
        } else if ("Delete Article".equals(command)) {
            // Handle Delete Article
            contentLayout.show(contentPanel, "DeleteCategory");
        } else if ("Add Article To Category".equals(command)) {
            contentLayout.show(contentPanel, "CategoryTable");
            // Handle Add Article To Category
        }
    }
}