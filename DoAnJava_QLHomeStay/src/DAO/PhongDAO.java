/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.PhongPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;
import javax.swing.JOptionPane;

/**
 *
 * @author dong3
 */
public class PhongDAO {

    private KetNoiCSDL db;

    public PhongDAO() {
        db = new KetNoiCSDL();
    }

    public ArrayList<PhongPOJO> dsPhong() {
        ArrayList<PhongPOJO> dsPh = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsPhong");
            while (rs.next()) {
                PhongPOJO ph = new PhongPOJO();
                ph.setMaPH(rs.getString("Mã Phòng"));
                ph.setLoaiPhong(rs.getString("Tên Loại Phòng"));
                ph.setTenPH(rs.getString("Tên Phòng"));
                ph.setTienNghi(rs.getString("Tiện Nghi"));
                ph.setTrangThai(rs.getNString("Trạng Thái"));
                ph.setDongia(rs.getDouble("Đơn Giá"));

                dsPh.add(ph);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsPh;
    }

    public void themPhong(String TenPh, int maLPH, String TienNghi, String TrangThai) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("themPhong", TenPh, maLPH, TienNghi, TrangThai);
            JOptionPane.showMessageDialog(null, "Thêm phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Thêm phòng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void xoaPhong(int maPH) {

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            KetNoiCSDL db = new KetNoiCSDL();
            db.open();
            try {
                db.executeUpdateProcedure("xoaPhong", maPH);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                System.out.println("Lỗi: " + ex);
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
            db.close();
        }
    }

    public void capNhatPhong(int maPh, String TenPh, int maLPH, String TienNghi, String TrangThai) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("suaPhong", maPh, TenPh, maLPH, TienNghi, TrangThai);
            JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Loi~:" + ex);
            JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public ArrayList<PhongPOJO> timKiemPhong(String tenPh) {
        ArrayList<PhongPOJO> danhSachPh = new ArrayList<>();
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("timKiemPH", tenPh);
            while (rs.next()) {
                PhongPOJO ph = new PhongPOJO();
                ph.setMaPH(rs.getString("MaPhong"));
                ph.setLoaiPhong(rs.getString("TenLoaiPhong"));
                ph.setTenPH(rs.getString("TenPhong"));
                ph.setTienNghi(rs.getString("TienNghi"));
                ph.setTrangThai(rs.getString("TrangThai"));
                ph.setDongia(rs.getDouble("DonGia"));

                danhSachPh.add(ph);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        }
        db.close();
        return danhSachPh;
    }

    public ArrayList<PhongPOJO> dsPhongTrong() {
        ArrayList<PhongPOJO> dsPh = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsPhongTrong");
            while (rs.next()) {
                PhongPOJO ph = new PhongPOJO();
                ph.setMaPH(rs.getString("MaPhong"));
                ph.setLoaiPhong(rs.getString("TenLoaiPhong"));
                ph.setTenPH(rs.getString("TenPhong"));
                ph.setTienNghi(rs.getString("TienNghi"));
                ph.setTrangThai(rs.getString("TrangThai"));
                ph.setDongia(rs.getDouble("DonGia"));

                dsPh.add(ph);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsPh;
    }
}
