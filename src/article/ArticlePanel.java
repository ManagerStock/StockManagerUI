package article;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticlePanel extends JPanel implements ActionListener {
    private JButton addArticleButton;
    //private JButton updateArticleButton;
    private JButton deleteArticleButton;

    private ArticleTable articleTable;
    private JTextField searchTextField; // Text field for entering article ID
    private JButton searchButton; // Button to search for article by ID

    public ArticlePanel() {
        addArticleButton = new JButton("Add Article");
      //  updateArticleButton = new JButton("Update Article");
        deleteArticleButton = new JButton("Delete Article");

        addArticleButton.addActionListener(this);
        //updateArticleButton.addActionListener(this);
        deleteArticleButton.addActionListener(this);

        // Initialize components for searching article by ID
        searchTextField = new JTextField(10);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);

        // Add components to the panel
        add(addArticleButton);
        //add(updateArticleButton);
        add(deleteArticleButton);
        add(new JLabel("Search Article by ID:"));
        add(searchTextField);
        add(searchButton);

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
            case "Delete Article":
                // Open Delete Article Form
                DeleteArticle deleteArticle = new DeleteArticle(articleTable);
                deleteArticle.setVisible(true);
                break;
            case "Search":
                // Retrieve article ID from the text field
                String articleIdText = searchTextField.getText();
                try {
                    long articleId = Long.parseLong(articleIdText);
                    // Fetch article data from backend using the article ID
                    // Display the Update Article Form with the retrieved article data
                    UpdateArticleForm updateArticleForm = new UpdateArticleForm(articleTable, articleId);
                    updateArticleForm.setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid article ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}