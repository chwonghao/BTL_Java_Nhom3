/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.io.*;

/**
 *
 * @author haong
 */
public class ThongKe implements Serializable{
    private String ngayTK,maTK,maSach;
    private int soLuongTon;

    public ThongKe(String maTK,String maSach,String ngayTK,int soLuongTon) {
        this.ngayTK = ngayTK;
        this.maTK=maTK;
        this.maSach=maSach;
        this.soLuongTon=soLuongTon;
    }
    public ThongKe(){
        
    }

    public String getNgayTK() {
        return ngayTK;
    }

    public void setNgayTK(String ngayTK) throws Exception{
        if(ngayTK.equals(""))
            throw new Exception("Ngày thống kê không được để trống!");
        this.ngayTK = ngayTK;
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) throws Exception{
        if(maTK.equals(""))
            throw new Exception("Mã số thống kê không được để trống!");
        this.maTK = maTK;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) throws Exception{
        if(maSach.equals(""))
            throw new Exception("Mã sách không được để trống!");
        this.maSach = maSach;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) throws Exception{
        if(soLuongTon <0)
            throw new Exception("Số lượng tồn không được là số âm!");
        this.soLuongTon = soLuongTon;
    }
}
