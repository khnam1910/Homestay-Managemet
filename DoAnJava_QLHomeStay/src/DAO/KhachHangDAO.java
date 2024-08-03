/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import POJO.KhachHangPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author dong3
 */
public class KhachHangDAO {

    private final KetNoiCSDL db;

    public KhachHangDAO() {
        db = new KetNoiCSDL();
    }

    public ArrayList<KhachHangPOJO> dsKhachHang() {
        ArrayList<KhachHangPOJO> dsKH = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsKhachHang");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                KhachHangPOJO kh = new KhachHangPOJO();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                Date ngaySinh = rs.getDate("NgaySinh");
                kh.setNgaySinh(sdf.format(ngaySinh));
                kh.setSDT(rs.getString("SDT"));
                kh.setCCCD(rs.getString("CCCD"));
                kh.setTenQH(rs.getString("TenQH"));

                dsKH.add(kh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsKH;
    }

    public void themKhachHang(String tenKH, String gioiTinh, java.util.Date ngaySinh, String sdt, String cccd, int maQH) {

        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("themKhachHang", tenKH, gioiTinh, new java.sql.Date(ngaySinh.getTime()), sdt, cccd, maQH);
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void capNhatKhachHang(int maKH, String tenKH, java.util.Date ngaySinh, String sdt, String cccd, int maQH, String gioiTinh) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("suaKhachHang", maKH, tenKH, gioiTinh, new java.sql.Date(ngaySinh.getTime()), sdt, cccd, maQH);
            JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
            JOptionPane.showMessageDialog(null, "Cập nhật khách hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void xoaKhachHang(int maKH) {
        // Hiển thị hộp thoại xác nhận
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            KetNoiCSDL db = new KetNoiCSDL();
            db.open();
            try {
                db.executeUpdateProcedure("xoaKhachHang", maKH);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        
            } catch (SQLException ex) {
                System.out.println("Lỗi: " + ex);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            db.close();
        }
    }

    public ArrayList<KhachHangPOJO> timKiemKhachHang(String tenKH) {
        ArrayList<KhachHangPOJO> danhSachKH = new ArrayList<>();
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("timKhachHang", tenKH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                KhachHangPOJO kh = new KhachHangPOJO();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setGioiTinh(rs.getString("GioiTinh"));
                Date ngaySinh = rs.getDate("NgaySinh");
                kh.setNgaySinh(sdf.format(ngaySinh));
                kh.setSDT(rs.getString("SDT"));
                kh.setCCCD(rs.getString("CCCD"));
                kh.setTenQH(rs.getString("TenQH"));

                danhSachKH.add(kh);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        }
        db.close();
        return danhSachKH;
    }
    
    public ArrayList<KhachHangPOJO> dsCCCD() {
        ArrayList<KhachHangPOJO> dsCCCDKH = new ArrayList<>();
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("dsCCCD");
            while (rs.next()) {
                KhachHangPOJO kh = new KhachHangPOJO();
                kh.setCCCD(rs.getString("CCCD"));
                kh.setMaKH(rs.getString("MaKH"));

                dsCCCDKH.add(kh);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        }
        db.close();
        return dsCCCDKH;
    } 
}
