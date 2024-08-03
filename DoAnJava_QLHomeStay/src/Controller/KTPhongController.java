package Controller;

import DAO.DatPhongDAO;
import GUI.ChiTietPhongDialog;
import GUI.KTPhongJPanel;
import POJO.DatPhongPOJO;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class KTPhongController {

    private KTPhongJPanel kTPhongPanel;
    private DatPhongDAO datPhongDAO;
    int makh;
    int maph;
    public KTPhongController(KTPhongJPanel view) {
        this.kTPhongPanel = view;


        view.addJtbKTPhongMouseListener(new JtbKTPhongMouseListener());
    }

    private class JtbKTPhongMouseListener extends MouseAdapter {

        public JtbKTPhongMouseListener() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            datPhongDAO = new DatPhongDAO();
            if (e.getClickCount() == 2) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                if (row >= 0 && row < target.getRowCount()) {

                    String trangthai = (String) kTPhongPanel.getJtbKTPhong().getValueAt(row, 3);
                    if ("Đang trống".equals(trangthai)) {
                        JOptionPane.showMessageDialog(kTPhongPanel.getJtbKTPhong(), "Vui lòng đặt phòng!!");
                        return;

                    }
                    maph = Integer.parseInt(kTPhongPanel.getJtbKTPhong().getValueAt(row, 0).toString());
//                    System.out.println(maph);

                    ArrayList<DatPhongPOJO> dsmakh = datPhongDAO.ChiTietPhong(maph);
                    for (DatPhongPOJO dp : dsmakh) {
                        makh = Integer.parseInt(dp.getKhachHangPOJO().getMaKH());
                    }
                    
                    Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, kTPhongPanel);
                    ChiTietPhongDialog chiTietPhong = new ChiTietPhongDialog(frame, true, maph, makh, kTPhongPanel.getUsername());
                    chiTietPhong.setVisible(true);
                    
                }
            }
        }
    }
}
