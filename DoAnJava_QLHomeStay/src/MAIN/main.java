package Main;

import Controller.LoginController;
import GUI.LoginJDialog;

public class main {
    public static void main(String[] args) {
        LoginJDialog loginJDialog = new LoginJDialog(new javax.swing.JFrame(), true);
//        LoginController controller = new LoginController(loginJDialog);
        loginJDialog.setVisible(true);
    }
}
