/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.DichVuPOJO;
import POJO.PhucVuPOJO;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import javax.crypto.AEADBadTagException;

/**
 *
 * @author dong3
 */
public class PhucVuDAO {

    private KetNoiCSDL db;

    public PhucVuDAO() {
        this.db = new KetNoiCSDL();
    }

    public void themPhucVu(int madv, int makh, int soluong) {
        db = new KetNoiCSDL();
        db.open();

        try {
            db.executeUpdateProcedure("ThemPhucVu", madv, makh, soluong);
            JOptionPane.showMessageDialog(null, "Order thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            System.out.println("Lỗi:" + ex);
            JOptionPane.showMessageDialog(null, "Order thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ArrayList<PhucVuPOJO> thongTinPhucVu(int maph, int makh) {
        ArrayList<PhucVuPOJO> dsPV = new ArrayList<>();
        db.open();
        try {
            ResultSet rs = db.executeProcedure("thongTinPhucVu", maph, makh);

            while (rs.next()) {
                PhucVuPOJO pv = new PhucVuPOJO();
                DichVuPOJO dv = new DichVuPOJO();
                dv.setDonGia(rs.getDouble("DonGia"));
                dv.setTenDV(rs.getString("TenDV"));
                pv.setSoluong(rs.getInt("SoLuong"));
                pv.setDichVuPOJO(dv);

                dsPV.add(pv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DichVuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsPV;
    }
}
