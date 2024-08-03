/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author dong3
 */
public class QuanHuyenPOJO {
    private int MaQH;
    private String TenQH;

    public QuanHuyenPOJO() {
    }

    public QuanHuyenPOJO(int MaQH, String TenQH) {
        this.MaQH = MaQH;
        this.TenQH = TenQH;
    }

    public int getMaQH() {
        return MaQH;
    }

    public void setMaQH(int MaQH) {
        this.MaQH = MaQH;
    }

    public String getTenQH() {
        return TenQH;
    }

    public void setTenQH(String TenQH) {
        this.TenQH = TenQH;
    }

    @Override
    public String toString() {
        return this.getTenQH();
    }
    
    
    
}
