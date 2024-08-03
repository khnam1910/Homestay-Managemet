/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DatPhongDAO;
import GUI.DatPhongJPanel;
import POJO.KhachHangPOJO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dong3
 */
public class DatPhongController {

    DatPhongJPanel datPhongJPanel;
    DatPhongDAO datPhongDAO;

    public DatPhongController(DatPhongJPanel view) {
        this.datPhongJPanel = view;
        datPhongDAO = new DatPhongDAO();

        view.addJtbDSPhongSelectionListener(new JtbDSPhongSelectionListner());
        view.addJtbDSDatPhSelectionListener(new JtbDSDatPhSelectionListner());
        view.addJbtDatPhongSelectionListener(new JbtDatPhongSelectionListener());
        view.addJbtDoiPhongSelectionListener(new JbtDoiPhongSelectionListner());
        view.addJbtLuuSelectionListener(new JbtLuuSelectionListner());
    }

    private class JbtLuuSelectionListner implements ActionListener {

        public JbtLuuSelectionListner() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(datPhongJPanel.getJdcNgayTra().getDate());
            if (datPhongJPanel.isEmpty()) {
                KhachHangPOJO selectedKH = (KhachHangPOJO) datPhongJPanel.getJcbCCCD().getSelectedItem();
                int makh = Integer.parseInt(selectedKH.getMaKH());
                String maPHText = datPhongJPanel.getJtfMaPhong().getText();
                int maPH = Integer.parseInt(maPHText.substring(2));
                Date ngaydat = new Date();
                Date ngaytra = new Date();
                ngaytra = datPhongJPanel.getJdcNgayTra().getDate();
                int songuoi = Integer.parseInt(datPhongJPanel.getJtfSoNguoi().getText());
                datPhongDAO.datPhong(makh, maPH, songuoi, ngaydat, ngaytra);
                datPhongJPanel.loadDSDP();
                datPhongJPanel.loadDSPT();
                datPhongJPanel.setEnaled();
                datPhongJPanel.reset();
            }
        }
    }

    private class JbtDoiPhongSelectionListner implements ActionListener {

        public JbtDoiPhongSelectionListner() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int madp = 0, maPH2 = 0, maPH1 = 0;
            int rowDSDP = datPhongJPanel.getJtbDSDP().getSelectedRow();
            int rowDSPT = datPhongJPanel.getJtbDSPT().getSelectedRow();
            if (rowDSDP != -1) {
                String maDPText = datPhongJPanel.getMaDpList().get(rowDSDP);
                String maPh1Text = datPhongJPanel.getMaPhList1().get(rowDSDP);
                madp = Integer.parseInt(maDPText.substring(2));
                maPH1 = Integer.parseInt(maPh1Text.substring(2));
            }
            if (rowDSPT != -1) {
                String maPh2Text = datPhongJPanel.getMaPhList2().get(rowDSPT);
                maPH2 = Integer.parseInt(maPh2Text.substring(2));
            }
            if (madp == 0 && maPH2 == 0 && maPH1 == 0) {
                return;
            } else {
                datPhongDAO.doiPhong(madp, maPH1, maPH2);
                datPhongJPanel.loadDSDP();
                datPhongJPanel.loadDSPT();
                datPhongJPanel.setEnaled();
                datPhongJPanel.reset();
                madp = 0;
                maPH2 = 0;
                maPH1 = 0;
            }
        }
    }

    private class JbtDatPhongSelectionListener implements ActionListener {

        public JbtDatPhongSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            datPhongJPanel.getJbtLuu().setEnabled(true);
            datPhongJPanel.getJtfSoNguoi().setText("");
            datPhongJPanel.getJdcNgayTra().setDate(null);
            datPhongJPanel.setAbleDSPT();
        }
    }

    private class JtbDSPhongSelectionListner implements ListSelectionListener {

        public JtbDSPhongSelectionListner() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            datPhongJPanel.filltextPH();
            datPhongJPanel.getJtfSoNguoi().setText("");
            datPhongJPanel.getJdcNgayDat().setDate(null);
            datPhongJPanel.getJdcNgayTra().setDate(null);
        }
    }

    private class JtbDSDatPhSelectionListner implements ListSelectionListener {

        public JtbDSDatPhSelectionListner() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            datPhongJPanel.setAbleDSDP();
            datPhongJPanel.filltextDP();
        }
    }

}
