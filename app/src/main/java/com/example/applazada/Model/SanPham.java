package com.example.applazada.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int MaSP;
    private int SoLuong;
    private int MaLoaiSP;
    private int MaTH;
    private int LuotMua;
    private int SoLuongTonKho;
    private String TenSP, AnhLon, ThongTin;
    private long Gia;
    private byte[] HinhGioHang;
    private int TrangThai;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, long gia, String anhLon, String thongTin, int soLuongTonKho, int maLoaiSP, int maTH, int luotMua, int trangThai) {
        MaSP = maSP;
        MaLoaiSP = maLoaiSP;
        MaTH = maTH;
        TenSP = tenSP;
        AnhLon = anhLon;
        TrangThai = trangThai;
        ThongTin = thongTin;
        SoLuongTonKho = soLuongTonKho;
        Gia = gia;
        LuotMua = luotMua;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getMaLoaiSP() {
        return MaLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        MaLoaiSP = maLoaiSP;
    }

    public int getMaTH() {
        return MaTH;
    }

    public void setMaTH(int maTH) {
        MaTH = maTH;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getAnhLon() {
        return AnhLon;
    }

    public void setAnhLon(String anhLon) {
        AnhLon = anhLon;
    }

    public String getThongTin() {
        return ThongTin;
    }

    public void setThongTin(String thongTin) {
        ThongTin = thongTin;
    }

    public long getGia() {
        return Gia;
    }

    public void setGia(long gia) {
        Gia = gia;
    }

    public int getLuotMua() {
        return LuotMua;
    }

    public void setLuotMua(int luotMua) {
        LuotMua = luotMua;
    }

    public byte[] getHinhGioHang() {
        return HinhGioHang;
    }

    public void setHinhGioHang(byte[] hinhGioHang) {
        HinhGioHang = hinhGioHang;
    }

    public int getSoLuongTonKho() {
        return SoLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        SoLuongTonKho = soLuongTonKho;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }
}
