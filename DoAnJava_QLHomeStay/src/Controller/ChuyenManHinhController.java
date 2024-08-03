package Controller;

import Bean.DanhMucBean;
import GUI.DatPhongJPanel;
import GUI.DichVuJPanel;
import GUI.HoaDonJPanel;
import GUI.KTPhongJPanel;
import GUI.KhachHangJPanel;
import GUI.NhanVienJPanel;
import GUI.PhongJPanel;
import GUI.ThongKeJPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class ChuyenManHinhController {

    private String Username;
    private JPanel root;
    private String kindSelected = "";

    private List<DanhMucBean> listItem = null;

    public ChuyenManHinhController(JPanel jpnRoot, String Username) {
        this.root = jpnRoot;
        this.Username = Username;
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "Kiểm tra phòng";
        jpnItem.setBackground(new Color(0, 153, 204));
        jpnItem.setBackground(new Color(0, 153, 204));

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new KTPhongJPanel(Username));
        root.validate();
        root.repaint();
    }

    public void setEvent(List<DanhMucBean> listItem) {
        this.listItem = listItem;
        for (DanhMucBean item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;

        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbITEM) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbITEM;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "KTPhong":
                    node = new KTPhongJPanel(Username);
                    break;
                case "Phong":
                    node = new PhongJPanel();
                    break;
                case "DatPhong":
                    node = new DatPhongJPanel();
                    break;
                case "DichVu":
                    node = new DichVuJPanel();
                    break;
                case "HoaDon":
                    node = new HoaDonJPanel();
                    break;
                case "NhanVien":
                    node = new NhanVienJPanel();
                    break;
                case "ThongKe":
                    node = new ThongKeJPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangJPanel();
                    break;
                default:
                    node = new KTPhongJPanel(Username);
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(0, 153, 204));
            jlbItem.setBackground(new Color(0, 153, 204));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(0, 153, 204));
            jlbItem.setBackground(new Color(0, 153, 204));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(51, 204, 255));
                jlbItem.setBackground(new Color(51, 204, 255));
            }
        }
    }

    private void setChangeBackground(String kind) {
        for (DanhMucBean item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(0, 153, 204));
                item.getJlb().setBackground(new Color(0, 153, 204));
            } else {
                item.getJpn().setBackground(new Color(51, 204, 255));
                item.getJlb().setBackground(new Color(51, 204, 255));
            }
        }
    }
}
