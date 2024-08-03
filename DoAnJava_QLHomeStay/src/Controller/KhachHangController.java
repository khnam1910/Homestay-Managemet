/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.KhachHangDAO;
import GUI.KhachHangJPanel;
import POJO.KhachHangPOJO;
import POJO.QuanHuyenPOJO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dong3
 */
public class KhachHangController {

    String action = "";

    private final KhachHangJPanel khachHangJPanel;
    private final KhachHangDAO khachHangDAO;

    public KhachHangController(KhachHangJPanel view) {
        this.khachHangJPanel = view;
        khachHangDAO = new KhachHangDAO();

        view.addJtbKHSelectionListener(new JtbKHSelectionListner());
        view.addJbtThemActionListener(new JbtAddSelectionListener());
        view.addJbtSuaActionListener(new JbtUpdateSelectionListener());
        view.addJbtXoaActionListener(new JbtDeleteActionListener());
        view.addJbtLuuActionListener(new JbtSaveSelectionListener());
        view.addJbtResetActionListener(new JbtResetActionListener());
        view.addJbtSearchActionListener(new JbtSearchActionListener());
    }

    public void showKhachHangView() {
        khachHangJPanel.setVisible(true);
    }

    private class JbtSearchActionListener implements ActionListener {

        public JbtSearchActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tenKH = khachHangJPanel.getJtfTuKhoa().getText();
            ArrayList<KhachHangPOJO> danhSachKH = khachHangDAO.timKiemKhachHang(tenKH);
            khachHangJPanel.capNhatDanhSachKhachHang(danhSachKH);
        }
    }

    private class JbtResetActionListener implements ActionListener {

        public JbtResetActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            khachHangJPanel.reset();
        }
    }

    private class JbtSaveSelectionListener implements ActionListener {

        public JbtSaveSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String tenKH = khachHangJPanel.getJtfHoTen().getText();
            String gioiTinh = khachHangJPanel.getJrdNam().isSelected() ? "Nam" : "Nữ";
            java.util.Date ngaySinh = khachHangJPanel.getJdcNgaySinh().getDate();
            String sdt = khachHangJPanel.getJtfSDT().getText();
            String cccd = khachHangJPanel.getJtfCCCD().getText();
            QuanHuyenPOJO selectedQuanHuyen = (QuanHuyenPOJO) khachHangJPanel.getJcbDiaChi().getSelectedItem();
            int maQH = selectedQuanHuyen.getMaQH();

            if (action.equals("")) {
                JOptionPane.showMessageDialog(null, "VUI LÒNG CHỌN YÊU CẦU", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
            else if (khachHangJPanel.isEmpty()) {
                if (action.equals("Them")) {
                    khachHangDAO.themKhachHang(tenKH, gioiTinh, ngaySinh, sdt, cccd, maQH);
                    khachHangJPanel.reset();
                    khachHangJPanel.setEnabled();
                }
                if (action.equals("CapNhat")) {

                    String maKHText = khachHangJPanel.getJtfMaKH().getText();
                    int maKH = Integer.parseInt(maKHText.substring(2));

                    khachHangDAO.capNhatKhachHang(maKH, tenKH, ngaySinh, sdt, cccd, maQH, gioiTinh);
                    khachHangJPanel.reset();
                    khachHangJPanel.setEnabled();
                }
            }
            action = "";
        }
    }

    private class JbtDeleteActionListener implements ActionListener {

        public JbtDeleteActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = khachHangJPanel.getJtbDSKH().getSelectedRow();
            int maKH = Integer.parseInt(khachHangJPanel.getJtfMaKH().getText().substring(2));
            if (selectedRow != -1) {
                khachHangDAO.xoaKhachHang(maKH);
                khachHangJPanel.loadKhachHang();
                khachHangJPanel.reset();
                khachHangJPanel.setEnabled();
            } else {
                JOptionPane.showMessageDialog(null, "VUI LÒNG CHỌN MỘT DÒNG", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        }
    }

    private class JbtUpdateSelectionListener implements ActionListener {

        public JbtUpdateSelectionListener() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "CapNhat";
            khachHangJPanel.setAbled(action);
        }
    }

    private class JbtAddSelectionListener implements ActionListener {

        public JbtAddSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "Them";
            khachHangJPanel.setAbled(action);
            khachHangJPanel.reset();
        }

    }

    private class JtbKHSelectionListner implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            khachHangJPanel.fillText();
            khachHangJPanel.setEnabled();
        }
    }

}
