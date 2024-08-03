/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.NhanVienPOJO;
import POJO.TaiKhoanPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author dong3
 */
public class NhanVienDAO {

    private final KetNoiCSDL db;

    public NhanVienDAO() {
        db = new KetNoiCSDL();
    }

    public ArrayList<NhanVienPOJO> dsNhanVien() {
        ArrayList<NhanVienPOJO> dsNV = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsNhanVien");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                NhanVienPOJO nv = new NhanVienPOJO();
                TaiKhoanPOJO tk = new TaiKhoanPOJO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                Date ngaysinh = rs.getDate("NgaySinh");
                nv.setNgaySinh(sdf.format(ngaysinh));
                nv.setSDT(rs.getString("SDT"));
                tk.setQuyen(rs.getString("Quyen"));
                nv.setTaiiKhoanPOJO(tk);
                nv.setTenQH(rs.getString("TenQH"));
                dsNV.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsNV;
    }

    public void themNhanVien(String tentk, String matkhau, String quyen, String hoten, java.util.Date ngaysinh, String sdt, String gioitinh, int maqh) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("themNhanVien", hoten, new java.sql.Date(ngaysinh.getTime()), sdt, maqh, gioitinh, tentk, matkhau, quyen);
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Thêm nhân viên thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void suaNhanVien(int manv, String hoten, java.util.Date ngaysinh, String sdt, String gioitinh, int maqh) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("suaNhanVien", manv, hoten, new java.sql.Date(ngaysinh.getTime()), sdt, gioitinh, maqh);
            JOptionPane.showMessageDialog(null, "Sửa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Sửa nhân viên thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void xoaKhachHang(int maNV) {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            KetNoiCSDL db = new KetNoiCSDL();
            db.open();
            try {
                db.executeUpdateProcedure("xoaNhanVien", maNV);
                JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                System.out.println("Lỗi: " + ex);
                JOptionPane.showMessageDialog(null, "Xóa nhân viên thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            db.close();
        }
    }

    public ArrayList<NhanVienPOJO> timKiemNhanVien(String tenNV) {
        ArrayList<NhanVienPOJO> dsNV = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("timkiemNV", tenNV);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                NhanVienPOJO nv = new NhanVienPOJO();
                TaiKhoanPOJO tk = new TaiKhoanPOJO();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setGioiTinh(rs.getString("GioiTinh"));
                Date ngaysinh = rs.getDate("NgaySinh");
                nv.setNgaySinh(sdf.format(ngaysinh));
                nv.setSDT(rs.getString("SDT"));
                tk.setQuyen(rs.getString("Quyen"));
                nv.setTaiiKhoanPOJO(tk);
                nv.setTenQH(rs.getString("TenQH"));
                dsNV.add(nv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsNV;
    }

    public NhanVienPOJO layMaNV(String username) {
        NhanVienPOJO nv = new NhanVienPOJO();
        db.open();
        
        try {
            ResultSet rs = db.executeProcedure("layMaNV", username);
            
            while (rs.next()) 
            {
                nv.setMaNV(rs.getString("MaNV"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return nv;
    }
}
