/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class DichVuPOJO 
{
    private String MaDV;
    private String TenDV;
    private double DonGia;
    private String TenLoaiDV;

    public DichVuPOJO() {
    }

    public DichVuPOJO(String MaDV, String TenDV, double DonGia, String TenLoaiDV) {
        this.MaDV = MaDV;
        this.TenDV = TenDV;
        this.DonGia = DonGia;
        this.TenLoaiDV = TenLoaiDV;
    }

    public String getMaDV() {
        return MaDV;
    }

    public void setMaDV(String MaDV) {
        this.MaDV = MaDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double DonGia) {
        this.DonGia = DonGia;
    }

    public String getTenLoaiDV() {
        return TenLoaiDV;
    }

    public void setTenLoaiDV(String TenLoaiDV) {
        this.TenLoaiDV = TenLoaiDV;
    }
    
    
}
