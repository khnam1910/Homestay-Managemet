/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class PhucVuPOJO {
    private int mapv; 
    private int soluong;
    private DichVuPOJO dichVuPOJO;
    private KhachHangPOJO khachHangPOJO;

    public PhucVuPOJO() {
    }

    public PhucVuPOJO(int mapv, int soluong, DichVuPOJO dichVuPOJO, KhachHangPOJO khachHangPOJO) {
        this.mapv = mapv;
        this.soluong = soluong;
        this.dichVuPOJO = dichVuPOJO;
        this.khachHangPOJO = khachHangPOJO;
    }
    
    
    
    public int getMapv() {
        return mapv;
    }

    public void setMapv(int mapv) {
        this.mapv = mapv;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public DichVuPOJO getDichVuPOJO() {
        return dichVuPOJO;
    }

    public void setDichVuPOJO(DichVuPOJO dichVuPOJO) {
        this.dichVuPOJO = dichVuPOJO;
    }

    public KhachHangPOJO getKhachHangPOJO() {
        return khachHangPOJO;
    }

    public void setKhachHangPOJO(KhachHangPOJO khachHangPOJO) {
        this.khachHangPOJO = khachHangPOJO;
    }
    
    
    
    
}
