/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.LoaiPhongPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author dong3
 */
public class LoaiPhongDAO {

    private KetNoiCSDL db;

    public LoaiPhongDAO() {
        this.db = new KetNoiCSDL();
    }

    public ArrayList<LoaiPhongPOJO> dsLoaiPhong() {
        ArrayList<LoaiPhongPOJO> dsLPH = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsLoaiPhong");
            while (rs.next()) {
                LoaiPhongPOJO lph = new LoaiPhongPOJO();
                lph.setMaLoaiPhong(rs.getInt("MaLoaiPhong"));
                lph.setTenLoaiPhong(rs.getNString("TenLoaiPhong"));
                lph.setDonGia(rs.getDouble("DonGia"));
                
                dsLPH.add(lph);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsLPH;
    }

}
