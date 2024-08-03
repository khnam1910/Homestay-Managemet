/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class LoaiPhongPOJO {
    private int MaLoaiPhong;
    private String TenLoaiPhong;
    private double DonGia;

    public LoaiPhongPOJO() {
    }

    public LoaiPhongPOJO(int MaLoaiPhong, String TenLoaiPhong, double DonGia) {
        this.MaLoaiPhong = MaLoaiPhong;
        this.TenLoaiPhong = TenLoaiPhong;
        this.DonGia = DonGia;
    }

    public int getMaLoaiPhong() {
        return MaLoaiPhong;
    }

    public void setMaLoaiPhong(int MaLoaiPhong) {
        this.MaLoaiPhong = MaLoaiPhong;
    }

    public String getTenLoaiPhong() {
        return TenLoaiPhong;
    }

    public void setTenLoaiPhong(String TenLoaiPhong) {
        this.TenLoaiPhong = TenLoaiPhong;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    @Override
    public String toString() {
        return this.TenLoaiPhong;
    }
    
    
    
}
