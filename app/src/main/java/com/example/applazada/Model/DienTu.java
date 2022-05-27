package com.example.applazada.Model;

import java.util.List;

public class DienTu {
    private List<ThuongHieu> thuongHieuList;
    private List<SanPham> sanPhamList;
    private String hinhSanPham;

    public DienTu() {
    }

    public DienTu(List<ThuongHieu> thuongHieuList, List<SanPham> sanPhamList, String hinhSanPham) {
        this.thuongHieuList = thuongHieuList;
        this.sanPhamList = sanPhamList;
        this.hinhSanPham = hinhSanPham;
    }

    public List<ThuongHieu> getThuongHieuList() {
        return thuongHieuList;
    }

    public void setThuongHieuList(List<ThuongHieu> thuongHieuList) {
        this.thuongHieuList = thuongHieuList;
    }

    public List<SanPham> getSanPhamList() {
        return sanPhamList;
    }

    public void setSanPhamList(List<SanPham> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }
}
