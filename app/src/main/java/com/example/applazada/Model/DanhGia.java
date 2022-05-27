package com.example.applazada.Model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    int MaSP;
    int MaNV;
    double SoSao;
    int MaHD;
    String NoiDung;

    public DanhGia() {
    }

    public DanhGia(int maSP, int maNV, double soSao, String noiDung, int maHD) {
        MaSP = maSP;
        MaNV = maNV;
        SoSao = soSao;
        NoiDung = noiDung;
        MaHD = maHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
        MaNV = maNV;
    }

    public double getSoSao() {
        return SoSao;
    }

    public void setSoSao(double soSao) {
        SoSao = soSao;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }
}
