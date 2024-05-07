import login.LoginForm;

import javax.swing.*;
    public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
                // Set the default button to handle Enter key press
                loginForm.getRootPane().setDefaultButton(loginForm.getSubmitButton());

                // Wait for the login form to close
                loginForm.addLoginListener(new LoginForm.LoginListener() {
                    @Override
                    public void loginPerformed(boolean success) {
                        if (success) {
                            loginForm.dispose();
                            Menu stockManagerUI = new Menu();
                            stockManagerUI.setVisible(true);
                        }
                    }
                });
            });
        }
    }
