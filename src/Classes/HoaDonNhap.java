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
public class HoaDonNhap implements Serializable{
    private String maHDN, maSach, maNV, ngayNhap;
    private int soLuongN;
    private long donGiaN;
    private long thanhTien;

    public HoaDonNhap(String maHDN, String maSach, String maNV, String ngayNhap, int soLuongN, long donGiaN, long thanhTien) {
        this.maHDN = maHDN;
        this.maSach = maSach;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.soLuongN = soLuongN;
        this.donGiaN = donGiaN;
        this.thanhTien = thanhTien;
    }

    public HoaDonNhap() {
    }

    public long thanhTien() {
        return soLuongN * donGiaN;
    }

    public String getMaHDN() {
        return maHDN;
    }

    public void setMaHD(String maHDN) throws Exception {
        if (maHDN.equals("")) {
            throw new Exception("Mã hóa đơn không được để trống!");
        }
        this.maHDN = maHDN;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) throws Exception {
        if (maSach.equals("")) {
            throw new Exception("Mã sách không được để trống!");
        }
        this.maSach = maSach;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) throws Exception {
        if (maNV.equals("")) {
            throw new Exception("Mã nhân viên không được để trống!");
        }
        this.maNV = maNV;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayLap) {
        this.ngayNhap = ngayLap;
    }

    public int getSoLuongN() {
        return soLuongN;
    }

    public void setSoLuongN(int soLuongN) throws Exception {
        if (soLuongN < 0) {
            throw new Exception("Số lượng không được là số âm");
        }
        this.soLuongN = soLuongN;
    }

    public long getDonGiaN() {
        return donGiaN;
    }

    public void setDonGiaN(long donGiaN) {
        this.donGiaN = donGiaN;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HoaDonNhap hdn = (HoaDonNhap) obj;
        return maHDN.equals(hdn.maHDN) && maSach.equals(hdn.maSach) 
                && maNV.equals(hdn.maNV) && ngayNhap.equals(hdn.ngayNhap) 
                && soLuongN == hdn.soLuongN && donGiaN == hdn.donGiaN
                && thanhTien == hdn.thanhTien;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHDN, maSach, maNV, ngayNhap, soLuongN, donGiaN, thanhTien);
    }
}
