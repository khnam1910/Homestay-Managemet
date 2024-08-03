/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class HoaDonPOJO {

    private String mahd;
    private String NgayLapHD;
    private double TongTien;
    private KhachHangPOJO khachHangPOJO;
    private NhanVienPOJO nhanVienPOJO;
    private PhongPOJO phongPOJO;
    private DatPhongPOJO datPhongPOJO;
    private int TongSoPhong;
    private double TongTienDV;

    public HoaDonPOJO() {
    }

    public HoaDonPOJO(String mahd, String NgayLapHD, double TongTien, KhachHangPOJO khachHangPOJO, NhanVienPOJO nhanVienPOJO, PhongPOJO phongPOJO, DatPhongPOJO datPhongPOJO) {
        this.mahd = mahd;
        this.NgayLapHD = NgayLapHD;
        this.TongTien = TongTien;
        this.khachHangPOJO = khachHangPOJO;
        this.nhanVienPOJO = nhanVienPOJO;
        this.phongPOJO = phongPOJO;
        this.datPhongPOJO = datPhongPOJO;
    }

    public String getMahd() {
        return mahd;
    }

    public void setMahd(String mahd) {
        this.mahd = mahd;
    }

    public String getNgayLapHD() {
        return NgayLapHD;
    }

    public void setNgayLapHD(String NgayLapHD) {
        this.NgayLapHD = NgayLapHD;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien = TongTien;
    }

    public KhachHangPOJO getKhachHangPOJO() {
        return khachHangPOJO;
    }

    public void setKhachHangPOJO(KhachHangPOJO khachHangPOJO) {
        this.khachHangPOJO = khachHangPOJO;
    }

    public NhanVienPOJO getNhanVienPOJO() {
        return nhanVienPOJO;
    }

    public void setNhanVienPOJO(NhanVienPOJO nhanVienPOJO) {
        this.nhanVienPOJO = nhanVienPOJO;
    }

    public PhongPOJO getPhongPOJO() {
        return phongPOJO;
    }

    public void setPhongPOJO(PhongPOJO phongPOJO) {
        this.phongPOJO = phongPOJO;
    }

    public DatPhongPOJO getDatPhongPOJO() {
        return datPhongPOJO;
    }

    public void setDatPhongPOJO(DatPhongPOJO datPhongPOJO) {
        this.datPhongPOJO = datPhongPOJO;
    }

    public int getTongSoPhong() {
        return TongSoPhong;
    }

    public void setTongSoPhong(int TongSoPhong) {
        this.TongSoPhong = TongSoPhong;
    }

    public double getTongTienDV() {
        return TongTienDV;
    }

    public void setTongTienDV(double TongTienDV) {
        this.TongTienDV = TongTienDV;
    }

}
