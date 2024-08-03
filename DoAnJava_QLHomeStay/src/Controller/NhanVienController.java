/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.NhanVienDAO;
import GUI.DangKyDialog;
import GUI.NhanVienJPanel;
import POJO.NhanVienPOJO;
import POJO.QuanHuyenPOJO;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dong3
 */
public class NhanVienController {

    String action = "";

    private NhanVienJPanel nhanVienJPanel;
    private NhanVienDAO nhanVienDAO;

    public NhanVienController(NhanVienJPanel view) {
        this.nhanVienJPanel = view;
        nhanVienDAO = new NhanVienDAO();

        view.addJtbNVSelectionListener(new JtbNVSelectionListner());
        view.addJbtThemActionListener(new JbtAddSelectionListener());
        view.addJbtSuaActionListener(new JbtUpdateSelectionListener());
        view.addJbtXoaActionListener(new JbtDeleteActionListener());
        view.addJbtLuuActionListener(new JbtSaveSelectionListener());
        view.addJbtResetActionListener(new JbtResetActionListener());
        view.addJbtSearchActionListener(new JbtSearchActionListener());
    }

    public void showNhanVienView() {
        nhanVienJPanel.setVisible(true);
    }

    private class JtbNVSelectionListner implements ListSelectionListener {

        public JtbNVSelectionListner() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            nhanVienJPanel.fillText();
            nhanVienJPanel.setEnabled();
        }
    }

    private class JbtAddSelectionListener implements ActionListener {

        public JbtAddSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "Them";
            nhanVienJPanel.setAbled(action);
            nhanVienJPanel.reset();
            Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, nhanVienJPanel);
            DangKyDialog dkDialog = new DangKyDialog(frame, true);
            dkDialog.setVisible(true);
        }
    }

    private class JbtUpdateSelectionListener implements ActionListener {

        public JbtUpdateSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "CapNhat";
            nhanVienJPanel.setAbled(action);

        }
    }

    private class JbtDeleteActionListener implements ActionListener {

        public JbtDeleteActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = nhanVienJPanel.getJtbDSNV().getSelectedRow();
            int maNV = Integer.parseInt(nhanVienJPanel.getJtfMaNV().getText().substring(2));
            if (selectedRow != -1) {
                nhanVienDAO.xoaKhachHang(maNV);
                nhanVienJPanel.reset();
                nhanVienJPanel.setEnabled();
            } else {
                JOptionPane.showMessageDialog(null, "VUI LÒNG CHỌN MỘT DÒNG", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class JbtSaveSelectionListener implements ActionListener {

        public JbtSaveSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String hoten = nhanVienJPanel.getJtfHoTen().getText();
            java.util.Date ngaysinh = nhanVienJPanel.getJdcNgaySinh().getDate();
            String sdt = nhanVienJPanel.getJtfSDT().getText();
            String gioitinh = nhanVienJPanel.getJrdNam().isSelected() ? "Nam" : "Nữ";
            QuanHuyenPOJO selectedQH = (QuanHuyenPOJO) nhanVienJPanel.getJcbDiaChi().getSelectedItem();
            int maqh = selectedQH.getMaQH();

            if (nhanVienJPanel.isEmpty()) {
                String maNVText = nhanVienJPanel.getJtfMaNV().getText();
                int maNV = Integer.parseInt(maNVText.substring(2));
                nhanVienDAO.suaNhanVien(maNV, hoten, ngaysinh, sdt, gioitinh, maqh);
                nhanVienJPanel.reset();
            }
        }
    }

    private class JbtResetActionListener implements ActionListener {

        public JbtResetActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            nhanVienJPanel.reset();
        }
    }

    private class JbtSearchActionListener implements ActionListener {

        public JbtSearchActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tenNV = nhanVienJPanel.getJtfTuKhoa().getText();
            ArrayList<NhanVienPOJO> danhSachNV = nhanVienDAO.timKiemNhanVien(tenNV);
            nhanVienJPanel.capNhatDanhSachNhanVien(danhSachNV);
        }
    }
}
