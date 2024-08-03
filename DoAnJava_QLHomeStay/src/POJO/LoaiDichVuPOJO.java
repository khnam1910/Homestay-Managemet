/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class LoaiDichVuPOJO 
{
    private int MaLoaiDV;
    private String TenLoaiDV;

    public LoaiDichVuPOJO() {
    }

    public LoaiDichVuPOJO(int MaLoaiDV, String TenLoaiDV) {
        this.MaLoaiDV = MaLoaiDV;
        this.TenLoaiDV = TenLoaiDV;
    }

    public int getMaLoaiDV() {
        return MaLoaiDV;
    }

    public void setMaLoaiDV(int MaLoaiDV) {
        this.MaLoaiDV = MaLoaiDV;
    }

    public String getTenLoaiDV() {
        return TenLoaiDV;
    }

    public void setTenLoaiDV(String TenLoaiDV) {
        this.TenLoaiDV = TenLoaiDV;
    }

    @Override
    public String toString() {
        return this.TenLoaiDV;
    }
    
    
    
}
