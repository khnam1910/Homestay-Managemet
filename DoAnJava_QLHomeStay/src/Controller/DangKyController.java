/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.NhanVienDAO;
import GUI.DangKyDialog;
import GUI.NhanVienJPanel;
import POJO.QuanHuyenPOJO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author dong3
 */
public class DangKyController {

    private DangKyDialog dangKyDialog;
    private NhanVienDAO nhanVienDAO;
    private NhanVienJPanel nhanVienJPanel;

    public DangKyController(DangKyDialog view) {
        this.dangKyDialog = view;
        nhanVienDAO = new NhanVienDAO();

        view.addJbtDKySelectionListener(new JbtDkySelectionListener());
        view.addJbtResetActionListener(new JbtResetSelectionListener());

    }

   
    private class JbtDkySelectionListener implements ActionListener {

        public JbtDkySelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String tentk = dangKyDialog.getJtfTenTK().getText();
            String matkhau = dangKyDialog.getJtfMatKhau().getText();
            String quyen = dangKyDialog.getJrdNVien().isSelected() ? "Nhân viên" : "Quản lý";
            String hoten = dangKyDialog.getJtfHoTen().getText();
            java.util.Date ngaysinh = dangKyDialog.getJdcNgaySinh().getDate();
            String sdt = dangKyDialog.getJtfSDT().getText();
            String gioitinh = dangKyDialog.getJrdNam().isSelected() ? "Nam" : "Nữ";
            QuanHuyenPOJO selectedQH = (QuanHuyenPOJO) dangKyDialog.getJcbDiaChi().getSelectedItem();
            int maqh = selectedQH.getMaQH();
            if(dangKyDialog.isEmpty())
            {
                nhanVienDAO.themNhanVien(tentk,matkhau,quyen,hoten,ngaysinh,sdt,gioitinh,maqh);
//              nhanVienJPanel.loadNhanVien();
                
            }
        }
    }

    private class JbtResetSelectionListener implements ActionListener {

        public JbtResetSelectionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dangKyDialog.reset();
        }
    }

}
