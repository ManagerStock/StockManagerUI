package article;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticlePanel extends JPanel implements ActionListener {
    private JButton addArticleButton;
    private JButton updateArticleButton;
    private JButton deleteArticleButton;
    private JButton addArticleToCategoryButton;
    private JPanel contentPanel;
    private CardLayout contentLayout;
    private ArticleTable articleTable;
    private AddArticleForm addArticleForm;
    private DeleteArticle deleteArticle ;

    public ArticlePanel() {
        setLayout(new BorderLayout());
        initializeButtonPanel();

        contentPanel = new JPanel();
        contentLayout = new CardLayout();
        contentPanel.setLayout(contentLayout);

        // Pass a reference to ArticleTable into AddArticleForm for refresh
        articleTable = new ArticleTable();
        addArticleForm = new AddArticleForm(articleTable);
        deleteArticle = new DeleteArticle(articleTable);

        contentPanel.add(articleTable, "ArticleTable");
        contentPanel.add(addArticleForm, "ArticleForm");
        contentPanel.add(deleteArticle, "DeleteArticle");

        add(contentPanel, BorderLayout.CENTER);
        //contentLayout.show(contentPanel, "ArticleTable");
    }

    private void initializeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        addArticleButton = new JButton("Add Article");
        updateArticleButton = new JButton("Update Article");
        deleteArticleButton = new JButton("Delete Article");
        addArticleToCategoryButton = new JButton("Add Article To Category");

        addArticleButton.addActionListener(this);
        updateArticleButton.addActionListener(this);
        deleteArticleButton.addActionListener(this);
        addArticleToCategoryButton.addActionListener(this);

        buttonPanel.add(addArticleButton);
        buttonPanel.add(updateArticleButton);
        buttonPanel.add(deleteArticleButton);
        buttonPanel.add(addArticleToCategoryButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("Add Article".equals(command)) {
            contentLayout.show(contentPanel, "ArticleForm");
        } else if ("Update Article".equals(command)) {
            // Handle Update Article
        } else if ("Delete Article".equals(command)) {
            // Handle Delete Article
            contentLayout.show(contentPanel, "DeleteArticle");
        } else if ("Add Article To Category".equals(command)) {
            contentLayout.show(contentPanel, "ArticleTable");
            // Handle Add Article To Category
        }
    }
}