import Category.CategoryPanel;
import Transition.TransitionPanel;
import User.UserPanel;
import article.ArticlePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    private ArticlePanel articlePanel;
    private TransitionPanel transitionPanel;
    private UserPanel userPanel;
    private CategoryPanel categoryPanel;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Menu() {
        setTitle("Stock Manager");
        setSize(800, 600); // Set the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create menus
        JMenu menu = new JMenu("Menu");

        // Create menu items
        JMenuItem articleMenuItem = new JMenuItem("Article");
        JMenuItem transitionMenuItem = new JMenuItem("Transition");
        JMenuItem categoryMenuItem = new JMenuItem("Category");
        JMenuItem userMenuItem = new JMenuItem("User");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Add menu items to menu
        menu.add(articleMenuItem);
        menu.add(transitionMenuItem);
        menu.add(categoryMenuItem);
        menu.add(userMenuItem);
        menu.addSeparator(); // Add a separator between the items
        menu.add(exitMenuItem);

        // Add action listeners to menu items
        articleMenuItem.addActionListener(this);
        transitionMenuItem.addActionListener(this);
        categoryMenuItem.addActionListener(this);
        userMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        // Add menu to menu bar
        menuBar.add(menu);

        // Set menu bar to frame
        setJMenuBar(menuBar);

        // Initialize panels
        articlePanel = new ArticlePanel();
        transitionPanel = new TransitionPanel();
        categoryPanel = new CategoryPanel();
        userPanel = new UserPanel();

        // Create card panel and set layout to CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Add panels to card panel with unique names
        cardPanel.add(articlePanel, "ArticlePanel");
        cardPanel.add(transitionPanel, "TransitionPanel");
        cardPanel.add(categoryPanel, "CategoryPanel");
        cardPanel.add(userPanel, "UserPanel");

        // Add card panel to frame
        add(cardPanel);
        // Add card panel to frame
        // Initially show the article panel
        cardLayout.show(cardPanel, "ArticlePanel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle menu item clicks
        String command = e.getActionCommand();
        switch (command) {
            case "Article":
                // Show article panel
                cardLayout.show(cardPanel, "ArticlePanel");
                break;
            case "Transition":
                // Show transition panel
                cardLayout.show(cardPanel, "TransitionPanel");
                break;
            case "Category":
                // Show category panel
                cardLayout.show(cardPanel, "CategoryPanel");
                break;
            case "User":
                // Show user panel
                cardLayout.show(cardPanel, "UserPanel");
                break;
            case "Exit":
                // Handle Exit menu item click
                System.exit(0);
                break;
            default:
                break;
        }
    }}