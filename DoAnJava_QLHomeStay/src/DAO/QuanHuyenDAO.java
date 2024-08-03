/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.QuanHuyenPOJO;
import java.util.ArrayList;
import java.sql.*;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dong3
 */
public class QuanHuyenDAO {

    private KetNoiCSDL db;

    public QuanHuyenDAO() {
        db = new KetNoiCSDL();
    }

    public ArrayList<QuanHuyenPOJO> dsQuanHuyen() {
        ArrayList<QuanHuyenPOJO> dsQH = new ArrayList<>();
        db.open();

        try {
            ResultSet rs = db.executeProcedure("dsQuanHuyen");

            while (rs.next()) {
                QuanHuyenPOJO qh = new QuanHuyenPOJO();
                qh.setMaQH(rs.getInt("MaQH"));
                qh.setTenQH(rs.getString("TenQH"));

                dsQH.add(qh);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return dsQH;
    }

}
