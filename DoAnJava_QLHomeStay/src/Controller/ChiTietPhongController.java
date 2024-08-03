/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.HoaDonDAO;
import DAO.NhanVienDAO;
import GUI.ChiTietPhongDialog;
import GUI.ChiTietThanhToanDialog;
import GUI.LoginJDialog;
import GUI.ThemDichVuDialog;
import POJO.NhanVienPOJO;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author dong3
 */
public class ChiTietPhongController {

    NhanVienDAO nhanVienDAO;
    NhanVienPOJO nhanVienPOJO;
    HoaDonDAO hoaDonDAO;
    ChiTietPhongDialog chiTietPhongDialog;
    LoginController loginController;
    int manv;
    int maph;
    int makh;
    String username;

    public ChiTietPhongController(ChiTietPhongDialog view) {
        this.chiTietPhongDialog = view;
        username = chiTietPhongDialog.getUsername();
        view.addJbtThemDVActionListner(new JbtThemDVActionListner());
        view.addJbtThanhToanActionListner(new JbtThanhToanActionListner());
    }

    private class JbtThanhToanActionListner implements ActionListener {

        public JbtThanhToanActionListner() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            nhanVienDAO = new NhanVienDAO();
            username = chiTietPhongDialog.getUsername();

            manv = Integer.parseInt(nhanVienDAO.layMaNV(username).getMaNV());

            if (manv == 0) {
                JOptionPane.showMessageDialog(chiTietPhongDialog, "VUI LÒNG ĐĂNG NHẬP", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                maph = chiTietPhongDialog.getMaph();
                makh = chiTietPhongDialog.getMakh();
                System.out.println(maph);
                Date ngayLapHD = new Date();
                hoaDonDAO = new HoaDonDAO();
                boolean checkThanhToan = hoaDonDAO.ThanhToan(makh, manv, maph, ngayLapHD);
                if (checkThanhToan == true) {
                    JOptionPane.showMessageDialog(chiTietPhongDialog, "Thanh toán thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, chiTietPhongDialog);
                    ChiTietThanhToanDialog chiTietThanhToanDialog = new ChiTietThanhToanDialog(frame, true,maph, makh);
                    chiTietThanhToanDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(chiTietPhongDialog, "Thanh toán thất bại", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            }

        }
    }

    private class JbtThemDVActionListner implements ActionListener {

        public JbtThemDVActionListner() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, chiTietPhongDialog);
            ThemDichVuDialog themDichVuDialog = new ThemDichVuDialog(frame, true, chiTietPhongDialog.getMakh());
//            themDichVuDialog.setVisible(true);
            if (!themDichVuDialog.isVisible()) {
                chiTietPhongDialog.dispose();
                themDichVuDialog.setVisible(true);
            }
            if (!themDichVuDialog.isVisible()) {
                System.out.println(chiTietPhongDialog.getMaph());
                System.out.println(chiTietPhongDialog.getMakh());
                chiTietPhongDialog = new ChiTietPhongDialog(frame, true, chiTietPhongDialog.getMaph(), chiTietPhongDialog.getMakh(), username);
                chiTietPhongDialog.setVisible(true);
            }

        }
    }

}
