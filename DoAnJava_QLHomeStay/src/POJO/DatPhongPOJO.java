/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class DatPhongPOJO {
    private String MaDP;
    private String NgayDat;
    private String NgayTra;
    private int SoNguoi;
    private PhongPOJO phongPOJO;
    private KhachHangPOJO khachHangPOJO;

    public DatPhongPOJO() {
    }

    public DatPhongPOJO(String MaDP, String NgayDat, String NgayTra, int SoNguoi, PhongPOJO phongPOJO, KhachHangPOJO khachHangPOJO) {
        this.MaDP = MaDP;
        this.NgayDat = NgayDat;
        this.NgayTra = NgayTra;
        this.SoNguoi = SoNguoi;
        this.phongPOJO = phongPOJO;
        this.khachHangPOJO = khachHangPOJO;
    }

    public String getMaDP() {
        return MaDP;
    }

    public void setMaDP(String MaDP) {
        this.MaDP = MaDP;
    }

    public String getNgayDat() {
        return NgayDat;
    }

    public void setNgayDat(String NgayDat) {
        this.NgayDat = NgayDat;
    }

    public String getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(String NgayTra) {
        this.NgayTra = NgayTra;
    }

    public int getSoNguoi() {
        return SoNguoi;
    }

    public void setSoNguoi(int SoNguoi) {
        this.SoNguoi = SoNguoi;
    }

    public PhongPOJO getPhongPOJO() {
        return phongPOJO;
    }

    public void setPhongPOJO(PhongPOJO phongPOJO) {
        this.phongPOJO = phongPOJO;
    }

    public KhachHangPOJO getKhachHangPOJO() {
        return khachHangPOJO;
    }

    public void setKhachHangPOJO(KhachHangPOJO khachHangPOJO) {
        this.khachHangPOJO = khachHangPOJO;
    }
    
    
    
}
