import Category.CategoryPanel;
import Transition.TransitionPanel;
import User.UserPanel;
import article.ArticlePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public Menu() {
        setTitle("Stock Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245)); // Light grey background

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(60, 63, 65)); // Dark grey
        JMenu menu = new JMenu("Menu");
        menu.setForeground(Color.WHITE);

        // Menu items
        String[] itemLabels = {"Article", "Transition", "Category", "User", "Exit"};
        for (String label : itemLabels) {
            JMenuItem menuItem = new JMenuItem(label);
            menuItem.setBackground(new Color(108, 117, 125)); // Medium grey
            menuItem.setForeground(Color.WHITE);
            menuItem.addActionListener(this);
            menu.add(menuItem);
            if ("Exit".equals(label)) {
                menu.addSeparator();
            }
        }

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Card layout for panel switching
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(new Color(233, 236, 239)); // Light grey

        // Adding the panels
        cardPanel.add(new ArticlePanel(), "ArticlePanel");
        cardPanel.add(new TransitionPanel(), "TransitionPanel");
        cardPanel.add(new CategoryPanel(), "CategoryPanel");
        cardPanel.add(new UserPanel(), "UserPanel");

        add(cardPanel);

        // Initial panel
        cardLayout.show(cardPanel, "ArticlePanel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Article":
                cardLayout.show(cardPanel, "ArticlePanel");
                break;
            case "Transition":
                cardLayout.show(cardPanel, "TransitionPanel");
                break;
            case "Category":
                cardLayout.show(cardPanel, "CategoryPanel");
                break;
            case "User":
                cardLayout.show(cardPanel, "UserPanel");
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }}