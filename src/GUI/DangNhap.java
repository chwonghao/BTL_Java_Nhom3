/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Classes.QuanTri;
import IOData.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author haong
 */
public final class DangNhap extends javax.swing.JFrame {
    String fileName = "src/Data/AccountAD.dat";
    GenericFileHandler<QuanTri> fileHandler = new GenericFileHandler<>(fileName);
    List<QuanTri> accountList = new ArrayList<>();
    /**
     * Creates new form DangNhap
     */
    public DangNhap() {
        initComponents();
        writedata();
        readdata();
    }
    
    private boolean isValidLogin(String username, String password, List<QuanTri> ds) { 
        for (QuanTri tk : ds) { 
            if (tk.getTenDangNhap().equals(username) && tk.getMatKhau().equals(password)) { 
                return true; 
            } 
        } 
        return false; 
    }
    
    public void writedata() {
        accountList.add(new QuanTri("ChuongHao", "123"));
        accountList.add(new QuanTri("TrungHieu", "123"));
        accountList.add(new QuanTri("HuyKhanh", "123"));
        accountList.add(new QuanTri("a", "1"));
        fileHandler.writeToFile(accountList);
    }
    
    public void readdata() {
        List<QuanTri> read = fileHandler.readFromFile();
        for (QuanTri ds : read) {
            System.out.println("Name: " + ds.getTenDangNhap() + ", Brand: " + ds.getMatKhau());
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

        lblDN = new javax.swing.JLabel();
        lblTenDN = new javax.swing.JLabel();
        lblMK = new javax.swing.JLabel();
        txtTenDN = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        btnDangNhap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ĐĂNG NHẬP");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        lblDN.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        lblDN.setText("ĐĂNG NHẬP");
        lblDN.setName(""); // NOI18N

        lblTenDN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenDN.setText("Tên đang nhập");

        lblMK.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMK.setText("Mật khẩu");

        txtTenDN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDangNhap.setLabel("ĐĂNG NHẬP");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenDN)
                    .addComponent(lblMK))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenDN, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(txtMatKhau)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .addComponent(lblDN)
                .addGap(219, 219, 219))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblDN)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenDN)
                    .addComponent(txtTenDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMK)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        String username = txtTenDN.getText(); 
        String password = new String(txtMatKhau.getPassword());

        if (isValidLogin(username, password, accountList)) { 
            Menu mn = new Menu();
            mn.setVisible(true);
            this.dispose();
        } else { 
            //JOptionPane.showConfirmDialog(null, "Tài khoản hoặc mật khẩu không chính xác!", "Thông báo", WIDTH);
            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDangNhapActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangNhap().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JLabel lblDN;
    private javax.swing.JLabel lblMK;
    private javax.swing.JLabel lblTenDN;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDN;
    // End of variables declaration//GEN-END:variables
}
