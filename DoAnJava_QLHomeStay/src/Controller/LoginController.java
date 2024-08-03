package Controller;

import DAO.NhanVienDAO;
import GUI.LoginJDialog;
import DAO.TaiKhoanDAO;
import GUI.ChiTietPhongDialog;
import GUI.DangKyDialog;
import GUI.KTPhongJPanel;
import GUI.Main;
import POJO.NhanVienPOJO;
import POJO.TaiKhoanPOJO;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class LoginController {

    private LoginJDialog loginJDialog;
    private TaiKhoanDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;
    private NhanVienPOJO nv;
    private KTPhongJPanel kTPhongJPanel;

    public LoginController(LoginJDialog view) {
        this.loginJDialog = view;
        this.taiKhoanDAO = new TaiKhoanDAO();

        view.addjbDangNhapMouseListener(new jbDangNhapMouseListener());
        view.addJbtTaoTKActionListener(new JbtTaoTKActionlistener());
    }

    private class JbtTaoTKActionlistener implements ActionListener {

        public JbtTaoTKActionlistener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
            
            Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, loginJDialog);
            DangKyDialog dangKyDialog = new DangKyDialog(frame, true);
            dangKyDialog.setVisible(true);
        }
    }

    private class jbDangNhapMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            nhanVienDAO = new NhanVienDAO();
            nv = new NhanVienPOJO();

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

    public NhanVienPOJO getNv() {
        return nv;
    }

    public void setNv(NhanVienPOJO nv) {
        this.nv = nv;
    }

}
