/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Controller.DatPhongController;
import DAO.DatPhongDAO;
import DAO.KhachHangDAO;
import DAO.PhongDAO;
import POJO.DatPhongPOJO;
import POJO.KhachHangPOJO;
import POJO.PhongPOJO;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dong3
 */
public class DatPhongJPanel extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form DatPhongJPanel
     */
    KhachHangDAO khachHangDAO;
    PhongDAO phongDAO;
    DatPhongDAO datPhongDAO;
    ArrayList<String> maPhList1;
    ArrayList<String> maPhList2;
    ArrayList<String> maDpList;
    DatPhongController datPhongController;

    public DatPhongJPanel() {
        initComponents();
        setEnaled();
        jtbDSDP.getTableHeader().setReorderingAllowed(false);
        jtbDSPT.getTableHeader().setReorderingAllowed(false);
        loadCCCD();
        loadDSDP();
        loadDSPT();

        datPhongController = new DatPhongController(this);
    }

    public void loadDSDP() {
        datPhongDAO = new DatPhongDAO();
        maDpList = new ArrayList<>();
        maPhList1 = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"CCCD", "Tên Phòng", "Đơn Giá", "Ngày Đặt", "Ngày Trả", "Số Người"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<DatPhongPOJO> DSDP = datPhongDAO.dsDatPhong();

        for (DatPhongPOJO dp : DSDP) {
            String maDP = "DP" + dp.getMaDP();
            String maPh = "PH" + dp.getPhongPOJO().getMaPH();
            maDpList.add(maDP); // Thêm "Mã Phòng" vào danh sách
            maPhList1.add(maPh);
            model.addRow(new Object[]{dp.getKhachHangPOJO().getCCCD(), dp.getPhongPOJO().getTenPH(),
                dp.getPhongPOJO().getDongia(), dp.getNgayDat(), dp.getNgayTra(), dp.getSoNguoi()});
        }

        jtbDSDP.setModel(model);
    }

    public void loadDSPT() {
        phongDAO = new PhongDAO();
        maPhList2 = new ArrayList<>(); // Danh sách để lưu "Mã Phòng"
        // Tạo một đối tượng DefaultTableModel để chứa dữ liệu cho bảng
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Tên Phòng", "Loại Phòng", "Tiện Nghi", "Trạng Thái", "Đơn Giá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<PhongPOJO> DSPH = phongDAO.dsPhongTrong();

        for (PhongPOJO ph : DSPH) {
            String maPH = "PH" + ph.getMaPH();
            maPhList2.add(maPH); // Thêm "Mã Phòng" vào danh sách
            model.addRow(new Object[]{ph.getTenPH(), ph.getLoaiPhong(), ph.getTienNghi(), ph.getTrangThai(), ph.getDongia()});
        }

        jtbDSPT.setModel(model);
    }

    public void loadCCCD() {
        khachHangDAO = new KhachHangDAO();
        ArrayList<KhachHangPOJO> dsCCCD = khachHangDAO.dsCCCD();

        jcbCCCD.removeAllItems();

        for (KhachHangPOJO kh : dsCCCD) {
            jcbCCCD.addItem(kh);
        }
    }

    public void setEnaled() {
        jtfGiaPhong.setEnabled(false);
        jcbCCCD.setEnabled(false);
        jtfSoNguoi.setEnabled(false);
        jtfMaPhong.setEnabled(false);
        jdcNgayDat.setEnabled(false);
        jdcNgayTra.setEnabled(false);
        jbtDoiPhong.setEnabled(false);
        jbtDatPhong.setEnabled(false);
        jbtLuu.setEnabled(false);
    }

    public void filltextPH() {
        int row = jtbDSPT.getSelectedRow();
        if (row != -1) {
            jtfMaPhong.setText(maPhList2.get(row));
            jtfGiaPhong.setText(String.valueOf(jtbDSPT.getValueAt(row, 4)));
            jbtDatPhong.setEnabled(true);
            jbtDoiPhong.setEnabled(false);
        }
    }

    public void filltextDP() {
        int row = jtbDSDP.getSelectedRow();
        int rowDSPT = jtbDSPT.getSelectedRow();
        if (row != -1) {
            jtfMaPhong.setText(maPhList1.get(row));
            String cccd = (String) jtbDSDP.getValueAt(row, 0);
            for (int i = 0; i < jcbCCCD.getItemCount(); i++) {
                KhachHangPOJO item = (KhachHangPOJO) jcbCCCD.getItemAt(i);
                if (item.getCCCD().equals(cccd)) {
                    jcbCCCD.setSelectedItem(item);
                    break;
                }
            }
            jtfGiaPhong.setText(String.valueOf(jtbDSDP.getValueAt(row, 2)));
            String ngaydat = (String) jtbDSDP.getValueAt(row, 3);
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaydat);
                jdcNgayDat.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String ngaytra = (String) jtbDSDP.getValueAt(row, 4);
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ngaytra);
                jdcNgayTra.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jtfSoNguoi.setText(String.valueOf(jtbDSDP.getValueAt(row, 5)));
            jbtDatPhong.setEnabled(false);
            if (rowDSPT != -1) {
                jbtDoiPhong.setEnabled(true);
            }
        }
    }

    public void setAbleDSPT() {
        jcbCCCD.setEnabled(true);
        jtfSoNguoi.setEnabled(true);
        jdcNgayTra.setEnabled(true);
    }

    public void setAbleDSDP() {
        jtfSoNguoi.setEnabled(false);
        jdcNgayTra.setEnabled(false);
        jcbCCCD.setEnabled(false);
        jbtLuu.setEnabled(false);
    }

    public boolean isEmpty() {
        if (jtfSoNguoi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "VUI LÒNG NHẬP SỐ NGƯỜI", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            jtfSoNguoi.requestFocus();
            return false;
        }
        System.out.println(jdcNgayTra.getDate());
        if (jdcNgayTra.getDate() == null) {
            JOptionPane.showMessageDialog(null, "VUI LÒNG NHẬP NGÀY TRẢ PHÒNG", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            jdcNgayTra.requestFocus();
            return false;
        }
        return true;
    }

    public void reset() {
        jdcNgayDat.setDate(null);
        jdcNgayTra.setDate(null);
        jtfGiaPhong.setText("");
        jtfMaPhong.setText("");
        jtfSoNguoi.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDSDP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbCCCD = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jtfMaPhong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtfGiaPhong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jdcNgayDat = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jtfSoNguoi = new javax.swing.JTextField();
        jbtDatPhong = new javax.swing.JButton();
        jbtDoiPhong = new javax.swing.JButton();
        jbtLuu = new javax.swing.JButton();
        jdcNgayTra = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtbDSPT = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 204, 255));

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        jtbDSDP.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtbDSDP.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbDSDP.setGridColor(new java.awt.Color(0, 0, 0));
        jtbDSDP.setRowHeight(50);
        jtbDSDP.setRowMargin(10);
        jScrollPane1.setViewportView(jtbDSDP);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DANH SÁCH ĐẶT PHÒNG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1447, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(617, 617, 617)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("THÔNG TIN ĐẶT PHÒNG");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Mã CCCD");

        jcbCCCD.setBackground(new java.awt.Color(255, 255, 255));
        jcbCCCD.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jcbCCCD.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Mã Phòng");

        jtfMaPhong.setBackground(new java.awt.Color(255, 255, 255));
        jtfMaPhong.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfMaPhong.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Giá Phòng");

        jtfGiaPhong.setBackground(new java.awt.Color(255, 255, 255));
        jtfGiaPhong.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfGiaPhong.setForeground(new java.awt.Color(0, 0, 0));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ngày Đặt Phòng");

        jdcNgayDat.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayDat.setForeground(new java.awt.Color(0, 0, 0));
        jdcNgayDat.setDateFormatString("dd/MM/yyyy");
        jdcNgayDat.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Ngày Trả Phòng");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Số Người");

        jtfSoNguoi.setBackground(new java.awt.Color(255, 255, 255));
        jtfSoNguoi.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfSoNguoi.setForeground(new java.awt.Color(0, 0, 0));

        jbtDatPhong.setBackground(new java.awt.Color(102, 204, 255));
        jbtDatPhong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtDatPhong.setForeground(new java.awt.Color(255, 255, 255));
        jbtDatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/appointment.png"))); // NOI18N
        jbtDatPhong.setText("Đặt Phòng");

        jbtDoiPhong.setBackground(new java.awt.Color(255, 153, 153));
        jbtDoiPhong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtDoiPhong.setForeground(new java.awt.Color(0, 0, 0));
        jbtDoiPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/shift.png"))); // NOI18N
        jbtDoiPhong.setText("Đổi Phòng");

        jbtLuu.setBackground(new java.awt.Color(255, 255, 255));
        jbtLuu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtLuu.setForeground(new java.awt.Color(0, 0, 0));
        jbtLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        jbtLuu.setText("  Lưu");

        jdcNgayTra.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayTra.setForeground(new java.awt.Color(0, 0, 0));
        jdcNgayTra.setDateFormatString("dd/MM/yyyy");
        jdcNgayTra.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(239, 239, 239)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbtDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jdcNgayDat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfGiaPhong)
                            .addComponent(jtfSoNguoi)
                            .addComponent(jtfMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jbtLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jdcNgayTra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(496, Short.MAX_VALUE)
                .addComponent(jbtDoiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdcNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdcNgayTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfSoNguoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtDoiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtDatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));

        jtbDSPT.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtbDSPT.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbDSPT.setGridColor(new java.awt.Color(0, 0, 0));
        jtbDSPT.setRowHeight(50);
        jtbDSPT.setRowMargin(10);
        jScrollPane2.setViewportView(jtbDSPT);

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("DANH SÁCH PHÒNG TRỐNG");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel4)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtDatPhong;
    private javax.swing.JButton jbtDoiPhong;
    private javax.swing.JButton jbtLuu;
    private javax.swing.JComboBox<KhachHangPOJO> jcbCCCD;
    private com.toedter.calendar.JDateChooser jdcNgayDat;
    private com.toedter.calendar.JDateChooser jdcNgayTra;
    private javax.swing.JTable jtbDSDP;
    private javax.swing.JTable jtbDSPT;
    private javax.swing.JTextField jtfGiaPhong;
    private javax.swing.JTextField jtfMaPhong;
    private javax.swing.JTextField jtfSoNguoi;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addJtbDSPhongSelectionListener(ListSelectionListener listener) {
        jtbDSPT.getSelectionModel().addListSelectionListener(listener);
    }

    public void addJtbDSDatPhSelectionListener(ListSelectionListener listener) {
        jtbDSDP.getSelectionModel().addListSelectionListener(listener);
    }

    public void addJbtDatPhongSelectionListener(ActionListener actionListener) {
        jbtDatPhong.addActionListener(actionListener);
    }

    public void addJbtLuuSelectionListener(ActionListener actionListener) {
        jbtLuu.addActionListener(actionListener);
    }

    public void addJbtDoiPhongSelectionListener(ActionListener actionListener) {
        jbtDoiPhong.addActionListener(actionListener);
    }

    public JComboBox<KhachHangPOJO> getJcbCCCD() {
        return jcbCCCD;
    }

    public void setJcbCCCD(JComboBox<KhachHangPOJO> jcbCCCD) {
        this.jcbCCCD = jcbCCCD;
    }

    public JDateChooser getJdcNgayDat() {
        return jdcNgayDat;
    }

    public void setJdcNgayDat(JDateChooser jdcNgayDat) {
        this.jdcNgayDat = jdcNgayDat;
    }

    public JDateChooser getJdcNgayTra() {
        return jdcNgayTra;
    }

    public void setJdcNgayTra(JDateChooser jdcNgayTra) {
        this.jdcNgayTra = jdcNgayTra;
    }

    public JTable getJtbDSDP() {
        return jtbDSDP;
    }

    public void setJtbDSDP(JTable jtbDSDP) {
        this.jtbDSDP = jtbDSDP;
    }

    public JTable getJtbDSPT() {
        return jtbDSPT;
    }

    public void setJtbDSPT(JTable jtbDSPT) {
        this.jtbDSPT = jtbDSPT;
    }

    public JTextField getJtfGiaPhong() {
        return jtfGiaPhong;
    }

    public void setJtfGiaPhong(JTextField jtfGiaPhong) {
        this.jtfGiaPhong = jtfGiaPhong;
    }

    public JTextField getJtfSoNguoi() {
        return jtfSoNguoi;
    }

    public void setJtfSoNguoi(JTextField jtfSoNguoi) {
        this.jtfSoNguoi = jtfSoNguoi;
    }

    public JTextField getJtfMaPhong() {
        return jtfMaPhong;
    }

    public void setJtfMaPhong(JTextField jtfMaPhong) {
        this.jtfMaPhong = jtfMaPhong;
    }

    public ArrayList<String> getMaDpList() {
        return maDpList;
    }

    public void setMaDpList(ArrayList<String> maDpList) {
        this.maDpList = maDpList;
    }

    public ArrayList<String> getMaPhList1() {
        return maPhList1;
    }

    public void setMaPhList1(ArrayList<String> maPhList1) {
        this.maPhList1 = maPhList1;
    }

    public ArrayList<String> getMaPhList2() {
        return maPhList2;
    }

    public void setMaPhList2(ArrayList<String> maPhList2) {
        this.maPhList2 = maPhList2;
    }

    public JButton getJbtLuu() {
        return jbtLuu;
    }

    public void setJbtLuu(JButton jbtLuu) {
        this.jbtLuu = jbtLuu;
    }

}
