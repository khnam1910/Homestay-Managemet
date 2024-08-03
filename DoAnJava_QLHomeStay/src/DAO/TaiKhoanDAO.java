package DAO;

import POJO.TaiKhoanPOJO;
import java.sql.*;
import javax.swing.JOptionPane;

public class TaiKhoanDAO {

    private KetNoiCSDL ketNoiCSDL;

    public TaiKhoanDAO() {
        ketNoiCSDL = new KetNoiCSDL();
    }

    public boolean checkUsername(String username) {
        boolean exists = false;
        ketNoiCSDL.open();
        try {
            ResultSet rs = ketNoiCSDL.executeProcedure("KiemTraTaiKhoan", username);
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            ketNoiCSDL.close();
        }
        return exists;
    }

    public boolean checkPassword(String username, String password) {
        boolean valid = false;
        ketNoiCSDL.open();
        try {
            ResultSet rs = ketNoiCSDL.executeProcedure("KiemTraMatKhau", username, password);
            if (rs.next() && rs.getInt(1) > 0) {
                valid = true;
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            ketNoiCSDL.close();
        }
        return valid;
    }

    public String getQuyen(String username) {
        String quyen = null;
        ketNoiCSDL.open();
        try {
            ResultSet rs = ketNoiCSDL.executeProcedure("getQuyen", username);
            if (rs.next()) {
                quyen = rs.getString("Quyen");
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            ketNoiCSDL.close();
        }
        return quyen;
    }

    public TaiKhoanPOJO getNV(String username) {
        TaiKhoanPOJO taikhoan = null;
        ketNoiCSDL.open();
        try {
            ResultSet rs = ketNoiCSDL.executeProcedure("LayMaNV", username);
            if (rs.next()) {
                taikhoan = new TaiKhoanPOJO();
                taikhoan.setTenNV(rs.getString("TenNV"));
                taikhoan.setMaNV(rs.getString("MaNV"));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex);
        } finally {
            ketNoiCSDL.close();
        }
        return taikhoan;
    }
}
