/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.DatPhongPOJO;
import POJO.HoaDonPOJO;
import POJO.KhachHangPOJO;
import POJO.NhanVienPOJO;
import POJO.PhongPOJO;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.Date;

/**
 *
 * @author dong3
 */
public class HoaDonDAO {

    private KetNoiCSDL db;

    public HoaDonDAO() {
        this.db = new KetNoiCSDL();
    }

    public boolean ThanhToan(int makh, int manv, int maph, Date ngayLapHD) {
        db.open();
        try {
            java.sql.Date sqlDate = new java.sql.Date(ngayLapHD.getTime());
            db.executeUpdateProcedure("sp_ThanhToan", makh, manv, maph, sqlDate);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public HoaDonPOJO getHoaDonInfo(int MaKH, int MaPhong) {
        HoaDonPOJO hoaDon = new HoaDonPOJO();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("sp_ChiTietHoaDon", MaKH, MaPhong);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (rs.next()) {
                NhanVienPOJO nv = new NhanVienPOJO();
                KhachHangPOJO kh = new KhachHangPOJO();
                PhongPOJO ph = new PhongPOJO();
                DatPhongPOJO dp = new DatPhongPOJO();

                hoaDon.setMahd(rs.getString("MaHD"));
                nv.setTenNV(rs.getString("TenNV"));
                hoaDon.setNhanVienPOJO(nv);
                hoaDon.setNgayLapHD(sdf.format(rs.getDate("NgayLapHD")));
                hoaDon.setTongTien(rs.getDouble("TongTien"));

                kh.setTenKH(rs.getString("TenKH"));
                hoaDon.setKhachHangPOJO(kh);

                String maPhong = rs.getString("MaPhong");
                if (maPhong != null) {
                    ph.setMaPH(maPhong);
//                    dp.setPhongPOJO(ph);
                    hoaDon.setPhongPOJO(ph);
                } else {
                    System.out.println("loi~");
                }

                dp.setNgayDat(sdf.format(rs.getDate("NgayDatPhong")));
                dp.setNgayTra(sdf.format(rs.getDate("NgayTraPhong")));
                hoaDon.setDatPhongPOJO(dp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return hoaDon;
    }

    public ArrayList<HoaDonPOJO> dsPhong() {
        ArrayList<HoaDonPOJO> dsHD = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsHoaDon");
            while (rs.next()) {
                HoaDonPOJO hd = new HoaDonPOJO();
                hd.setMahd(rs.getString("Mã Hóa Đơn"));
                hd.setNgayLapHD(rs.getString("Ngày Lập HĐ"));
                hd.setTongTien(rs.getDouble("Tổng Tiền"));

                NhanVienPOJO nhanVien = new NhanVienPOJO();
                nhanVien.setMaNV(rs.getString("Mã NV"));
                hd.setNhanVienPOJO(nhanVien);

                KhachHangPOJO khachHang = new KhachHangPOJO();
                khachHang.setMaKH(rs.getString("Mã KH"));
                hd.setKhachHangPOJO(khachHang);

                PhongPOJO phong = new PhongPOJO();
                phong.setMaPH(rs.getString("Mã Phòng"));
                hd.setPhongPOJO(phong);

                dsHD.add(hd);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsHD;
    }

    public ArrayList<Integer> dsNam() throws SQLException {
        ArrayList<Integer> getDsNam = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("GetYearFromHoaDon");

            while (rs.next()) {
                int year = rs.getInt("YearNumber");
                getDsNam.add(year);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return getDsNam;
    }

    public HoaDonPOJO getTongTien(int year) {
        HoaDonPOJO tongtien = null;
        db.open();
        try {
            ResultSet rs = db.executeProcedure("TinhTongTienHoaDonTheoNam", year);
            if (rs.next()) {
                tongtien = new HoaDonPOJO();
                tongtien.setTongTien(rs.getDouble("TongTienHoaDon"));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            db.close();
        }
        return tongtien;
    }

    public HoaDonPOJO getTongSoPhong(int year) {
        HoaDonPOJO tongPhong = null;
        db.open();
        try {
            ResultSet rs = db.executeProcedure("TinhTongSoPhongDuocChoThue", year);
            if (rs.next()) {
                tongPhong = new HoaDonPOJO();
                tongPhong.setTongSoPhong(rs.getInt("TongSoPhong"));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            db.close();
        }
        return tongPhong;
    }

    public HoaDonPOJO getTongTienDichVu(int year) throws SQLException {
        HoaDonPOJO tongTienDichVu = null;
        db.open();
        try {
            ResultSet rs = db.executeProcedure("TinhTongTienDichVuTheoNam", year);
            if (rs.next()) {
                tongTienDichVu = new HoaDonPOJO();
                tongTienDichVu.setTongTienDV(rs.getDouble("TongTienDichVu"));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            db.close();
        }
        return tongTienDichVu;
    }

}
