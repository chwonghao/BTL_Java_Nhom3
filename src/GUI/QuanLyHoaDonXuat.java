/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.*;
import IOData.GenericFileHandler;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author haong
 */
public class QuanLyHoaDonXuat extends javax.swing.JFrame {

    String fileName = "src/Data/HoaDonXuat.dat";
    String sach = "src/Data/Books.dat";
    String nhanVien = "src/Data/Accounts.dat";
    GenericFileHandler<HoaDonXuat> fileHandler = new GenericFileHandler<>(fileName);
    List<HoaDonXuat> hdx = fileHandler.readFromFile();
    GenericFileHandler<Sach> readBooks = new GenericFileHandler<>(sach);
    List<Sach> ms = readBooks.readFromFile();
    GenericFileHandler<TaiKhoan> readAccounts = new GenericFileHandler<>(nhanVien);
    List<TaiKhoan> nVien = readAccounts.readFromFile();
    boolean State = true;

    /**
     * Creates new form QuanLyHoaDonXuat
     */
    public QuanLyHoaDonXuat() {
        initComponents();
        fakeData();
        loadDataToCCB();
        sortData();
    }

    private void loadData() {
        DefaultTableModel tbModel = (DefaultTableModel) txtHDX.getModel();
        List<HoaDonXuat> read = fileHandler.readFromFile();
        for (HoaDonXuat ds : read) {
            Object data[] = {ds.getMaHDX(), ds.getMaSach(), ds.getMaNV(), ds.getNgayXuat(), ds.getSoLuongX(), ds.getDonGiaX(), ds.getThanhTien()};
            tbModel.addRow(data);
        }
        txtHDX.setRowSelectionInterval(0, 0);
    }
    
    private void sortData() {
        List<HoaDonXuat> read = fileHandler.readFromFile();
        Collections.sort(read, Comparator.comparing(HoaDonXuat::getMaHDX));
        for (HoaDonXuat hd : read) {
            fileHandler.deleteObject(hd);
        }
        for (HoaDonXuat hd : read) {
            fileHandler.writeObjectToFile(hd);
        }
        loadData();
    }
    
    private void fakeData() {
        hdx.add(new HoaDonXuat("HDX1001", "S1001", "NV1001", "10-12-2024", 2, 20000, 40000));
        hdx.add(new HoaDonXuat("HDX1002", "S1002", "NV1002", "11-12-2024", 2, 20000, 40000));
        hdx.add(new HoaDonXuat("HDX1003", "S1003", "NV1003", "12-12-2024", 2, 20000, 40000));
        hdx.add(new HoaDonXuat("HDX1004", "S1004", "NV1004", "13-12-2024", 2, 20000, 40000));
        fileHandler.writeToFile(hdx);
    }

    private void clearData() {
        txtMHDX.setText("");
        txtMaSachX.setSelectedIndex(0);
        txtMaNVX.setSelectedIndex(0);
        txtNgayLapX.setText("");
        txtSoLuongX.setText("");
        txtDonGiaX.setText("");
        txtThanhTienX.setText("");
    }

    private void addObject() {
        boolean check = true;
        hdx = fileHandler.readFromFile();
        for (HoaDonXuat s : hdx) {
            if (txtMHDX.getText().equals(s.getMaHDX())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Hóa đơn đã tồn tại!");
                check = false;
                State = false;
            }
        }
        if (txtMHDX.getText().equals("")
                || txtMaSachX.getSelectedItem().equals("")
                || txtMaNVX.getSelectedItem().equals("")
                || txtNgayLapX.getText().equals("")
                || txtSoLuongX.getText().equals("")
                || txtDonGiaX.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            State = false;
        } else if (check == true) {
            int soLuong = Integer.parseInt(txtSoLuongX.getText());
            int donGia = Integer.parseInt(txtDonGiaX.getText());
            long thanhTien = soLuong * donGia;
            HoaDonXuat newHD = new HoaDonXuat(txtMHDX.getText(), txtMaSachX.getSelectedItem().toString(), txtMaNVX.getSelectedItem().toString(), txtNgayLapX.getText(), soLuong, donGia, thanhTien);
            fileHandler.writeObjectToFile(newHD);
            DefaultTableModel tbModel = (DefaultTableModel)txtHDX.getModel();
            Object data[] = {txtMHDX.getText(), txtMaSachX.getSelectedItem(), txtMaNVX.getSelectedItem(), txtNgayLapX.getText(),
                txtSoLuongX.getText(), txtDonGiaX.getText(), thanhTien};
            tbModel.insertRow(0, data);
            clearData();
            State = true;
        }
    }

    private void delObject() {
        hdx = fileHandler.readFromFile();
        String maHDX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 0).toString();
        String maSach = txtHDX.getValueAt(txtHDX.getSelectedRow(), 1).toString();
        String maNV = txtHDX.getValueAt(txtHDX.getSelectedRow(), 2).toString();
        String ngayXuat = txtHDX.getValueAt(txtHDX.getSelectedRow(), 3).toString();
        String soLuongX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 4).toString();
        String donGiaX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 5).toString();
        long thanhTienX = Integer.parseInt(soLuongX) * Integer.parseInt(donGiaX);

        try {
            HoaDonXuat delHD = new HoaDonXuat(maHDX, maSach, maNV, ngayXuat, Integer.parseInt(soLuongX), Integer.parseInt(donGiaX), thanhTienX);
            fileHandler.deleteObject(delHD);
            DefaultTableModel tbModel = (DefaultTableModel) txtHDX.getModel();
            tbModel.removeRow(txtHDX.getSelectedRow());
        } catch (NumberFormatException numberFormatException) {
        }
        clearData();
        State = true;
    }

    private void loadDataToField() {
        if (txtHDX.getSelectedRow() != -1) {
            txtMHDX.setText(txtHDX.getValueAt(txtHDX.getSelectedRow(), 0).toString());
            for (HoaDonXuat lms : hdx) {
                if (txtHDX.getValueAt(txtHDX.getSelectedRow(), 1).toString().equals(lms.getMaSach())) {
                    txtMaSachX.setSelectedItem(lms.getMaSach());
                }
            }
            for (HoaDonXuat lmnv : hdx) {
                if (txtHDX.getValueAt(txtHDX.getSelectedRow(), 2).toString().equals(lmnv.getMaNV())) {
                    txtMaNVX.setSelectedItem(lmnv.getMaNV());
                }
            }
            txtNgayLapX.setText(txtHDX.getValueAt(txtHDX.getSelectedRow(), 3).toString());
            txtSoLuongX.setText(txtHDX.getValueAt(txtHDX.getSelectedRow(), 4).toString());
            txtDonGiaX.setText(txtHDX.getValueAt(txtHDX.getSelectedRow(), 5).toString());
            txtThanhTienX.setText(txtHDX.getValueAt(txtHDX.getSelectedRow(), 6).toString());
        }
    }
    
    private void loadDataToCCB() {
        DefaultComboBoxModel s = new DefaultComboBoxModel();
        txtMaSachX.setModel(s);
        for (Sach lms : ms) {
            txtMaSachX.addItem(lms.getMaSach());
        }
        DefaultComboBoxModel nv = new DefaultComboBoxModel();
        txtMaNVX.setModel(nv);
        for (TaiKhoan lmnv : nVien) {
            txtMaNVX.addItem(lmnv.getMaNV());
        }
    }

    private void searchObject(Object input) {
        State = false;
        int count;
        for (count = 0; count < txtHDX.getRowCount(); count++) {
            if (txtHDX.getValueAt(count, 0).equals(input)) {
                State = true;
                break;
            }
        }
        if (State == true) {
            System.out.println("Tìm thấy hóa đơn có mã là: " + input.toString());
            txtHDX.getSelectionModel().setLeadSelectionIndex(count);
            loadDataToField();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn có mã: " + input.toString());
        }
        State = false;
    }

    private void confirmEdit(String input) {
        int reply;
        if (!txtMHDX.getText().equals(input)) {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa hóa đơn có mã là: " + input + "\nĐổi thành mã là: " + txtMHDX.getText(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                addObject();
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa hóa đơn thành công");
                }
            }
        } else {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa hóa đơn có mã là: " + txtHDX.getValueAt(txtHDX.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                if (txtMaSachX.getSelectedItem().equals("")
                        || txtMaNVX.getSelectedItem().equals("")
                        || txtNgayLapX.getText().equals("")
                        || txtSoLuongX.getText().equals("")
                        || txtDonGiaX.getText().equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    State = false;
                } else {
                    int soLuong = Integer.parseInt(txtSoLuongX.getText());
                    int donGia = Integer.parseInt(txtDonGiaX.getText());
                    long thanhTien = soLuong * donGia;
                    HoaDonXuat newHD = new HoaDonXuat(txtMHDX.getText(), txtMaSachX.getSelectedItem().toString(), txtMaNVX.getSelectedItem().toString(), txtNgayLapX.getText(), soLuong, donGia, thanhTien);
                    
                    fileHandler.writeObjectToFile(newHD);
                    DefaultTableModel tbModel = (DefaultTableModel) txtHDX.getModel();
                    Object data[] = {txtMHDX.getText(), txtMaSachX.getSelectedItem(), txtMaNVX.getSelectedItem(), txtNgayLapX.getText(), txtSoLuongX.getText(), txtDonGiaX.getText(), thanhTien};
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
        lblMaNV = new javax.swing.JLabel();
        lblDonGiaN = new javax.swing.JLabel();
        txtDonGiaX = new javax.swing.JTextField();
        txtSoLuongX = new javax.swing.JTextField();
        lblSoLuongN = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        txtThanhTienX = new javax.swing.JTextField();
        lblNgayNhap = new javax.swing.JLabel();
        lblMHDN = new javax.swing.JLabel();
        txtMHDX = new javax.swing.JTextField();
        txtNgayLapX = new javax.swing.JTextField();
        lblMaSach = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtHDX = new javax.swing.JTable();
        txtMaSachX = new javax.swing.JComboBox<>();
        txtMaNVX = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ HÓA ĐƠN XUẤT");
        setResizable(false);

        pnHeader.setBackground(new java.awt.Color(204, 204, 204));

        lblHeader.setBackground(new java.awt.Color(255, 255, 255));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("QUẢN LÝ HÓA ĐƠN XUẤT");

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
            .addGroup(pnHeaderLayout.createSequentialGroup()
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

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNV.setText("Mã nhân viên");

        lblDonGiaN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDonGiaN.setText("Đơn giá xuất");

        txtDonGiaX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDonGiaX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDonGiaXKeyTyped(evt);
            }
        });

        txtSoLuongX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoLuongXKeyTyped(evt);
            }
        });

        lblSoLuongN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongN.setText("Số lượng xuất");

        lblThanhTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThanhTien.setText("Thành tiền");

        txtThanhTienX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtThanhTienX.setEnabled(false);

        lblNgayNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayNhap.setText("Ngày lập");

        lblMHDN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN.setText("Mã hóa đơn xuất");

        txtMHDX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtNgayLapX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSach.setText("Mã sách");

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

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAdd.setText("Thêm hóa đơn");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtHDX.setAutoCreateRowSorter(true);
        txtHDX.setModel(new javax.swing.table.DefaultTableModel(
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
        txtHDX.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        txtHDX.setRowSorter(null);
        txtHDX.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtHDX.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtHDX.getTableHeader().setReorderingAllowed(false);
        txtHDX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtHDXMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtHDX);
        txtHDX.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (txtHDX.getColumnModel().getColumnCount() > 0) {
            txtHDX.getColumnModel().getColumn(0).setResizable(false);
            txtHDX.getColumnModel().getColumn(0).setPreferredWidth(1);
            txtHDX.getColumnModel().getColumn(1).setResizable(false);
            txtHDX.getColumnModel().getColumn(1).setPreferredWidth(1);
            txtHDX.getColumnModel().getColumn(2).setResizable(false);
            txtHDX.getColumnModel().getColumn(2).setPreferredWidth(1);
            txtHDX.getColumnModel().getColumn(3).setResizable(false);
            txtHDX.getColumnModel().getColumn(3).setPreferredWidth(100);
            txtHDX.getColumnModel().getColumn(4).setResizable(false);
            txtHDX.getColumnModel().getColumn(4).setPreferredWidth(1);
            txtHDX.getColumnModel().getColumn(5).setResizable(false);
            txtHDX.getColumnModel().getColumn(5).setPreferredWidth(100);
            txtHDX.getColumnModel().getColumn(6).setResizable(false);
            txtHDX.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        txtMaSachX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtMaNVX.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMHDN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMHDX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaSachX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaNVX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDonGiaX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSoLuongN, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoLuongX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtThanhTienX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNgayLapX, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoLuongN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuongX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDonGiaN, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDonGiaX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtThanhTienX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(78, 78, 78))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMHDN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMHDX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(txtMaSachX))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                            .addComponent(txtMaNVX))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblNgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayLapX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        String maHDX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 0).toString();
        String maSach = txtHDX.getValueAt(txtHDX.getSelectedRow(), 1).toString();
        String maNV = txtHDX.getValueAt(txtHDX.getSelectedRow(), 2).toString();
        String ngayXuat = txtHDX.getValueAt(txtHDX.getSelectedRow(), 3).toString();
        String soLuongX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 4).toString();
        String donGiaX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 5).toString();
        String thanhTienX = txtHDX.getValueAt(txtHDX.getSelectedRow(), 6).toString();
        if (!txtMHDX.getText().equals(maHDX)
                || !txtMaSachX.getSelectedItem().equals(maSach)
                || !txtMaNVX.getSelectedItem().equals(maNV)
                || !txtNgayLapX.getText().equals(ngayXuat)
                || !txtSoLuongX.getText().equals(soLuongX)
                || !txtDonGiaX.getText().equals(donGiaX)
                || !txtThanhTienX.getText().equals(thanhTienX)) {
            confirmEdit(maHDX);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Thông tin không được thay đổi!");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận xóa hóa đơn có mã là: " + txtHDX.getValueAt(txtHDX.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
        if (reply == javax.swing.JOptionPane.YES_OPTION) {
            delObject();
            javax.swing.JOptionPane.showMessageDialog(null, "Xóa hóa đơn thành công");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtMHDX.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hóa đơn để tìm kiếm");
        } else {
            searchObject(txtMHDX.getText());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtHDXMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHDXMousePressed
        loadDataToField();
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSearch.setEnabled(true);
    }//GEN-LAST:event_txtHDXMousePressed

    private void txtSoLuongXKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongXKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSoLuongXKeyTyped

    private void txtDonGiaXKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDonGiaXKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDonGiaXKeyTyped

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
            java.util.logging.Logger.getLogger(QuanLyHoaDonXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyHoaDonXuat().setVisible(true);
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
    private javax.swing.JTextField txtDonGiaX;
    private javax.swing.JTable txtHDX;
    private javax.swing.JTextField txtMHDX;
    private javax.swing.JComboBox<String> txtMaNVX;
    private javax.swing.JComboBox<String> txtMaSachX;
    private javax.swing.JTextField txtNgayLapX;
    private javax.swing.JTextField txtSoLuongX;
    private javax.swing.JTextField txtThanhTienX;
    // End of variables declaration//GEN-END:variables
}
