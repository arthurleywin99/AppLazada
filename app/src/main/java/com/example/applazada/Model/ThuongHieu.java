package com.example.applazada.Model;

import java.io.Serializable;

public class ThuongHieu implements Serializable {
    private int MaTH, LuotMua;
    private String TenTH, HinhTH;

    public ThuongHieu() {
    }

    public ThuongHieu(int maTH, String tenTH, String hinhTH, int luotMua) {
        MaTH = maTH;
        TenTH = tenTH;
        HinhTH = hinhTH;
        LuotMua = luotMua;
    }

    public int getMaTH() {
        return MaTH;
    }

    public void setMaTH(int maTH) {
        MaTH = maTH;
    }

    public String getTenTH() {
        return TenTH;
    }

    public void setTenTH(String tenTH) {
        TenTH = tenTH;
    }

    public String getHinhTH() {
        return HinhTH;
    }

    public void setHinhTH(String hinhTH) {
        HinhTH = hinhTH;
    }

    public int getLuotMua() {
        return LuotMua;
    }

    public void setLuotMua(int luotMua) {
        LuotMua = luotMua;
    }
}
