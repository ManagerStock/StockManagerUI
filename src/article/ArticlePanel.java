package article;

import User.AddUserForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticlePanel extends JPanel implements ActionListener {
    private JButton addArticleButton;
    private JButton updateArticleButton;
    private JButton deleteArticleButton;
   
    private ArticleTable articleTable;
    private JPanel contentPanel ;
    private CardLayout contentLayout;

    public ArticlePanel() {
        addArticleButton = new JButton("Add Article");
        updateArticleButton = new JButton("Update Article");
        deleteArticleButton = new JButton("Delete Article");


        addArticleButton.addActionListener(this);
        updateArticleButton.addActionListener(this);
        deleteArticleButton.addActionListener(this);


        add(addArticleButton);
        add(updateArticleButton);
        add(deleteArticleButton);


        // Initialize ArticleTable
        articleTable = new ArticleTable();
        add(articleTable); // Add the article table to the panel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Article":
                // Open Add Article Form
                AddArticleForm addArticleForm = new AddArticleForm(articleTable);
                addArticleForm.setVisible(true);
                break;
            case "Update Article":
                // Handle Update Article
                break;
            case "Delete Article":
                // Open Delete Article Form
                DeleteArticle deleteArticle = new DeleteArticle(articleTable);
                deleteArticle.setVisible(true);
                break;

        }
    }
}