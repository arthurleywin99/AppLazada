package com.example.applazada.Model;

public class ChiTietHoaDon {
    private int MaHD, MaSP, SoLuong;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maHD, int maSP, int soLuong) {
        MaHD = maHD;
        MaSP = maSP;
        SoLuong = soLuong;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
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
}
