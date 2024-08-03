/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class NhanVienPOJO {
    private String MaNV;
    private String TenNV;
    private String GioiTinh;
    private String NgaySinh;
    private String SDT;
    private TaiKhoanPOJO taiKhoanPOJO;
    private String TenQH;

   
   
    public NhanVienPOJO() {
    }

    public NhanVienPOJO(String MaNV, String TenNV, String GioiTinh, String NgaySinh, String SDT, TaiKhoanPOJO taiiKhoanPOJO, String TenQH) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.taiKhoanPOJO = taiiKhoanPOJO;
        this.TenQH = TenQH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
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

    public TaiKhoanPOJO getTaiiKhoanPOJO() {
        return taiKhoanPOJO;
    }

    public void setTaiiKhoanPOJO(TaiKhoanPOJO taiiKhoanPOJO) {
        this.taiKhoanPOJO = taiiKhoanPOJO;
    }

    public String getTenQH() {
        return TenQH;
    }

    public void setTenQH(String TenQH) {
        this.TenQH = TenQH;
    }

   
    
    
    
}
