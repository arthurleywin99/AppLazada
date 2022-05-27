package com.example.applazada.Model;

import java.io.Serializable;
import java.util.List;

public class HoaDon implements Serializable {
    private int MaHD, MaNV, ChuyenKhoan;
    private String DiaChi, NgayMua, NgayGiao, TrangThai, TenNguoiNhan, SoDT, MaChuyenKhoan;

    public HoaDon() {
    }

    public HoaDon(int maHD, int maNV, int chuyenKhoan, String diaChi, String ngayMua, String ngayGiao, String trangThai, String tenNguoiNhan, String soDT, String maChuyenKhoan) {
        MaHD = maHD;
        MaNV = maNV;
        ChuyenKhoan = chuyenKhoan;
        DiaChi = diaChi;
        NgayMua = ngayMua;
        NgayGiao = ngayGiao;
        TrangThai = trangThai;
        TenNguoiNhan = tenNguoiNhan;
        SoDT = soDT;
        MaChuyenKhoan = maChuyenKhoan;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public int getChuyenKhoan() {
        return ChuyenKhoan;
    }

    public void setChuyenKhoan(int chuyenKhoan) {
        ChuyenKhoan = chuyenKhoan;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(String ngayMua) {
        NgayMua = ngayMua;
    }

    public String getNgayGiao() {
        return NgayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        NgayGiao = ngayGiao;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getTenNguoiNhan() {
        return TenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        TenNguoiNhan = tenNguoiNhan;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }

    public String getMaChuyenKhoan() {
        return MaChuyenKhoan;
    }

    public void setMaChuyenKhoan(String maChuyenKhoan) {
        MaChuyenKhoan = maChuyenKhoan;
    }
}
