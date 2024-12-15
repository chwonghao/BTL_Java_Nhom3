/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.TaiKhoan;
import IOData.GenericFileHandler;
import java.util.*;

import javax.swing.table.*;

/**
 *
 * @author haong
 */
public final class QuanLyTaiKhoan extends javax.swing.JFrame {

    String fileName = "src/Data/Accounts.dat";
    GenericFileHandler<TaiKhoan> fileHandler = new GenericFileHandler<>(fileName);
    List<TaiKhoan> accounts = fileHandler.readFromFile();
    boolean State = true;

    /**
     * Creates new form QuanLyTaiKhoan
     */
    public QuanLyTaiKhoan() {
        initComponents();
        fakeData();
        sortData();
    }

    private void loadData() {
        DefaultTableModel tbModel = (DefaultTableModel) txtQLTK.getModel();
        List<TaiKhoan> read = fileHandler.readFromFile();
        for (TaiKhoan ds : read) {
            Object data[] = {ds.getMaNV(), ds.getTenNV(), ds.getDiaChi(), ds.getSdt(), ds.getEmail()};
            tbModel.addRow(data);
        }
        txtQLTK.setRowSelectionInterval(0, 0);
    }
    
    private void sortData() {
        List<TaiKhoan> read = fileHandler.readFromFile();
        Collections.sort(read, Comparator.comparing(TaiKhoan::getMaNV));
        for (TaiKhoan tk : read) {
            fileHandler.deleteObject(tk);
        }
        for (TaiKhoan tk : read) {
            fileHandler.writeObjectToFile(tk);
        }
        loadData();
    }

    private void fakeData() {
        accounts.add(new TaiKhoan("NV1001", "Nguyễn Chương Hào", "Nghệ An", "0987654321", "nguyenchuonghao123@gmail.com"));
        accounts.add(new TaiKhoan("NV1002", "Ngô Trung Hiếu", "Hà Nội", "0987654321", "ngotrunghieu123@gmail.com"));
        accounts.add(new TaiKhoan("NV1003", "Hồ Huy Khánh", "Hà Tĩnh", "0987654321", "hohuykhanh123@gmail.com"));
        accounts.add(new TaiKhoan("NV1004", "Nguyễn Văn A", "Bắc Ninh", "0987654321", "nguyenvana123@gmail.com"));
        accounts.add(new TaiKhoan("NV1005", "Trần Thị B", "Phú Thọ", "0987654321", "tranthib123@gmail.com"));
        accounts.add(new TaiKhoan("NV1006", "Đặng Thành C", "Hải Dương", "0987654321", "dangthanhc123@gmail.com"));
        fileHandler.writeToFile(accounts);
    }

    private void clearData() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
    }

    private void addObject() {
        boolean check = true;
        accounts = fileHandler.readFromFile();
        for (TaiKhoan tk : accounts) {
            if (txtMaNV.getText().equals(tk.getMaNV())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
                check = false;
                State = false;
            }
        }
        if (txtMaNV.getText().equals("") || txtHoTen.getText().equals("") || txtDiaChi.getText().equals("") || txtSDT.getText().equals("") || txtEmail.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            State = false;
        } else if (check == true) {
            TaiKhoan newtk = new TaiKhoan(txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText());
            fileHandler.writeObjectToFile(newtk);
            DefaultTableModel tbModel = (DefaultTableModel) txtQLTK.getModel();
            Object data[] = {txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText()};
            tbModel.insertRow(0, data);
            clearData();
            State = true;
        }
    }

    private void delObject() {
        accounts = fileHandler.readFromFile();
        String id = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 0).toString();
        String name = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 1).toString();
        String address = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 2).toString();
        String phone = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 3).toString();
        String email = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 4).toString();
        TaiKhoan delTK = new TaiKhoan(id, name, address, phone, email);
        fileHandler.deleteObject(delTK);
        DefaultTableModel tbModel = (DefaultTableModel) txtQLTK.getModel();
        tbModel.removeRow(txtQLTK.getSelectedRow());
        clearData();
        State = true;
    }

    private void loadDataToField() {
        if (txtQLTK.getSelectedRow() != -1) {
            txtMaNV.setText(txtQLTK.getModel().getValueAt(txtQLTK.getSelectedRow(), 0).toString());
            txtHoTen.setText(txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 1).toString());
            txtDiaChi.setText(txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 2).toString());
            txtSDT.setText(txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 3).toString());
            txtEmail.setText(txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 4).toString());
        }
    }

    private void searchObject(Object input) {
        State = false;
        int count;
        for (count = 0; count < txtQLTK.getRowCount(); count++) {
            if (txtQLTK.getValueAt(count, 0).equals(input)) {
                State = true;
                break;
            }
        }
        if (State == true) {
            System.out.println("Tìm thấy nhân viên có mã: " + input.toString());
            txtQLTK.getSelectionModel().setLeadSelectionIndex(count);
            loadDataToField();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên có mã: " + input.toString());
        }
        State = false;
    }

    private void confirmEdit(String maNV) {
        int reply;
        if (!txtMaNV.getText().equals(maNV)) {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa tài khoản có mã là: " + maNV + "\nĐổi thành mã là: " + txtMaNV.getText(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                boolean check = true;
                accounts = fileHandler.readFromFile();
                for (TaiKhoan tk : accounts) {
                    if (txtMaNV.getText().equals(tk.getMaNV())) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
                        check = false;
                        State = false;
                    }
                }
                if (txtMaNV.getText().equals("") || txtHoTen.getText().equals("") || txtDiaChi.getText().equals("") || txtSDT.getText().equals("") || txtEmail.getText().equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    State = false;
                } else if (check == true) {
                    TaiKhoan newtk = new TaiKhoan(txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText());
                    fileHandler.writeObjectToFile(newtk);
                    DefaultTableModel tbModel = (DefaultTableModel) txtQLTK.getModel();
                    Object data[] = {txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText()};
                    tbModel.insertRow(0, data);
                    clearData();
                    State = true;
                }
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công");
                }
            }
        } else {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa tài khoản có mã là: " + txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                if (txtHoTen.getText().equals("") || txtDiaChi.getText().equals("") || txtSDT.getText().equals("") || txtEmail.getText().equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    State = false;
                } else {
                    TaiKhoan newtk = new TaiKhoan(txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText());
                    fileHandler.writeObjectToFile(newtk);
                    DefaultTableModel tbModel = (DefaultTableModel) txtQLTK.getModel();
                    Object data[] = {txtMaNV.getText(), txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText()};
                    tbModel.insertRow(0, data);
                    clearData();
                    State = true;
                }
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa tài khoản thành công");
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

        jPanel1 = new javax.swing.JPanel();
        lblMHDN1 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        lblMHDN2 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        txtSDT = new javax.swing.JTextField();
        pnHeader = new javax.swing.JPanel();
        lblHeader = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblMHDN3 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtQLTK = new javax.swing.JTable();
        lblMHDN5 = new javax.swing.JLabel();
        lblMaSach = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QUẢN LÝ TÀI KHOẢN");
        setResizable(false);

        lblMHDN1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN1.setText("Họ và tên");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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

        lblMHDN2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN2.setText("Số điện thoại");

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setEnabled(false);
        btnSearch.setHideActionText(true);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSDTKeyTyped(evt);
            }
        });

        pnHeader.setBackground(new java.awt.Color(204, 204, 204));

        lblHeader.setBackground(new java.awt.Color(255, 255, 255));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("QUẢN LÝ TÀI KHOẢN");

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
                .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
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

        lblMHDN3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN3.setText("Địa chỉ");

        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtQLTK.setAutoCreateRowSorter(true);
        txtQLTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Họ tên", "Địa chỉ", "Số điện thoại", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        txtQLTK.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        txtQLTK.setRowSorter(null);
        txtQLTK.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtQLTK.getTableHeader().setReorderingAllowed(false);
        txtQLTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtQLTKMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtQLTK);
        if (txtQLTK.getColumnModel().getColumnCount() > 0) {
            txtQLTK.getColumnModel().getColumn(0).setResizable(false);
            txtQLTK.getColumnModel().getColumn(0).setPreferredWidth(1);
            txtQLTK.getColumnModel().getColumn(1).setResizable(false);
            txtQLTK.getColumnModel().getColumn(1).setPreferredWidth(100);
            txtQLTK.getColumnModel().getColumn(2).setResizable(false);
            txtQLTK.getColumnModel().getColumn(2).setPreferredWidth(150);
            txtQLTK.getColumnModel().getColumn(3).setResizable(false);
            txtQLTK.getColumnModel().getColumn(3).setPreferredWidth(1);
            txtQLTK.getColumnModel().getColumn(4).setResizable(false);
            txtQLTK.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        lblMHDN5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN5.setText("Email");

        lblMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSach.setText("Mã nhân viên");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAdd.setText("Thêm tài khoản");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 870, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblMHDN1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMHDN3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblMHDN2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMHDN5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMHDN1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMHDN3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMHDN2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMHDN5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if (State)
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận xóa tài khoản có mã là: " + txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 0).toString() + "?", null, javax.swing.JOptionPane.YES_NO_OPTION);
        if (reply == javax.swing.JOptionPane.YES_OPTION) {
            delObject();
            javax.swing.JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String maNV = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 0).toString();
        String tenNV = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 1).toString();
        String diaChi = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 2).toString();
        String sdt = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 3).toString();
        String email = txtQLTK.getValueAt(txtQLTK.getSelectedRow(), 4).toString();
        if (!txtMaNV.getText().equals(maNV) || !txtHoTen.getText().equals(tenNV) || !txtDiaChi.getText().equals(diaChi) || !txtSDT.getText().equals(sdt) || !txtEmail.getText().equals(email)) {
            confirmEdit(maNV);
            System.out.println(maNV + txtMaNV.getText());
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Thông tin không được thay đổi!");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtMaNV.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Vui lòng nhập mã nhân viên để tìm kiếm thông tin!");
        } else {
            searchObject(txtMaNV.getText());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtQLTKMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQLTKMousePressed
        loadDataToField();
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSearch.setEnabled(true);
    }//GEN-LAST:event_txtQLTKMousePressed

    private void txtSDTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSDTKeyTyped

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
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyTaiKhoan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMHDN1;
    private javax.swing.JLabel lblMHDN2;
    private javax.swing.JLabel lblMHDN3;
    private javax.swing.JLabel lblMHDN5;
    private javax.swing.JLabel lblMaSach;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTable txtQLTK;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
