package com.example.applazada.Model;

import java.io.Serializable;
import java.util.Date;

public class NhanVien implements Serializable {
    private int MaNV;
    private String TenNV;
    private String TenDN;
    private String MatKhau;
    private String DiaChi;
    private String NgaySinh;
    private String SDT;
    private String GioiTinh;
    private String CMND;
    private int MaLoaiNV;

    public NhanVien() {
    }

    public NhanVien(int maNV, String tenNV, String tenDangNhap, String matKhau, String diaChi, String ngaySinh, String SDT, String gioiTinh, String CMND, int maLoaiNV) {
        MaNV = maNV;
        TenNV = tenNV;
        TenDN = tenDangNhap;
        MatKhau = matKhau;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
        GioiTinh = gioiTinh;
        this.CMND = CMND;
        MaLoaiNV = maLoaiNV;
    }

    public NhanVien(String tenNV, String tenDangNhap, String matKhau, String diaChi, String ngaySinh, String SDT, String gioiTinh, String CMND, int maLoaiNV) {
        TenNV = tenNV;
        TenDN = tenDangNhap;
        MatKhau = matKhau;
        DiaChi = diaChi;
        NgaySinh = ngaySinh;
        this.SDT = SDT;
        GioiTinh = gioiTinh;
        this.CMND = CMND;
        MaLoaiNV = maLoaiNV;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }

    public String getTenDangNhap() {
        return TenDN;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDN = tenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public int getMaLoaiNV() {
        return MaLoaiNV;
    }

    public void setMaLoaiNV(int maLoaiNV) {
        MaLoaiNV = maLoaiNV;
    }
}
