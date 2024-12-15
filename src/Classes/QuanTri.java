/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.*;
import java.util.*;

/**
 *
 * @author haong
 */
public class QuanTri implements Serializable {
    private String tenDangNhap;
    private String matKhau;

    public QuanTri(String tenDangNhap, String matKhau) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public QuanTri() {
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuanTri tk = (QuanTri) obj;
        return tenDangNhap.equals(tk.tenDangNhap) && matKhau.equals(tk.matKhau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenDangNhap, matKhau);
    }
}
