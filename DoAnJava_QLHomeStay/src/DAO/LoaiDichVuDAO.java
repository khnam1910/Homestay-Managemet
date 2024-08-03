/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.LoaiDichVuPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;
/**
 *
 * @author dong3
 */
public class LoaiDichVuDAO {
    KetNoiCSDL db;

    public LoaiDichVuDAO() {
        this.db = new KetNoiCSDL();
    }
    
      public ArrayList<LoaiDichVuPOJO> dsQuanHuyen() {
        ArrayList<LoaiDichVuPOJO> dsLDV = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsLoaiDV");

            while (rs.next()) {
                LoaiDichVuPOJO ldv = new LoaiDichVuPOJO();
                ldv.setMaLoaiDV(rs.getInt("MaLoaiDV"));
                ldv.setTenLoaiDV(rs.getString("TenLoaiDV"));

                dsLDV.add(ldv);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsLDV;
    }
    
}
