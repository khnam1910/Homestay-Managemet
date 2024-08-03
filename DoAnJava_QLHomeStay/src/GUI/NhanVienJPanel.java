/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import Controller.NhanVienController;
import DAO.NhanVienDAO;
import DAO.QuanHuyenDAO;
import POJO.NhanVienPOJO;
import POJO.QuanHuyenPOJO;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class NhanVienJPanel extends javax.swing.JPanel implements ActionListener {

    /**
     * Creates new form NhanVienJPanel
     */
    ArrayList<String> maNVList;
    NhanVienDAO nhanVienDAO;
    NhanVienController nhanVienController;
    QuanHuyenDAO quanhuyendao;

    public NhanVienJPanel() {
        initComponents();
        loadNhanVien();
        loadQuanHuyen();
        customJTF();
        setEnabled();
        jtbDSNV.getTableHeader().setReorderingAllowed(false);
        nhanVienController = new NhanVienController(this);
        nhanVienController.showNhanVienView();
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
        jtfHoTen.addFocusListener(highlighter);
        jtfSDT.addFocusListener(highlighter);
        jtfTuKhoa.addFocusListener(highlighter);
        jdcNgaySinh.addFocusListener(highlighter);
        jcbDiaChi.addFocusListener(highlighter);
    }

    public void loadNhanVien() {
        nhanVienDAO = new NhanVienDAO();
        maNVList = new ArrayList<>();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "SDT", "Quyền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ArrayList<NhanVienPOJO> DSNV = nhanVienDAO.dsNhanVien();

        for (NhanVienPOJO nv : DSNV) {
            String maNV = "NV" + nv.getMaNV();
            maNVList.add(maNV);
            model.addRow(new Object[]{nv.getTenNV(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getTenQH(), nv.getSDT(), nv.getTaiiKhoanPOJO().getQuyen()});
        }

        jtbDSNV.setModel(model);

    }

    public void loadQuanHuyen() {
        quanhuyendao = new QuanHuyenDAO();
        ArrayList<QuanHuyenPOJO> dsqh = quanhuyendao.dsQuanHuyen();

        jcbDiaChi.removeAllItems();

        for (QuanHuyenPOJO qh : dsqh) {
            jcbDiaChi.addItem(qh);
        }
    }

    public void fillText() {
        int row = jtbDSNV.getSelectedRow();
        if (row != -1) {
            jtfMaNV.setText(maNVList.get(row));
            jtfHoTen.setText((String) jtbDSNV.getValueAt(row, 0));
            String gioiTinh = (String) jtbDSNV.getValueAt(row, 1);
            if (gioiTinh.equals("Nam")) {
                jrdNam.setSelected(true);
            } else {
                jrdNu.setSelected(true);
            }
            String dateString = (String) jtbDSNV.getValueAt(row, 2);
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
                jdcNgaySinh.setDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String diachi = (String) jtbDSNV.getValueAt(row, 3);
            for (int i = 0; i < jcbDiaChi.getItemCount(); i++) {
                QuanHuyenPOJO item = (QuanHuyenPOJO) jcbDiaChi.getItemAt(i);
                if (item.getTenQH().equals(diachi)) {
                    jcbDiaChi.setSelectedItem(item);
                    break;
                }
            }
            jtfSDT.setText((String) jtbDSNV.getValueAt(row, 4));
        }
    }

    public void capNhatDanhSachNhanVien(ArrayList<NhanVienPOJO> danhsachNV) {
        DefaultTableModel model = (DefaultTableModel) jtbDSNV.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có
        for (NhanVienPOJO nv : danhsachNV) {
            model.addRow(new Object[]{nv.getTenNV(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getTenQH(), nv.getSDT(), nv.getTaiiKhoanPOJO().getQuyen()});
        }
    }

    public void setEnabled() {
        jtfMaNV.setEnabled(false);
        jtfHoTen.setEnabled(false);
        jtfSDT.setEnabled(false);
        jcbDiaChi.setEnabled(false);
        jdcNgaySinh.setEnabled(false);
    }

    public void setAbled(String action) {
        if (action.toString().equals("Them")) {
            jtfMaNV.setEnabled(false);
            jtfHoTen.setEnabled(true);
            jtfSDT.setEnabled(true);
            jcbDiaChi.setEnabled(true);
            jdcNgaySinh.setEnabled(true);
        }
        if (action.toString().equals("CapNhat")) {
            jtfMaNV.setEnabled(false);
            jtfHoTen.setEnabled(true);
            jtfSDT.setEnabled(true);
            jcbDiaChi.setEnabled(true);
            jdcNgaySinh.setEnabled(true);
        }
    }

    public void reset() {
        jtfHoTen.setText("");
        jtfMaNV.setText("");
        jtfSDT.setText("");
        jdcNgaySinh.setDate(null);
        jtfTuKhoa.setText("");
        loadNhanVien();
    }

    public boolean isEmpty() {
        if (jtfHoTen.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Họ tên bỏ trống", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jtfHoTen.requestFocus();
            return false;
        }
        if (jdcNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Ngày sinh bỏ trống", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jdcNgaySinh.requestFocus();
            return false;
        }
        String sdt = jtfSDT.getText();
        if (sdt.isEmpty() || sdt.length() != 10) {
            JOptionPane.showMessageDialog(null, "Số điện thoại bỏ trống hoặc không đúng 10 số", "Thông báo", JOptionPane.WARNING_MESSAGE);
            jtfSDT.requestFocus();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtfMaNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtfHoTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtfSDT = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jrdNam = new javax.swing.JRadioButton();
        jrdNu = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jcbDiaChi = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jdcNgaySinh = new com.toedter.calendar.JDateChooser();
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
        jtbDSNV = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), "THÔNG TIN NHÂN VIÊN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MÃ NHÂN VIÊN");

        jtfMaNV.setBackground(new java.awt.Color(255, 255, 255));
        jtfMaNV.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfMaNV.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("HỌ TÊN");

        jtfHoTen.setBackground(new java.awt.Color(255, 255, 255));
        jtfHoTen.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfHoTen.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SỐ ĐIỆN THOẠI");

        jtfSDT.setBackground(new java.awt.Color(255, 255, 255));
        jtfSDT.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtfSDT.setForeground(new java.awt.Color(0, 0, 0));

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GIỚI TÍNH", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jrdNam.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(jrdNam);
        jrdNam.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jrdNam.setForeground(new java.awt.Color(255, 255, 255));
        jrdNam.setText("Nam");

        jrdNu.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(jrdNu);
        jrdNu.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jrdNu.setForeground(new java.awt.Color(255, 255, 255));
        jrdNu.setText("Nữ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jrdNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jrdNu)
                .addGap(52, 52, 52))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jrdNam)
                    .addComponent(jrdNu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ĐỊA CHỈ");

        jcbDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        jcbDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcbDiaChi.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NGÀY SINH");

        jdcNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgaySinh.setForeground(new java.awt.Color(0, 0, 0));
        jdcNgaySinh.setDateFormatString("dd/MM/yyyy");
        jdcNgaySinh.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jdcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jcbDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jdcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 28, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
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

        jScrollPane1.setBackground(new java.awt.Color(51, 153, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), "DANH SÁCH NHÂN VIÊN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        jtbDSNV.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jtbDSNV.setModel(new javax.swing.table.DefaultTableModel(
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
        jtbDSNV.setGridColor(new java.awt.Color(0, 0, 0));
        jtbDSNV.setRowHeight(50);
        jtbDSNV.setRowMargin(10);
        jScrollPane1.setViewportView(jtbDSNV);

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("QUẢN LÝ NHÂN VIÊN");

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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAdd;
    private javax.swing.JButton jbtDel;
    private javax.swing.JButton jbtReset;
    private javax.swing.JButton jbtSave;
    private javax.swing.JButton jbtSearch;
    private javax.swing.JButton jbtUpd;
    private javax.swing.JComboBox<QuanHuyenPOJO> jcbDiaChi;
    private com.toedter.calendar.JDateChooser jdcNgaySinh;
    private javax.swing.JRadioButton jrdNam;
    private javax.swing.JRadioButton jrdNu;
    private javax.swing.JTable jtbDSNV;
    private javax.swing.JTextField jtfHoTen;
    private javax.swing.JTextField jtfMaNV;
    private javax.swing.JTextField jtfSDT;
    private javax.swing.JTextField jtfTuKhoa;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addJtbNVSelectionListener(ListSelectionListener listener) {
        jtbDSNV.getSelectionModel().addListSelectionListener(listener);
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

    public JComboBox<QuanHuyenPOJO> getJcbDiaChi() {
        return jcbDiaChi;
    }

    public void setJcbDiaChi(JComboBox<QuanHuyenPOJO> jcbDiaChi) {
        this.jcbDiaChi = jcbDiaChi;
    }

    public JDateChooser getJdcNgaySinh() {
        return jdcNgaySinh;
    }

    public void setJdcNgaySinh(JDateChooser jdcNgaySinh) {
        this.jdcNgaySinh = jdcNgaySinh;
    }

    public JRadioButton getJrdNam() {
        return jrdNam;
    }

    public void setJrdNam(JRadioButton jrdNam) {
        this.jrdNam = jrdNam;
    }

    public JRadioButton getJrdNu() {
        return jrdNu;
    }

    public void setJrdNu(JRadioButton jrdNu) {
        this.jrdNu = jrdNu;
    }

    public JTable getJtbDSNV() {
        return jtbDSNV;
    }

    public void setJtbDSNV(JTable jtbDSNV) {
        this.jtbDSNV = jtbDSNV;
    }

    public JTextField getJtfHoTen() {
        return jtfHoTen;
    }

    public void setJtfHoTen(JTextField jtfHoTen) {
        this.jtfHoTen = jtfHoTen;
    }

    public JTextField getJtfMaNV() {
        return jtfMaNV;
    }

    public void setJtfMaNV(JTextField jtfMaNV) {
        this.jtfMaNV = jtfMaNV;
    }

    public JTextField getJtfSDT() {
        return jtfSDT;
    }

    public void setJtfSDT(JTextField jtfSDT) {
        this.jtfSDT = jtfSDT;
    }

    public JTextField getJtfTuKhoa() {
        return jtfTuKhoa;
    }

    public void setJtfTuKhoa(JTextField jtfTuKhoa) {
        this.jtfTuKhoa = jtfTuKhoa;
    }

}
