/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DichVuDAO;
import GUI.DichVuJPanel;
import POJO.DichVuPOJO;
import POJO.LoaiDichVuPOJO;
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
public class DichVuController {

    String action = "";
    private DichVuJPanel dichVuJPanel;
    private DichVuDAO dichVuDAO;

    public DichVuController(DichVuJPanel view) {
        this.dichVuJPanel = view;
        dichVuDAO = new DichVuDAO();
        dichVuJPanel.setEnabled();

        view.addJtbDVSelectionListener(new JtbDVSelectionListner());
        view.addJbtThemActionListener(new JbtAddSelectionListener());
        view.addJbtSuaActionListener(new JbtUpdateSelectionListener());
        view.addJbtXoaActionListener(new JbtDeleteActionListener());
        view.addJbtLuuActionListener(new JbtSaveSelectionListener());
        view.addJbtResetActionListener(new JbtResetActionListener());
        view.addJbtSearchActionListener(new JbtSearchActionListener());
    }

    public void showDichVuView() {
        dichVuJPanel.setVisible(true);
    }

    private class JtbDVSelectionListner implements ListSelectionListener {

        public JtbDVSelectionListner() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            dichVuJPanel.fillText();
        }
    }

    private class JbtAddSelectionListener implements ActionListener {

        public JbtAddSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "Them";
            dichVuJPanel.setAbled(action);
            dichVuJPanel.reset();
        }
    }

    private class JbtUpdateSelectionListener implements ActionListener {

        public JbtUpdateSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action = "CapNhat";
            dichVuJPanel.setAbled(action);
        }
    }

    private class JbtDeleteActionListener implements ActionListener {

        public JbtDeleteActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = dichVuJPanel.getJtbDSDV().getSelectedRow();

            if (selectedRow != -1) {
                int madv = Integer.parseInt(dichVuJPanel.getJtfMaDV().getText().substring(2));
                dichVuDAO.xoaDichVu(madv);
                dichVuJPanel.reset();
                dichVuJPanel.setEnabled();
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

            String tenDV = dichVuJPanel.getJtfTenDV().getText();

            LoaiDichVuPOJO selectedLDV = (LoaiDichVuPOJO) dichVuJPanel.getJcbLoaiDV().getSelectedItem();
            int maldv = selectedLDV.getMaLoaiDV();

            if (action.equals("")) {
                JOptionPane.showMessageDialog(null, "VUI LÒNG CHỌN YÊU CẦU", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else if (dichVuJPanel.isEmpty()) {
                double dongia = Double.parseDouble(dichVuJPanel.getJtfDonGia().getText());
                if (action.equals("Them")) {
                    dichVuDAO.themDichVu(tenDV, maldv, dongia);
                    dichVuJPanel.reset();
                    dichVuJPanel.setEnabled();
                }
                if (action.equals("CapNhat")) {

                    String maDVText = dichVuJPanel.getJtfMaDV().getText();
                    int maDV = Integer.parseInt(maDVText.substring(2));
                    dichVuDAO.capNhatDichVu(maDV, tenDV, dongia, maldv);
                    dichVuJPanel.reset();
                    dichVuJPanel.setEnabled();
                }

            }
        }
    }

    private class JbtResetActionListener implements ActionListener {

        public JbtResetActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dichVuJPanel.reset();
        }
    }

    private class JbtSearchActionListener implements ActionListener {

        public JbtSearchActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tenDV = dichVuJPanel.getJtfTuKhoa().getText();
            ArrayList<DichVuPOJO> danhSachDV = dichVuDAO.timKiemDichVu(tenDV);
            dichVuJPanel.capNhatDanhSachDV(danhSachDV);
        }
    }

}
