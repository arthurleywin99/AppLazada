package com.example.applazada.Model;

import java.io.Serializable;

public class LoaiNhanVien implements Serializable {
    private int MaLoaiNV;
    private String TenLoaiNV;

    public LoaiNhanVien() {
    }

    public LoaiNhanVien(int maLoaiNV, String tenLoaiNV) {
        MaLoaiNV = maLoaiNV;
        TenLoaiNV = tenLoaiNV;
    }

    public int getMaLoaiNV() {
        return MaLoaiNV;
    }

    public void setMaLoaiNV(int maLoaiNV) {
        MaLoaiNV = maLoaiNV;
    }

    public String getTenLoaiNV() {
        return TenLoaiNV;
    }

    public void setTenLoaiNV(String tenLoaiNV) {
        TenLoaiNV = tenLoaiNV;
    }
}
