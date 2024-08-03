/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Controller.PhongController;
import DAO.LoaiPhongDAO;
import DAO.PhongDAO;
import POJO.LoaiPhongPOJO;
import POJO.PhongPOJO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dong3
 */
public class PhongJPanel extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form PhongJPanel
     */
    ArrayList<String> maPhList;
    PhongDAO phongDAO;
    LoaiPhongDAO loaiPhongDAO;
    PhongController phongController;

    public PhongJPanel() {
        initComponents();
        setEnabled();
        customJTF();
        loadPhong();
        loadLoaiPhong();
        loadTrangThai();
        jtbDSPhong.getTableHeader().setReorderingAllowed(false);
        phongController = new PhongController(this);
        phongController.showPhongView();
    }

    public void customJTF() {
        FocusListener highlighter;
        highlighter = new FocusListener() {
            public void focusGained(FocusEvent e) {
                e.getComponent().setBackground(new Color(153, 255, 153));
            }

            public void focusLost(FocusEvent e) {
                e.getComponent().setBackground(UIManager.getColor("TextField.background"));
            }
        };
        jtfTenPh.addFocusListener(highlighter);
        jtfTienNghi.addFocusListener(highlighter);
        jcbLoaiPhong.addFocusListener(highlighter);
        jcbTrangThai.addFocusListener(highlighter);
        jtfTuKhoa.addFocusListener(highlighter);
    }

    public void loadPhong() {
        phongDAO = new PhongDAO();
        maPhList = new ArrayList<>(); // Danh sách để lưu "Mã Phòng"
        // Tạo một đối tượng DefaultTableModel để chứa dữ liệu cho bảng
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Tên Phòng", "Loại Phòng", "Tiện Nghi", "Trạng Thái", "Đơn Giá"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<PhongPOJO> DSPH = phongDAO.dsPhong();

        for (PhongPOJO ph : DSPH) {
            String maPH = "PH" + ph.getMaPH();
            maPhList.add(maPH); // Thêm "Mã Phòng" vào danh sách
            model.addRow(new Object[]{ph.getTenPH(), ph.getLoaiPhong(), ph.getTienNghi(), ph.getTrangThai(), ph.getDongia()});
        }

        jtbDSPhong.setModel(model);
    }

    public void loadLoaiPhong() {
        loaiPhongDAO = new LoaiPhongDAO();
        ArrayList<LoaiPhongPOJO> dslph = loaiPhongDAO.dsLoaiPhong();

        jcbLoaiPhong.removeAllItems();

        for (LoaiPhongPOJO lph : dslph) {
            jcbLoaiPhong.addItem(lph);
        }
    }

    public void loadTrangThai() {
        jcbTrangThai.removeAllItems();
        jcbTrangThai.addItem("Đang trống");
        jcbTrangThai.addItem("Đã được đặt");

    }

    public void capNhatDanhSachPh(ArrayList<PhongPOJO> danhSachPH) {
        DefaultTableModel model = (DefaultTableModel) jtbDSPhong.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có
        for (PhongPOJO ph : danhSachPH) {
            model.addRow(new Object[]{ph.getTenPH(), ph.getLoaiPhong(), ph.getTienNghi(), ph.getTrangThai(), ph.getDongia()});
        }
    }

    public void fillText() {
        int row = jtbDSPhong.getSelectedRow();
        if (row != -1) {
            jtfMaPh.setText(maPhList.get(row));
            jtfTenPh.setText((String) jtbDSPhong.getValueAt(row, 0));
            String loaiphong = (String) jtbDSPhong.getValueAt(row, 1);
            for (int i = 0; i < jcbLoaiPhong.getItemCount(); i++) {
                LoaiPhongPOJO item = (LoaiPhongPOJO) jcbLoaiPhong.getItemAt(i);
                if (item.getTenLoaiPhong().equals(loaiphong)) {
                    jcbLoaiPhong.setSelectedItem(item);
                    break;
                }

            }
            jtfTienNghi.setText((String) jtbDSPhong.getValueAt(row, 2));
            jcbTrangThai.setSelectedItem((String) jtbDSPhong.getValueAt(row, 3));
            jtfDonGia.setText(String.valueOf(jtbDSPhong.getValueAt(row, 4)));
        }
    }

    public void setEnabled() {
        jtfDonGia.setEnabled(false);
        jtfMaPh.setEnabled(false);
        jtfTenPh.setEnabled(false);
        jtfTienNghi.setEnabled(false);
        jcbLoaiPhong.setEnabled(false);
        jcbTrangThai.setEnabled(false);
    }

    public void setAbled(String action) {
        if (action.equals("Them")) {
            jtfDonGia.setEnabled(false);
            jtfMaPh.setEnabled(false);
            jtfTenPh.setEnabled(true);
            jtfTienNghi.setEnabled(true);
            jcbLoaiPhong.setEnabled(true);
            jcbTrangThai.setEnabled(true);
        }
        if (action.equals("CapNhat")) {
            jtfDonGia.setEnabled(false);
            jtfMaPh.setEnabled(false);
            jtfTenPh.setEnabled(true);
            jtfTienNghi.setEnabled(true);
            jcbLoaiPhong.setEnabled(true);
            jcbTrangThai.setEnabled(true);
        }
    }

    public void reset() {
        jtfDonGia.setText("");
        jtfMaPh.setText("");
        jtfTenPh.setText("");
        jtfTienNghi.setText("");
        jtfTuKhoa.setText("");
        loadPhong();
    }

    public boolean isEmpty() {
        if (jtfTenPh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tên phòng bỏ trống", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jtfTenPh.requestFocus();
            return false;
        }
        if (jtfTienNghi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Thông tin tiện nghi bỏ trống", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jtfTienNghi.requestFocus();
            return false;
        }
        if (jtfDonGia.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa chọn loại phòng", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jcbLoaiPhong.requestFocus();
            return false;
        }
        return true;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtfMaPh = new javax.swing.JTextField();
        jtfTenPh = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfTienNghi = new javax.swing.JTextField();
        jcbTrangThai = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jtfDonGia = new javax.swing.JTextField();
        jcbLoaiPhong = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jbtAdd = new javax.swing.JButton();
        jbtDel = new javax.swing.JButton();
        jbtUpd = new javax.swing.JButton();
        jbtSave = new javax.swing.JButton();
        jbtReset = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtfTuKhoa = new javax.swing.JTextField();
        jbtSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDSPhong = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), "THÔNG TIN PHÒNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mã Phòng");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Tên Phòng");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Loại Phòng");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Trạng Thái");

        jtfMaPh.setBackground(new java.awt.Color(255, 255, 255));
        jtfMaPh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtfMaPh.setForeground(new java.awt.Color(0, 0, 0));

        jtfTenPh.setBackground(new java.awt.Color(255, 255, 255));
        jtfTenPh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtfTenPh.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tiện Nghi");

        jtfTienNghi.setBackground(new java.awt.Color(255, 255, 255));
        jtfTienNghi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtfTienNghi.setForeground(new java.awt.Color(0, 0, 0));

        jcbTrangThai.setBackground(new java.awt.Color(255, 255, 255));
        jcbTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcbTrangThai.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Đơn Giá");

        jtfDonGia.setBackground(new java.awt.Color(255, 255, 255));
        jtfDonGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtfDonGia.setForeground(new java.awt.Color(0, 0, 0));
        jtfDonGia.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jcbLoaiPhong.setBackground(new java.awt.Color(255, 255, 255));
        jcbLoaiPhong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcbLoaiPhong.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jtfMaPh, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfTenPh, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(151, 151, 151)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jtfTienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jcbTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jtfDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTienNghi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfMaPh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfTenPh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbLoaiPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(51, 153, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), "CHỨC NĂNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jbtAdd.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add.png"))); // NOI18N
        jbtAdd.setText("  Thêm");
        jbtAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jbtDel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete.png"))); // NOI18N
        jbtDel.setText("  Xóa");
        jbtDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jbtUpd.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtUpd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit.png"))); // NOI18N
        jbtUpd.setText("  Sửa");
        jbtUpd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jbtSave.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save.png"))); // NOI18N
        jbtSave.setText("Lưu");

        jbtReset.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/restart.png"))); // NOI18N
        jbtReset.setText("Làm mới");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Từ khóa:");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jtfTuKhoa.setBackground(new java.awt.Color(255, 255, 255));
        jtfTuKhoa.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfTuKhoa.setForeground(new java.awt.Color(0, 0, 0));

        jbtSearch.setBackground(new java.awt.Color(255, 255, 255));
        jbtSearch.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jbtSearch.setForeground(new java.awt.Color(0, 0, 0));
        jbtSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/search.png"))); // NOI18N
        jbtSearch.setText("Tìm kiếm");
        jbtSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtSearch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jbtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jbtDel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jbtUpd, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jbtReset)
                .addGap(44, 44, 44)
                .addComponent(jbtSave, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTuKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSearch)
                .addGap(69, 69, 69))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtDel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtUpd, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtSave, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtReset, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfTuKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jScrollPane1.setBackground(new java.awt.Color(102, 153, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), "DANH SÁCH PHÒNG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        jtbDSPhong.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtbDSPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbDSPhong.setGridColor(new java.awt.Color(0, 0, 0));
        jtbDSPhong.setRowHeight(50);
        jtbDSPhong.setRowMargin(10);
        jScrollPane1.setViewportView(jtbDSPhong);

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("QUẢN LÝ PHÒNG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(47, 47, 47))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAdd;
    private javax.swing.JButton jbtDel;
    private javax.swing.JButton jbtReset;
    private javax.swing.JButton jbtSave;
    private javax.swing.JButton jbtSearch;
    private javax.swing.JButton jbtUpd;
    private javax.swing.JComboBox<LoaiPhongPOJO> jcbLoaiPhong;
    private javax.swing.JComboBox<String> jcbTrangThai;
    private javax.swing.JTable jtbDSPhong;
    private javax.swing.JTextField jtfDonGia;
    private javax.swing.JTextField jtfMaPh;
    private javax.swing.JTextField jtfTenPh;
    private javax.swing.JTextField jtfTienNghi;
    private javax.swing.JTextField jtfTuKhoa;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addJtbDSPhongSelectionListener(ListSelectionListener listener) {
        jtbDSPhong.getSelectionModel().addListSelectionListener(listener);
    }

    public void addJbtThemActionListener(ActionListener actionListener) {
        jbtAdd.addActionListener(actionListener);
    }

    public void addJbtXoaActionListener(ActionListener actionListener) {
        jbtDel.addActionListener(actionListener);
    }

    public void addJbtSuaActionListener(ActionListener actionListener) {
        jbtUpd.addActionListener(actionListener);
    }

    public void addJbtLuuActionListener(ActionListener actionListener) {
        jbtSave.addActionListener(actionListener);
    }

    public void addJbtResetActionListener(ActionListener actionListener) {
        jbtReset.addActionListener(actionListener);
    }

    public void addJbtSearchActionListener(ActionListener actionListener) {
        jbtSearch.addActionListener(actionListener);
    }

    public void addJcbLoaiPhongActionListener(ActionListener actionListener) {
        jcbLoaiPhong.addActionListener(actionListener);
    }

    public JComboBox<LoaiPhongPOJO> getJcbLoaiPhong() {
        return jcbLoaiPhong;
    }

    public void setJcbLoaiPhong(JComboBox<LoaiPhongPOJO> jcbLoaiPhong) {
        this.jcbLoaiPhong = jcbLoaiPhong;
    }

    public JComboBox<String> getJcbTrangThai() {
        return jcbTrangThai;
    }

    public void setJcbTrangThai(JComboBox<String> jcbTrangThai) {
        this.jcbTrangThai = jcbTrangThai;
    }

    public JTable getJtbDSPhong() {
        return jtbDSPhong;
    }

    public void setJtbDSPhong(JTable jtbDSPhong) {
        this.jtbDSPhong = jtbDSPhong;
    }

    public JTextField getJtfDonGia() {
        return jtfDonGia;
    }

    public void setJtfDonGia(String jtfDonGia) {
        this.jtfDonGia.setText(jtfDonGia);
    }

    public JTextField getJtfMaPh() {
        return jtfMaPh;
    }

    public void setJtfMaPh(JTextField jtfMaPh) {
        this.jtfMaPh = jtfMaPh;
    }

    public JTextField getJtfTenPh() {
        return jtfTenPh;
    }

    public void setJtfTenPh(JTextField jtfTenPh) {
        this.jtfTenPh = jtfTenPh;
    }

    public JTextField getJtfTienNghi() {
        return jtfTienNghi;
    }

    public void setJtfTienNghi(JTextField jtfTienNghi) {
        this.jtfTienNghi = jtfTienNghi;
    }

    public JTextField getJtfTuKhoa() {
        return jtfTuKhoa;
    }

    public void setJtfTuKhoa(JTextField jtfTuKhoa) {
        this.jtfTuKhoa = jtfTuKhoa;
    }

}
