/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class PhongPOJO {
    private String MaPH;
    private String TenPH;
    private double dongia;
    private String TienNghi;
    private String TrangThai;
    private String LoaiPhong;

    public PhongPOJO() {
    }

    public PhongPOJO(String MaPH, String TenPH, double dongia, String TienNghi, String TrangThai, String LoaiPhong) {
        this.MaPH = MaPH;
        this.TenPH = TenPH;
        this.dongia = dongia;
        this.TienNghi = TienNghi;
        this.TrangThai = TrangThai;
        this.LoaiPhong = LoaiPhong;
    }

   

    public String getMaPH() {
        return MaPH;
    }

    public void setMaPH(String MaPH) {
        this.MaPH = MaPH;
    }

    public String getTenPH() {
        return TenPH;
    }

    public void setTenPH(String TenPH) {
        this.TenPH = TenPH;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    public String getTienNghi() {
        return TienNghi;
    }

    public void setTienNghi(String TienNghi) {
        this.TienNghi = TienNghi;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getLoaiPhong() {
        return LoaiPhong;
    }

    public void setLoaiPhong(String LoaiPhong) {
        this.LoaiPhong = LoaiPhong;
    }

    @Override
    public String toString() {
        return this.getTenPH();
    }
    
    
    
}
