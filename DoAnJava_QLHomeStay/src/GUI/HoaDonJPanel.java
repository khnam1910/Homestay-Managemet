package GUI;

import DAO.HoaDonDAO;
import POJO.HoaDonPOJO;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class HoaDonJPanel extends javax.swing.JPanel {

    private HoaDonDAO hoaDonDAO;
    private ArrayList<String> maHDList;

    public HoaDonJPanel() {
        initComponents();
        loadHoaDon();
        jtbHoaDon.getTableHeader().setReorderingAllowed(false);
    }

    public void loadHoaDon() {
        hoaDonDAO = new HoaDonDAO();
        maHDList = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Mã Hóa Đơn", "Ngày Lập HĐ", "Tổng Tiền", "Mã NV", "Mã KH", "Mã Phòng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<HoaDonPOJO> DSHD = hoaDonDAO.dsPhong();
        DecimalFormat df = new DecimalFormat("#####0.00");
        for (HoaDonPOJO hd : DSHD) {
            String maHD = hd.getMahd();
            String ngayLapHD = hd.getNgayLapHD();
//            double tongTien = hd.getTongTien();
            String tongTienDf = df.format(hd.getTongTien());
            String maNV = hd.getNhanVienPOJO().getMaNV();
            String maKH = hd.getKhachHangPOJO().getMaKH();
            String maPhong = hd.getPhongPOJO().getMaPH();

            maHDList.add(maHD);
            model.addRow(new Object[]{maHD, ngayLapHD, tongTienDf, maNV, maKH, maPhong});
        }
        jtbHoaDon.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbHoaDon = new javax.swing.JTable();

        setBackground(new java.awt.Color(153, 204, 255));
        setPreferredSize(new java.awt.Dimension(2159, 814));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(2159, 814));

        jtbHoaDon.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtbHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtbHoaDon.setRowHeight(50);
        jtbHoaDon.setRowMargin(10);
        jScrollPane1.setViewportView(jtbHoaDon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1367, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbHoaDon;
    // End of variables declaration//GEN-END:variables
}
