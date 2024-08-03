/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.DatPhongPOJO;
import POJO.KhachHangPOJO;
import POJO.PhongPOJO;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author dong3
 */
public class DatPhongDAO {

    private KetNoiCSDL db;

    public DatPhongDAO() {
        db = new KetNoiCSDL();
    }

    public ArrayList<DatPhongPOJO> dsDatPhong() {
        ArrayList<DatPhongPOJO> dsDP = new ArrayList<>();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("dsDatPhong");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                DatPhongPOJO dp = new DatPhongPOJO();
                KhachHangPOJO kh = new KhachHangPOJO();
                PhongPOJO ph = new PhongPOJO();
                dp.setMaDP(rs.getString("MaDP"));

                // Kiểm tra giá trị null trước khi gán
                if (rs.getString("CCCD") != null) {
                    kh.setCCCD(rs.getString("CCCD"));
                    dp.setKhachHangPOJO(kh);
                }

                if (rs.getString("MaPhong") != null) {
                    ph.setMaPH(rs.getString("MaPhong"));
                    ph.setTenPH(rs.getString("TenPhong"));
                    ph.setDongia(rs.getDouble("DonGia"));
                    dp.setPhongPOJO(ph);
                }
                Date ngayDat = new Date(rs.getDate("NgayDatPhong").getTime());
                Date ngayTra = rs.getDate("NgayTraPhong") != null ? new Date(rs.getDate("NgayTraPhong").getTime()) : null;

                dp.setNgayDat(sdf.format(ngayDat));
                dp.setNgayTra(ngayTra != null ? sdf.format(ngayTra) : null);
                dp.setSoNguoi(rs.getInt("SoNguoi"));
                dsDP.add(dp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsDP;
    }

    public void datPhong(int maKH, int maPH, int soNguoi, java.util.Date ngayDat, java.util.Date ngayTra) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            db.executeUpdateProcedure("xuLyDatPhong", maKH, maPH, soNguoi, new java.sql.Date(ngayDat.getTime()), new java.sql.Date(ngayTra.getTime()));
            JOptionPane.showMessageDialog(null, "Đặt phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
            JOptionPane.showMessageDialog(null, "Đặt phòng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
        db.close();
    }

    public void doiPhong(int madp, int maPH1, int maPH2) {
        KetNoiCSDL db = new KetNoiCSDL();
        db.open();
        try {
            // Gọi stored procedure XuLyDoiPhong
            db.executeUpdateProcedure("XuLyDoiPhong", madp, maPH1, maPH2);
            JOptionPane.showMessageDialog(null, "Đổi phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
            JOptionPane.showMessageDialog(null, "Đổi phòng thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        } finally {
            db.close(); // Đảm bảo đóng kết nối sau khi thực thi
        }
    }

    public ArrayList<DatPhongPOJO> ChiTietPhong(int maph) {
        ArrayList<DatPhongPOJO> chitietphong = new ArrayList<>();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("thongTinChiTietPhong", maph);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                DatPhongPOJO dp = new DatPhongPOJO();
                KhachHangPOJO kh = new KhachHangPOJO();
                PhongPOJO ph = new PhongPOJO();
                
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getString("TenKH"));
                dp.setKhachHangPOJO(kh);
                                
                ph.setDongia(rs.getDouble("DonGia"));
                dp.setPhongPOJO(ph);

                Date ngayDat = new Date(rs.getDate("NgayDatPhong").getTime());
                Date ngayTra = new Date(rs.getDate("NgayTraPhong").getTime());

                dp.setNgayDat(sdf.format(ngayDat));
                dp.setNgayTra(sdf.format(ngayTra));
                dp.setSoNguoi(rs.getInt("SoNguoi"));
                chitietphong.add(dp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return chitietphong;
    }

}
