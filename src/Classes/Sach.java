/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.*;
import java.util.Objects;

/**
 *
 * @author haong
 */
public class Sach implements Serializable{
    private String maSach, tenSach, nhaXB, theLoai;
    private int soTrang;

    public Sach(String maSach, String tenSach, String nhaXB, String theLoai, int soTrang) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.nhaXB = nhaXB;
        this.theLoai = theLoai;
        this.soTrang = soTrang;
    }
    public Sach(){
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }
    
    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNhaXB() {
        return nhaXB;
    }

    public void setNhaXB(String nhaXB) {
        this.nhaXB = nhaXB;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sach tk = (Sach) obj;
        return maSach.equals(tk.maSach) 
                && tenSach.equals(tk.tenSach) 
                && nhaXB.equals(tk.nhaXB) 
                && theLoai.equals(tk.theLoai) 
                && soTrang == tk.soTrang;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSach, tenSach, nhaXB, theLoai, soTrang);
    }
}
