/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.*;
import IOData.GenericFileHandler;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author haong
 */
public class QuanLyHoaDonNhap extends javax.swing.JFrame {

    String fileName = "src/Data/HoaDonNhap.dat";
    String sach = "src/Data/Books.dat";
    String nhanVien = "src/Data/Accounts.dat";
    GenericFileHandler<HoaDonNhap> fileHandler = new GenericFileHandler<>(fileName);
    List<HoaDonNhap> hdn = fileHandler.readFromFile();
    GenericFileHandler<Sach> readBooks = new GenericFileHandler<>(sach);
    List<Sach> ms = readBooks.readFromFile();
    GenericFileHandler<TaiKhoan> readAccounts = new GenericFileHandler<>(nhanVien);
    List<TaiKhoan> nVien = readAccounts.readFromFile();
    boolean State = true;

    /**
     * Creates new form QuanLyHoaDonNhap
     */
    public QuanLyHoaDonNhap() {
        initComponents();
        fakeData();
        loadDataToCCB();
        sortData();
    }

    private void loadData() {
        DefaultTableModel tbModel = (DefaultTableModel) txtHDN.getModel();
        List<HoaDonNhap> read = fileHandler.readFromFile();
        for (HoaDonNhap ds : read) {
            Object data[] = {ds.getMaHDN(), ds.getMaSach(), ds.getMaNV(), ds.getNgayNhap(), ds.getSoLuongN(), ds.getDonGiaN(), ds.getThanhTien()};
            tbModel.addRow(data);
        }
        txtHDN.setRowSelectionInterval(0, 0);
    }

    private void sortData() {
        List<HoaDonNhap> read = fileHandler.readFromFile();
        Collections.sort(read, Comparator.comparing(HoaDonNhap::getMaHDN));
        for (HoaDonNhap hd : read) {
            fileHandler.deleteObject(hd);
        }
        for (HoaDonNhap hd : read) {
            fileHandler.writeObjectToFile(hd);
        }
        loadData();
    }

    private void fakeData() {
        hdn.add(new HoaDonNhap("HDN1001", "S1001", "NV1001", "9-12-2024", 2, 20000, 40000));
        hdn.add(new HoaDonNhap("HDN1001", "S1001", "NV1001", "10-12-2024", 2, 20000, 40000));
        hdn.add(new HoaDonNhap("HDN1001", "S1001", "NV1001", "11-12-2024", 2, 20000, 40000));
        hdn.add(new HoaDonNhap("HDN1001", "S1001", "NV1001", "12-12-2024", 2, 20000, 40000));
        fileHandler.writeToFile(hdn);
    }

    private void clearData() {
        txtMHDN.setText("");
        txtMaSachN.setSelectedIndex(0);
        txtMaNVN.setSelectedIndex(0);
        txtNgayLapX.setText("");
        txtSoLuongN.setText("");
        txtDonGiaN.setText("");
        txtThanhTienN.setText("");
    }

    private void addObject() {
        boolean check = true;
        hdn = fileHandler.readFromFile();
        for (HoaDonNhap s : hdn) {
            if (txtMHDN.getText().equals(s.getMaHDN())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Hóa đơn đã tồn tại!");
                check = false;
                State = false;
            }
        }
        if (txtMHDN.getText().equals("")
                || txtMaSachN.getSelectedItem().equals("")
                || txtMaNVN.getSelectedItem().equals("")
                || txtNgayLapX.getText().equals("")
                || txtSoLuongN.getText().equals("")
                || txtDonGiaN.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            State = false;
        } else if (check == true) {
            int soLuong = Integer.parseInt(txtSoLuongN.getText());
            int donGia = Integer.parseInt(txtDonGiaN.getText());
            long thanhTien = soLuong * donGia;
            HoaDonNhap newHD = new HoaDonNhap(txtMHDN.getText(), txtMaSachN.getSelectedItem().toString(), txtMaNVN.getSelectedItem().toString(), txtNgayLapX.getText(), soLuong, donGia, thanhTien);
            fileHandler.writeObjectToFile(newHD);
            DefaultTableModel tbModel = (DefaultTableModel) txtHDN.getModel();
            Object data[] = {txtMHDN.getText(), txtMaSachN.getSelectedItem(), txtMaNVN.getSelectedItem(), txtNgayLapX.getText(),
                txtSoLuongN.getText(), txtDonGiaN.getText(), thanhTien};
            tbModel.insertRow(0, data);
            clearData();
            State = true;
        }
    }

    private void delObject() {
        hdn = fileHandler.readFromFile();
        String maHDX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 0).toString();
        String maSach = txtHDN.getValueAt(txtHDN.getSelectedRow(), 1).toString();
        String maNV = txtHDN.getValueAt(txtHDN.getSelectedRow(), 2).toString();
        String ngayXuat = txtHDN.getValueAt(txtHDN.getSelectedRow(), 3).toString();
        String soLuongX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 4).toString();
        String donGiaX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 5).toString();
        long thanhTienX = Integer.parseInt(soLuongX) * Integer.parseInt(donGiaX);

        try {
            HoaDonNhap delHD = new HoaDonNhap(maHDX, maSach, maNV, ngayXuat, Integer.parseInt(soLuongX), Integer.parseInt(donGiaX), thanhTienX);
            fileHandler.deleteObject(delHD);
            DefaultTableModel tbModel = (DefaultTableModel) txtHDN.getModel();
            tbModel.removeRow(txtHDN.getSelectedRow());
        } catch (NumberFormatException numberFormatException) {
        }
        clearData();
        State = true;
    }

    private void loadDataToField() {
        if (txtHDN.getSelectedRow() != -1) {
            txtMHDN.setText(txtHDN.getValueAt(txtHDN.getSelectedRow(), 0).toString());
            for (HoaDonNhap lms : hdn) {
                if (txtHDN.getValueAt(txtHDN.getSelectedRow(), 1).toString().equals(lms.getMaSach())) {
                    txtMaSachN.setSelectedItem(lms.getMaSach());
                }
            }
            for (HoaDonNhap lmnv : hdn) {
                if (txtHDN.getValueAt(txtHDN.getSelectedRow(), 2).toString().equals(lmnv.getMaNV())) {
                    txtMaNVN.setSelectedItem(lmnv.getMaNV());
                }
            }
            txtNgayLapX.setText(txtHDN.getValueAt(txtHDN.getSelectedRow(), 3).toString());
            txtSoLuongN.setText(txtHDN.getValueAt(txtHDN.getSelectedRow(), 4).toString());
            txtDonGiaN.setText(txtHDN.getValueAt(txtHDN.getSelectedRow(), 5).toString());
            txtThanhTienN.setText(txtHDN.getValueAt(txtHDN.getSelectedRow(), 6).toString());
        }
    }
    
    private void loadDataToCCB() {
        DefaultComboBoxModel s = new DefaultComboBoxModel();
        txtMaSachN.setModel(s);
        for (Sach lms : ms) {
            txtMaSachN.addItem(lms.getMaSach());
        }
        DefaultComboBoxModel nv = new DefaultComboBoxModel();
        txtMaNVN.setModel(nv);
        for (TaiKhoan lmnv : nVien) {
            txtMaNVN.addItem(lmnv.getMaNV());
        }
    }

    private void searchObject(Object input) {
        State = false;
        int count;
        for (count = 0; count < txtHDN.getRowCount(); count++) {
            if (txtHDN.getValueAt(count, 0).equals(input)) {
                State = true;
                break;
            }
        }
        if (State == true) {
            System.out.println("Tìm thấy hóa đơn có mã là: " + input.toString());
            txtHDN.getSelectionModel().setLeadSelectionIndex(count);
            loadDataToField();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn có mã: " + input.toString());
        }
        State = false;
    }

    private void confirmEdit(String input) {
        int reply;
        if (!txtMHDN.getText().equals(input)) {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa hóa đơn có mã là: " + input + "\nĐổi thành mã là: " + txtMHDN.getText(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                addObject();
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa hóa đơn thành công");
                }
            }
        } else {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa hóa đơn có mã là: " + txtHDN.getValueAt(txtHDN.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                if (txtMaSachN.getSelectedItem().equals("")
                        || txtMaNVN.getSelectedItem().equals("")
                        || txtNgayLapX.getText().equals("")
                        || txtSoLuongN.getText().equals("")
                        || txtDonGiaN.getText().equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    State = false;
                } else {
                    int soLuong = Integer.parseInt(txtSoLuongN.getText());
                    int donGia = Integer.parseInt(txtDonGiaN.getText());
                    long thanhTien = soLuong * donGia;
                    HoaDonNhap newHD = new HoaDonNhap(txtMHDN.getText(), txtMaSachN.getSelectedItem().toString(), txtMaNVN.getSelectedItem().toString(), txtNgayLapX.getText(), soLuong, donGia, thanhTien);

                    fileHandler.writeObjectToFile(newHD);
                    DefaultTableModel tbModel = (DefaultTableModel) txtHDN.getModel();
                    Object data[] = {txtMHDN.getText(), txtMaSachN.getSelectedItem(), txtMaNVN.getSelectedItem(), txtNgayLapX.getText(), txtSoLuongN.getText(), txtDonGiaN.getText(), thanhTien};
                    tbModel.insertRow(0, data);
                    clearData();
                    State = true;
                }
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa hóa đơn thành công");
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnHeader = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHDN = new javax.swing.JTable();
        lblMHDN = new javax.swing.JLabel();
        txtMHDN = new javax.swing.JTextField();
        lblMaSach = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblDonGiaN = new javax.swing.JLabel();
        txtDonGiaN = new javax.swing.JTextField();
        txtSoLuongN = new javax.swing.JTextField();
        lblSoLuongN = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        txtThanhTienN = new javax.swing.JTextField();
        lblNgayNhap = new javax.swing.JLabel();
        txtNgayLapX = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtMaSachN = new javax.swing.JComboBox<>();
        txtMaNVN = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ HÓA ĐƠN NHẬP");
        setResizable(false);

        pnHeader.setBackground(new java.awt.Color(204, 204, 204));

        lblHeader.setBackground(new java.awt.Color(255, 255, 255));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setLabelFor(this);
        lblHeader.setText("QUẢN LÝ HÓA ĐƠN NHẬP");

        btnBack.setBackground(new java.awt.Color(0, 204, 204));
        btnBack.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBack.setText("Quay lại");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnHeaderLayout = new javax.swing.GroupLayout(pnHeader);
        pnHeader.setLayout(pnHeaderLayout);
        pnHeaderLayout.setHorizontalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnHeaderLayout.setVerticalGroup(
            pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHeaderLayout.createSequentialGroup()
                .addGroup(pnHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblHeader, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnHeaderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtHDN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã sách", "Mã nhân viên", "Ngày lập", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        txtHDN.setColumnSelectionAllowed(true);
        txtHDN.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtHDN.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtHDN.getTableHeader().setReorderingAllowed(false);
        txtHDN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtHDNMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtHDN);
        txtHDN.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (txtHDN.getColumnModel().getColumnCount() > 0) {
            txtHDN.getColumnModel().getColumn(0).setResizable(false);
            txtHDN.getColumnModel().getColumn(0).setPreferredWidth(1);
            txtHDN.getColumnModel().getColumn(1).setResizable(false);
            txtHDN.getColumnModel().getColumn(1).setPreferredWidth(1);
            txtHDN.getColumnModel().getColumn(2).setResizable(false);
            txtHDN.getColumnModel().getColumn(2).setPreferredWidth(1);
            txtHDN.getColumnModel().getColumn(3).setResizable(false);
            txtHDN.getColumnModel().getColumn(3).setPreferredWidth(100);
            txtHDN.getColumnModel().getColumn(4).setResizable(false);
            txtHDN.getColumnModel().getColumn(4).setPreferredWidth(1);
            txtHDN.getColumnModel().getColumn(5).setResizable(false);
            txtHDN.getColumnModel().getColumn(6).setResizable(false);
        }

        lblMHDN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN.setText("Mã hóa đơn nhập");

        txtMHDN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSach.setText("Mã sách");

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNV.setText("Mã nhân viên");

        lblDonGiaN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDonGiaN.setText("Đơn giá nhập");

        txtDonGiaN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDonGiaN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDonGiaNKeyTyped(evt);
            }
        });

        txtSoLuongN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoLuongNKeyTyped(evt);
            }
        });

        lblSoLuongN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongN.setText("Số lượng nhập");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThanhTien.setText("Thành tiền");

        txtThanhTienN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtThanhTienN.setEnabled(false);

        lblNgayNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayNhap.setText("Ngày lập");

        txtNgayLapX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAdd.setText("Thêm hóa đơn");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.setEnabled(false);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtMaSachN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtMaNVN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMHDN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMHDN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMaSachN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMaNVN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblSoLuongN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSoLuongN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtThanhTienN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayLapX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMHDN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMHDN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSachN)
                            .addComponent(lblMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNVN, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(txtNgayLapX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuongN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoLuongN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtThanhTienN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        Menu mn = new Menu();
        mn.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addObject();
        if (State) {
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String maHDX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 0).toString();
        String maSach = txtHDN.getValueAt(txtHDN.getSelectedRow(), 1).toString();
        String maNV = txtHDN.getValueAt(txtHDN.getSelectedRow(), 2).toString();
        String ngayXuat = txtHDN.getValueAt(txtHDN.getSelectedRow(), 3).toString();
        String soLuongX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 4).toString();
        String donGiaX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 5).toString();
        String thanhTienX = txtHDN.getValueAt(txtHDN.getSelectedRow(), 6).toString();
        if (!txtMHDN.getText().equals(maHDX)
                || !txtMaSachN.getSelectedItem().equals(maSach)
                || !txtMaNVN.getSelectedItem().equals(maNV)
                || !txtNgayLapX.getText().equals(ngayXuat)
                || !txtSoLuongN.getText().equals(soLuongX)
                || !txtDonGiaN.getText().equals(donGiaX)
                || !txtThanhTienN.getText().equals(thanhTienX)) {
            confirmEdit(maHDX);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Thông tin không được thay đổi!");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận xóa hóa đơn có mã là: " + txtHDN.getValueAt(txtHDN.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
        if (reply == javax.swing.JOptionPane.YES_OPTION) {
            delObject();
            javax.swing.JOptionPane.showMessageDialog(null, "Xóa hóa đơn thành công");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtMHDN.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hóa đơn để tìm kiếm");
        } else {
            searchObject(txtMHDN.getText());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtHDNMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHDNMousePressed
        loadDataToField();
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSearch.setEnabled(true);
    }//GEN-LAST:event_txtHDNMousePressed

    private void txtDonGiaNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaNKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDonGiaNKeyTyped

    private void txtSoLuongNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongNKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSoLuongNKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyHoaDonNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDonGiaN;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMHDN;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMaSach;
    private javax.swing.JLabel lblNgayNhap;
    private javax.swing.JLabel lblSoLuongN;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JTextField txtDonGiaN;
    private javax.swing.JTable txtHDN;
    private javax.swing.JTextField txtMHDN;
    private javax.swing.JComboBox<String> txtMaNVN;
    private javax.swing.JComboBox<String> txtMaSachN;
    private javax.swing.JTextField txtNgayLapX;
    private javax.swing.JTextField txtSoLuongN;
    private javax.swing.JTextField txtThanhTienN;
    // End of variables declaration//GEN-END:variables
}
