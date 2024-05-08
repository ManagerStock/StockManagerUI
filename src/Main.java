import javax.swing.*;

public class
Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu stockManagerUI = new Menu();
            stockManagerUI.setVisible(true);
        });
    }
}