package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThongKeDAO {
    private KetNoiCSDL db;
    
    public ThongKeDAO(){
        db = new KetNoiCSDL();
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
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return getDsNam;
    }
    
    public ArrayList<Integer> dsThang(int year) throws SQLException {
        ArrayList<Integer> getDsThang = new ArrayList<>();
        db.open();
        
        try {
            ResultSet rs = db.executeProcedure("GetMonthFromHoaDonByYear");
            
            while (rs.next()) {
                int month = rs.getInt("MonthNumber");
                getDsThang.add(month);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        return getDsThang;
    }
}
