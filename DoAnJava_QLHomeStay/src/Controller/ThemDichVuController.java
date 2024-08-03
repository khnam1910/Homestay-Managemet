/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.PhucVuDAO;
import GUI.ChiTietPhongDialog;
import GUI.ThemDichVuDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author dong3
 */
public class ThemDichVuController {
    private ThemDichVuDialog themDichVuDialog;
    private PhucVuDAO phucVuDAO;
    private ChiTietPhongDialog chiTietPhongDialog;
    
    int makh;
     public ThemDichVuController(ThemDichVuDialog view) {
        this.themDichVuDialog = view;

       view.addJtbDSDVSelectionListener(new JtbDSDVSelectionListener());
       view.addJbtOrderActionListener(new JbtOrederActionListener());

    }

    private class JbtOrederActionListener implements ActionListener {

        public JbtOrederActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            phucVuDAO = new PhucVuDAO();
            int row = themDichVuDialog.getJtbDSDV().getSelectedRow();
            {
                if(row != -1)
                {
                    int makh = themDichVuDialog.getMakh();
                    String maDVText = themDichVuDialog.getJtfMaDV().getText();
                    int madv = Integer.parseInt(maDVText.substring(2));
                    int soluong = Integer.parseInt(themDichVuDialog.getJtfSoLuong().getText());
                    phucVuDAO.themPhucVu(madv, makh, soluong);
                }
            }
          
            
        }
    }

    private class JtbDSDVSelectionListener implements ListSelectionListener {

        public JtbDSDVSelectionListener() {
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            themDichVuDialog.fillText();
        }
    }
}
