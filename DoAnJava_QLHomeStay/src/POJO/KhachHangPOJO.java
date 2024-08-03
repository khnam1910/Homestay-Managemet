/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class KhachHangPOJO {
   private String MaKH;
   private String TenKH;
   private String GioiTinh;
   private String NgaySinh;
   private String SDT;
   private String CCCD;
   private String TenQH;

    public KhachHangPOJO() {
    }

    public KhachHangPOJO(String MaKH, String TenKH, String GioiTinh, String NgaySinh, String SDT, String CCCD, String TenQH) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.TenQH = TenQH;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getTenQH() {
        return TenQH;
    }

    public void setTenQH(String MaQH) {
        this.TenQH = MaQH;
    }

    @Override
    public String toString() {
        return this.getCCCD();
    }
    
   
    
    
}
