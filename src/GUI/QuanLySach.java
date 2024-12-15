/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.Sach;
import IOData.GenericFileHandler;
import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author haong
 */
public class QuanLySach extends javax.swing.JFrame {

    String fileName = "src/Data/Books.dat";
    GenericFileHandler<Sach> fileHandler = new GenericFileHandler<>(fileName);
    List<Sach> books = fileHandler.readFromFile();
    boolean State = true;

    /**
     * Creates new form QuanLySach
     */
    public QuanLySach() {
        initComponents();
        fakeData();
        sortData();
    }

    private void loadData() {
        DefaultTableModel tbModel = (DefaultTableModel) txtQLS.getModel();
        List<Sach> read = fileHandler.readFromFile();
        for (Sach ds : read) {
            Object data[] = {ds.getMaSach(), ds.getTenSach(), ds.getNhaXB(), ds.getTheLoai(), ds.getSoTrang()};
            tbModel.addRow(data);
        }
        txtQLS.setRowSelectionInterval(0, 0);
    }
    
    private void sortData() {
        List<Sach> read = fileHandler.readFromFile();
        Collections.sort(read, Comparator.comparing(Sach::getMaSach));
        for (Sach s : read) {
            fileHandler.deleteObject(s);
        }
        read.forEach(s -> {
            fileHandler.writeObjectToFile(s);
        });
        loadData();
    }

    private void fakeData() {
        books.add(new Sach("S1001", "Nhà giả kim", "Paulo Coelho", "Kỳ ảo", 110));
        books.add(new Sach("S1002", "Trạng Quỳnh", "Kim Đồng", "Sách tranh", 145));
        books.add(new Sach("S1003", "Thám tử Conan", "Kim Đồng", "Trinh thám", 210));
        books.add(new Sach("S1004", "Đắc nhân tâm", "NXB Quốc gia", "Tâm lý", 240));
        fileHandler.writeToFile(books);
    }

    private void clearData() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtNXB.setText("");
        txtTheLoai.setSelectedIndex(0);
        txtSoTrang.setText("");
    }

    private void addObject() {
        boolean check = true;
        books = fileHandler.readFromFile();
        for (Sach s : books) {
            if (txtMaSach.getText().equals(s.getMaSach())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Sách đã tồn tại!");
                check = false;
                State = false;
            }
        }
        if (txtMaSach.getText().equals("") || txtTenSach.getText().equals("") || txtNXB.getText().equals("") || txtTheLoai.getSelectedItem().equals("") || txtSoTrang.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            State = false;
        } else if (check == true) {
            Sach newS = new Sach(txtMaSach.getText(), txtTenSach.getText(), txtNXB.getText(), txtTheLoai.getSelectedItem().toString(), Integer.parseInt(txtSoTrang.getText()));
            fileHandler.writeObjectToFile(newS);
            DefaultTableModel tbModel = (DefaultTableModel) txtQLS.getModel();
            Object data[] = {txtMaSach.getText(), txtTenSach.getText(), txtNXB.getText(), txtTheLoai.getSelectedItem(), txtSoTrang.getText()};
            tbModel.insertRow(0, data);
            clearData();
            State = true;
        }
    }

    private void delObject() {
        books = fileHandler.readFromFile();
        String maSach = txtQLS.getValueAt(txtQLS.getSelectedRow(), 0).toString();
        String tenSach = txtQLS.getValueAt(txtQLS.getSelectedRow(), 1).toString();
        String nhaXB = txtQLS.getValueAt(txtQLS.getSelectedRow(), 2).toString();
        String theLoai = txtQLS.getValueAt(txtQLS.getSelectedRow(), 3).toString();
        String soTrang = txtQLS.getValueAt(txtQLS.getSelectedRow(), 4).toString();
        Sach delS = new Sach(maSach, tenSach, nhaXB, theLoai, Integer.parseInt(soTrang));
        fileHandler.deleteObject(delS);
        DefaultTableModel tbModel = (DefaultTableModel) txtQLS.getModel();
        tbModel.removeRow(txtQLS.getSelectedRow());
        clearData();
        State = true;
    }

    private void loadDataToField() {
        if (txtQLS.getSelectedRow() != -1) {
            txtMaSach.setText(txtQLS.getModel().getValueAt(txtQLS.getSelectedRow(), 0).toString());
            txtTenSach.setText(txtQLS.getValueAt(txtQLS.getSelectedRow(), 1).toString());
            txtNXB.setText(txtQLS.getValueAt(txtQLS.getSelectedRow(), 2).toString());
            for (Sach s : books) {
                if (txtQLS.getValueAt(txtQLS.getSelectedRow(), 3).toString().equals(s.getTheLoai())) {
                    txtTheLoai.setSelectedItem(s.getTheLoai());
                }
            }
            txtSoTrang.setText(txtQLS.getValueAt(txtQLS.getSelectedRow(), 4).toString());
        }
    }

    private void searchObject(Object input) {
        State = false;
        int count;
        for (count = 0; count < txtQLS.getRowCount(); count++) {
            if (txtQLS.getValueAt(count, 0).equals(input)) {
                State = true;
                break;
            }
        }
        if (State == true) {
            System.out.println("Tìm thấy sách có mã là: " + input.toString());
            txtQLS.getSelectionModel().setLeadSelectionIndex(count);
            loadDataToField();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Không tìm thấy sách có mã: " + input.toString());
        }
        State = false;
    }

    private void confirmEdit(String input) {
        int reply;
        if (!txtMaSach.getText().equals(input)) {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa sách có mã là: " + input + "\nĐổi thành mã là: " + txtMaSach.getText(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                addObject();
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa sách thành công");
                }
            }
        } else {
            reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận sửa sách có mã là: " + txtQLS.getValueAt(txtQLS.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
            if (reply == javax.swing.JOptionPane.YES_OPTION) {
                if (txtTenSach.getText().equals("") || txtNXB.getText().equals("") || txtTheLoai.getSelectedItem().equals("") || txtSoTrang.getText().equals("")) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                    State = false;
                } else {
                    Sach newtk = new Sach(txtMaSach.getText(), txtTenSach.getText(), txtNXB.getText(), txtTheLoai.getSelectedItem().toString(), Integer.parseInt(txtSoTrang.getText()));
                    fileHandler.writeObjectToFile(newtk);
                    DefaultTableModel tbModel = (DefaultTableModel) txtQLS.getModel();
                    Object data[] = {txtMaSach.getText(), txtTenSach.getText(), txtNXB.getText(), txtTheLoai.getSelectedItem(), txtSoTrang.getText()};
                    tbModel.insertRow(0, data);
                    clearData();
                    State = true;
                }
                if (State) {
                    delObject();
                    javax.swing.JOptionPane.showMessageDialog(null, "Sửa sách thành công");
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
        txtQLS = new javax.swing.JTable();
        lblMaSach = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        lblMHDN1 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        lblMHDN2 = new javax.swing.JLabel();
        lblMHDN3 = new javax.swing.JLabel();
        txtNXB = new javax.swing.JTextField();
        lblMHDN5 = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtTheLoai = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý sách");
        setResizable(false);

        pnHeader.setBackground(new java.awt.Color(204, 204, 204));

        lblHeader.setBackground(new java.awt.Color(255, 255, 255));
        lblHeader.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHeader.setText("QUẢN LÝ SÁCH");

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

        txtQLS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sách", "Tên sách", "Nhà xuất bản", "Thể loại", "Số trang"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        txtQLS.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtQLS.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        txtQLS.getTableHeader().setReorderingAllowed(false);
        txtQLS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtQLSMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtQLS);
        if (txtQLS.getColumnModel().getColumnCount() > 0) {
            txtQLS.getColumnModel().getColumn(0).setResizable(false);
            txtQLS.getColumnModel().getColumn(0).setPreferredWidth(1);
            txtQLS.getColumnModel().getColumn(1).setResizable(false);
            txtQLS.getColumnModel().getColumn(1).setPreferredWidth(200);
            txtQLS.getColumnModel().getColumn(2).setResizable(false);
            txtQLS.getColumnModel().getColumn(2).setPreferredWidth(100);
            txtQLS.getColumnModel().getColumn(3).setResizable(false);
            txtQLS.getColumnModel().getColumn(3).setPreferredWidth(100);
            txtQLS.getColumnModel().getColumn(4).setResizable(false);
            txtQLS.getColumnModel().getColumn(4).setPreferredWidth(11);
        }

        lblMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSach.setText("Mã sách");

        txtMaSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMHDN1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN1.setText("Tên sách");

        txtTenSach.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMHDN2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN2.setText("Thể loại");

        lblMHDN3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN3.setText("Nhà xuất bản");

        txtNXB.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblMHDN5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMHDN5.setText("Số trang");

        txtSoTrang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoTrang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoTrangKeyTyped(evt);
            }
        });

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

        txtTheLoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTheLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hành động", "Phiêu lưu", "Khoa học viễn tưởng", "Kỳ ảo", "Trinh thám", "Kinh dị", "Lãng mạn", "Tâm lý xã hội", "Lịch sử", "Hài hước", "Tiểu sử và hồi ký", "Lịch sử", "Khoa học", "Kinh tế", "Chính trị", "Tâm lý học", "Triết học", "Tôn giáo và tâm linh", "Sách kỹ năng", "Sách học thuật", "Công nghệ thông tin", "Y học", "Kinh doanh", "Luật", "Kỹ thuật", "Giáo dục", "Nghệ thuật và thiết kế", "Nấu ăn", "Du lịch", "Sách tranh", "Sách thiếu nhi", "Sách thanh thiếu niên" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMHDN1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblMHDN5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMHDN3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMHDN2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMHDN1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMHDN3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMHDN2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMHDN5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
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
        if (State)
            javax.swing.JOptionPane.showMessageDialog(this, "Thêm sách thành công");
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String maSach = txtQLS.getValueAt(txtQLS.getSelectedRow(), 0).toString();
        String tenSach = txtQLS.getValueAt(txtQLS.getSelectedRow(), 1).toString();
        String nhaXB = txtQLS.getValueAt(txtQLS.getSelectedRow(), 2).toString();
        String theLoai = txtQLS.getValueAt(txtQLS.getSelectedRow(), 3).toString();
        String soTrang = txtQLS.getValueAt(txtQLS.getSelectedRow(), 4).toString();
        if (!txtMaSach.getText().equals(maSach) || !txtTenSach.getText().equals(tenSach) || !txtNXB.getText().equals(nhaXB) || !txtTheLoai.getSelectedItem().equals(theLoai) || !txtSoTrang.getText().equals(soTrang)) {
            confirmEdit(maSach);
            System.out.println(maSach + txtMaSach.getText());
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Thông tin không được thay đổi!");
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int reply = javax.swing.JOptionPane.showConfirmDialog(null, "Xác nhận xóa sách có mã là: " + txtQLS.getValueAt(txtQLS.getSelectedRow(), 0).toString(), null, javax.swing.JOptionPane.YES_NO_OPTION);
        if (reply == javax.swing.JOptionPane.YES_OPTION) {
            delObject();
            javax.swing.JOptionPane.showMessageDialog(null, "Xóa sách thành công");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtMaSach.getText().equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null, "Vui long nhap thong tin");
        } else {
            searchObject(txtMaSach.getText());
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtQLSMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQLSMousePressed
        loadDataToField();
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSearch.setEnabled(true);
    }//GEN-LAST:event_txtQLSMousePressed

    private void txtSoTrangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoTrangKeyTyped
        char c = evt.getKeyChar();
        if (((c < '0') || (c > '9')) && (c != java.awt.event.KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSoTrangKeyTyped

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
            java.util.logging.Logger.getLogger(QuanLySach.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);

} catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySach().setVisible(true);
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
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblMHDN1;
    private javax.swing.JLabel lblMHDN2;
    private javax.swing.JLabel lblMHDN3;
    private javax.swing.JLabel lblMHDN5;
    private javax.swing.JLabel lblMaSach;
    private javax.swing.JPanel pnHeader;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTable txtQLS;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JComboBox<String> txtTheLoai;
    // End of variables declaration//GEN-END:variables
}
