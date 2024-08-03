/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import GUI.DichVuJPanel;
import POJO.DichVuPOJO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author dong3
 */
public class DichVuDAO {

    private KetNoiCSDL db;

    public DichVuDAO() {
        this.db = new KetNoiCSDL();
    }

    public ArrayList<DichVuPOJO> dsDV() {
        ArrayList<DichVuPOJO> dsDV = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsDichVu");

            while (rs.next()) {
                DichVuPOJO dv = new DichVuPOJO();
                dv.setMaDV(rs.getString("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));
                dv.setTenLoaiDV(rs.getString("TenLoaiDV"));
                dsDV.add(dv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DichVuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsDV;
    }

    public void themDichVu(String TenDV, int maldv, double dongia) {

        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("themDichVu", TenDV, maldv, dongia);
            JOptionPane.showMessageDialog(null, "Thêm dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Thêm dịch vụ thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void capNhatDichVu(int maDV, String tenDV, double dongia, int maldv) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("suaDichVu", maDV, tenDV, dongia, maldv);
            JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void xoaDichVu(int madv) {
        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            KetNoiCSDL db = new KetNoiCSDL();
            db.open();
            try {
                db.executeUpdateProcedure("xoaDichVu", madv);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                System.out.println("Lỗi: " + ex);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            db.close();
        }
    }

    public ArrayList<DichVuPOJO> timKiemDichVu(String tenDV) {
        ArrayList<DichVuPOJO> danhSachDV = new ArrayList<>();
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("timKiemDV", tenDV);
            while (rs.next()) {
                DichVuPOJO dv = new DichVuPOJO();
                dv.setMaDV(rs.getString("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));
                dv.setTenLoaiDV(rs.getString("TenLoaiDV"));

                danhSachDV.add(dv);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        }
        db.close();
        return danhSachDV;
    }
    
}
