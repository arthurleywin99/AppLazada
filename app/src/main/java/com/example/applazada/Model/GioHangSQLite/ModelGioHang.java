package com.example.applazada.Model.GioHangSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.applazada.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class ModelGioHang {
    SQLiteDatabase database;
    DBSanPham dbSanPham;

    public void MoKetNoiSQL(Context context) {
        dbSanPham = new DBSanPham(context);
    }

    public boolean ThemGioHang(SanPham sanPham) {
        database = dbSanPham.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBSanPham.TB_GIOHANG_MASP, sanPham.getMaSP());
        contentValues.put(DBSanPham.TB_GIOHANG_TENSP, sanPham.getTenSP());
        contentValues.put(DBSanPham.TB_GIOHANG_GIATIEN, sanPham.getGia());
        contentValues.put(DBSanPham.TB_GIOHANG_HINHANH, sanPham.getHinhGioHang());
        contentValues.put(DBSanPham.TB_GIOHANG_SOLUONG, sanPham.getSoLuong());
        contentValues.put(DBSanPham.TB_GIOHANG_SOLUONGTONKHO, sanPham.getSoLuongTonKho());

        long id = database.insert(DBSanPham.TB_GIOHANG, null, contentValues);
        return id > 0;
    }

    public boolean XoaSanPhamTrongGioHang(int masp) {
        database = dbSanPham.getWritableDatabase();

        int check = database.delete(dbSanPham.TB_GIOHANG, dbSanPham.TB_GIOHANG_MASP + " = " + masp, null);
        return check > 0;
    }

    public boolean CapNhatSanPhamTrongGioHang(int masp, int soLuong) {
        database = dbSanPham.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbSanPham.TB_GIOHANG_SOLUONG, soLuong);

        int check = database.update(dbSanPham.TB_GIOHANG, contentValues, dbSanPham.TB_GIOHANG_MASP + " = " + masp, null);
        return check > 0;
    }

    public int LaySoLuongSanPhamDaCoTrongGioHang(int masp) {
        int res = 0;
        database = dbSanPham.getReadableDatabase();

        String query = "SELECT * FROM " + DBSanPham.TB_GIOHANG + " WHERE " + DBSanPham.TB_GIOHANG_MASP + " = " + masp;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            return cursor.getInt(4);
        }

        cursor.close();

        return 0;
    }

    public List<SanPham> LayDanhSachSanPhamTrongGioHang() {
        List<SanPham> sanPhamList = new ArrayList<>();
        database = dbSanPham.getReadableDatabase();

        String query = "SELECT * FROM " + DBSanPham.TB_GIOHANG;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int MaSP = cursor.getInt(0);
            String TenSP = cursor.getString(1);
            int GiaTien = cursor.getInt(2);
            byte[] HinhAnh = cursor.getBlob(3);
            int soLuong = cursor.getInt(4);
            int soLuongTonKho = cursor.getInt(5);

            SanPham sanPham = new SanPham();
            sanPham.setMaSP(MaSP);
            sanPham.setTenSP(TenSP);
            sanPham.setGia(GiaTien);
            sanPham.setHinhGioHang(HinhAnh);
            sanPham.setSoLuong(soLuong);
            sanPham.setSoLuongTonKho(soLuongTonKho);

            sanPhamList.add(sanPham);
            cursor.moveToNext();
        }

        cursor.close();

        return sanPhamList;
    }
}
