package com.example.applazada.Model;

import java.io.Serializable;

public class HoaDonResponse implements Serializable {
    private String MaHD;

    public HoaDonResponse() {
    }

    public HoaDonResponse(String maHD) {
        MaHD = maHD;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }
}
