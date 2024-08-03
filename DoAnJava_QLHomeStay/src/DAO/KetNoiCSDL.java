/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author dong3
 */
public class KetNoiCSDL {

    private Connection conn;
    private CallableStatement csta;
    String strSerName = "DESKTOP-CQLOKB1";
    String strUser = "sa";
    String strPass = "1";
    String strDB = "QL_HomeStay";

    public void open() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException Ex) {
            Logger.getLogger(KetNoiCSDL.class.getName()).log(Level.SEVERE, strDB, Ex);
        }
        String connectionURL = "jdbc:sqlserver://" + strSerName
                + ":1433;databaseName=" + strDB
                + ";user=" + strUser
                + ";password=" + strPass
                + ";TrustServerCertificate = true; Encrypt=false";
        try {
            conn = DriverManager.getConnection(connectionURL);
            if (conn != null) {
                System.out.println("Ket noi thanh cong");
            } else {
                System.out.println("Ket noi that bai");
            }
        } catch (SQLException Ex) {
            Logger.getLogger(KetNoiCSDL.class.getName()).log(Level.SEVERE, strDB, Ex);
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(KetNoiCSDL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeProcedure(String procedureName, Object... params) throws SQLException {
        String sql = "{call " + procedureName + "(";
        for (int i = 0; i < params.length; i++) {
            sql += (i == 0 ? "?" : ", ?");
        }
        sql += ")}";

        csta = conn.prepareCall(sql);
        for (int i = 0; i < params.length; i++) {
            csta.setObject(i + 1, params[i]);
        }

        return csta.executeQuery();
    }

    public void executeUpdateProcedure(String procedureName, Object... params) throws SQLException {
        String sql = "{call " + procedureName + "(";
        for (int i = 0; i < params.length; i++) {
            sql += (i == 0 ? "?" : ", ?");
        }
        sql += ")}";

        csta = conn.prepareCall(sql);
        for (int i = 0; i < params.length; i++) {
            csta.setObject(i + 1, params[i]);
        }

        csta.executeUpdate();
    }

    public Object executeFunction(String functionName, int sqlType, Object... params) throws SQLException {
        String sql = "{? = call " + functionName + "(";
        for (int i = 0; i < params.length; i++) {
            sql += (i == 0 ? "?" : ", ?");
        }
        sql += ")}";

        csta = conn.prepareCall(sql);
        csta.registerOutParameter(1, sqlType);
        for (int i = 0; i < params.length; i++) {
            csta.setObject(i + 2, params[i]);
        }

        csta.execute();
        return csta.getObject(1);
    }
}
