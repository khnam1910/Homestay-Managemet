package Controller;

import GUI.LoginJDialog;
import DAO.TaiKhoanDAO;
import GUI.Main;
import POJO.TaiKhoanPOJO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class TaiKhoanController {

    private LoginJDialog loginJDialog;
    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanController(LoginJDialog view) {
        this.loginJDialog = view;
        this.taiKhoanDAO = new TaiKhoanDAO();
        view.addjbDangNhapMouseListener(new jbDangNhapMouseListener());
    }
    
    private class jbDangNhapMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            String username = loginJDialog.getUsername();
            String password = loginJDialog.getPassword();

            if (taiKhoanDAO.checkUsername(username)) {
                if (taiKhoanDAO.checkPassword(username, password)) {
                    String quyen = taiKhoanDAO.getQuyen(username);
                    TaiKhoanPOJO taikhoan = taiKhoanDAO.getNV(username);

                    if (taikhoan != null) {
                        loginJDialog.dispose();
                        Main mainFrame = new Main(quyen, username);
                        mainFrame.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin nhân viên", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không đúng", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    loginJDialog.resetFields();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tên tài khoản không tồn tại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                loginJDialog.resetFields();
            }
        }

        
    }
}
