package article;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArticlePanel extends JPanel implements ActionListener {
    private JButton addArticleButton;
    private JButton updateArticleButton;
    private JButton deleteArticleButton;
    private JButton addArticleToCategoryButton;

    public ArticlePanel() {
        // Initialize buttons
        addArticleButton = new JButton("Add Article");
        updateArticleButton = new JButton("Update Article");
        deleteArticleButton = new JButton("Delete Article");
        addArticleToCategoryButton = new JButton("Add Article To Category");

        // Add action listeners to buttons
        addArticleButton.addActionListener(this);
        updateArticleButton.addActionListener(this);
        deleteArticleButton.addActionListener(this);
        addArticleToCategoryButton.addActionListener(this);

        // Add buttons to panel
        add(addArticleButton);
        add(updateArticleButton);
        add(deleteArticleButton);
        add(addArticleToCategoryButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add Article":
                // Handle Add Article button click
                break;
            case "Update Article":
                // Handle Update Article button click
                break;
            case "Delete Article":
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