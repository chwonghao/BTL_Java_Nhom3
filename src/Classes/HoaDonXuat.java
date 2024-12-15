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
public class HoaDonXuat implements Serializable{
    private String maHDX, maSach, maNV, ngayXuat;
    private int soLuongX;
    private int donGiaX;
    private long thanhTien;

    public HoaDonXuat(String maHDX, String maSach, String maNV, String ngayXuat, int soLuongX, int donGiaX, long thanhTien) {
        this.maHDX = maHDX;
        this.maSach = maSach;
        this.maNV = maNV;
        this.ngayXuat = ngayXuat;
        this.soLuongX = soLuongX;
        this.donGiaX = donGiaX;
        this.thanhTien = thanhTien;
    }
    
    public HoaDonXuat() {
    }

    public String getMaHDX() {
        return maHDX;
    }

    public void setMaHDX(String maHDX) {
        this.maHDX = maHDX;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getSoLuongX() {
        return soLuongX;
    }

    public void setSoLuongX(int soLuongX){
        this.soLuongX = soLuongX;
    }

    public int getDonGiaX() {
        return donGiaX;
    }

    public void setDonGiaX(int donGiaX) {
        this.donGiaX = donGiaX;
    }

    public long getThanhTien() {
        return getSoLuongX() * getDonGiaX();
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HoaDonXuat hdx = (HoaDonXuat) obj;
        return maHDX.equals(hdx.maHDX) && maSach.equals(hdx.maSach) 
                && maNV.equals(hdx.maNV) && ngayXuat.equals(hdx.ngayXuat) 
                && soLuongX == hdx.soLuongX && donGiaX == hdx.donGiaX
                && thanhTien == hdx.thanhTien;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHDX, maSach, maNV, ngayXuat, soLuongX, donGiaX, thanhTien);
    }
}
