/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.PhongDAO;
import GUI.PhongJPanel;
import POJO.LoaiPhongPOJO;
import POJO.PhongPOJO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dong3
 */
public class PhongController {

    String action = "";
    private final PhongJPanel phongJPanel;
    private final PhongDAO phongDAO;

    public PhongController(PhongJPanel view) {
        this.phongJPanel = view;
        phongDAO = new PhongDAO();

        view.addJtbDSPhongSelectionListener(new JtbDSPhongSelectionListner());
        view.addJbtThemActionListener(new JbtAddSelectionListener());
        view.addJbtSuaActionListener(new JbtUpdateSelectionListener());
        view.addJbtXoaActionListener(new JbtDeleteActionListener());
        view.addJbtLuuActionListener(new JbtSaveSelectionListener());
        view.addJbtResetActionListener(new JbtResetActionListener());
        view.addJbtSearchActionListener(new JbtSearchActionListener());
        view.addJcbLoaiPhongActionListener(new JcbLoaiPhongActionListener());
    }

    public void showPhongView() {
        phongJPanel.setVisible(true);
    }

    private class JcbLoaiPhongActionListener implements ActionListener {

        public JcbLoaiPhongActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LoaiPhongPOJO selectedLoaiPhong = (LoaiPhongPOJO) phongJPanel.getJcbLoaiPhong().getSelectedItem();
            if (selectedLoaiPhong != null) {
                phongJPanel.setJtfDonGia(String.valueOf(selectedLoaiPhong.getDonGia()));
            }
        }
    }

    private class JtbDSPhongSelectionListner implements ListSelectionListener {

        public JtbDSPhongSelectionListner() {

        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            phongJPanel.fillText();
            phongJPanel.setEnabled();
        }
    }

    private class JbtAddSelectionListener implements ActionListener {

        public JbtAddSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "Them";
            phongJPanel.setAbled(action);
            phongJPanel.reset();

        }
    }

    private class JbtUpdateSelectionListener implements ActionListener {

        public JbtUpdateSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "CapNhat";
            phongJPanel.setAbled(action);
        }
    }

    private class JbtDeleteActionListener implements ActionListener {

        public JbtDeleteActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = phongJPanel.getJtbDSPhong().getSelectedRow();

            if (selectedRow != -1) {
                int maPH = Integer.parseInt(phongJPanel.getJtfMaPh().getText().substring(2));
                phongDAO.xoaPhong(maPH);
                phongJPanel.reset();
                phongJPanel.setEnabled();
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
            String TenPh = phongJPanel.getJtfTenPh().getText();
            String TienNghi = phongJPanel.getJtfTienNghi().getText();
            String TrangThai = phongJPanel.getJcbTrangThai().getSelectedItem().toString();
            LoaiPhongPOJO selectedLoaiPh = (LoaiPhongPOJO) phongJPanel.getJcbLoaiPhong().getSelectedItem();
            int maLPH = selectedLoaiPh.getMaLoaiPhong();

            if (action.equals("")) {
                JOptionPane.showMessageDialog(null, "VUI LÒNG CHỌN YÊU CẦU", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else if (phongJPanel.isEmpty()) {
                if (action.equals("Them")) {
                    phongDAO.themPhong(TenPh, maLPH, TienNghi, TrangThai);
                    phongJPanel.reset();
                    phongJPanel.setEnabled();
                }
                if (action.equals("CapNhat")) {
                    String maPhText = phongJPanel.getJtfMaPh().getText();
                    int maPh = Integer.parseInt(maPhText.substring(2));
                    phongDAO.capNhatPhong(maPh, TenPh, maLPH, TienNghi, TrangThai);
                    phongJPanel.reset();
                    phongJPanel.setEnabled();
                }
            }
        }
    }

    private class JbtResetActionListener implements ActionListener {

        public JbtResetActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            phongJPanel.reset();

        }
    }

    private class JbtSearchActionListener implements ActionListener {

        public JbtSearchActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tenPH = phongJPanel.getJtfTuKhoa().getText();
            ArrayList<PhongPOJO> danhSachPH = phongDAO.timKiemPhong(tenPH);
            phongJPanel.capNhatDanhSachPh(danhSachPH);
        }
    }

}
