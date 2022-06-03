package com.example.applazada.Model.DangNhapSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.applazada.Model.NhanVien;

public class ModelNhanVien {
    SQLiteDatabase database;
    DbNhanVien dbNhanVien;

    public void MoKetNoiSQL(Context context) {
        dbNhanVien = new DbNhanVien(context);
    }

    public boolean ThemNhanVien(NhanVien nhanVien) {
        database = dbNhanVien.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbNhanVien.TB_NHANVIEN_MANV, nhanVien.getMaNV());
        contentValues.put(DbNhanVien.TB_NHANVIEN_TENNV, nhanVien.getTenNV());
        contentValues.put(DbNhanVien.TB_NHANVIEN_TENDN, nhanVien.getTenDangNhap());
        contentValues.put(DbNhanVien.TB_NHANVIEN_MATKHAU, nhanVien.getMatKhau());
        contentValues.put(DbNhanVien.TB_NHANVIEN_DIACHI, nhanVien.getDiaChi());
        contentValues.put(DbNhanVien.TB_NHANVIEN_NGAYSINH, nhanVien.getNgaySinh());
        contentValues.put(DbNhanVien.TB_NHANVIEN_SDT, nhanVien.getSDT());
        contentValues.put(DbNhanVien.TB_NHANVIEN_GIOITINH, nhanVien.getGioiTinh());
        contentValues.put(DbNhanVien.TB_NHANVIEN_CMND, nhanVien.getCMND());
        contentValues.put(DbNhanVien.TB_NHANVIEN_MALOAINV, nhanVien.getMaLoaiNV());
        contentValues.put(DbNhanVien.TB_NHANVIEN_TRANGTHAI, nhanVien.getTrangThai());

        long id = database.insert(DbNhanVien.TB_NHANVIEN, null, contentValues);
        return id > 0;
    }

    public boolean XoaNhanVien(int manv) {
        database = dbNhanVien.getWritableDatabase();

        int check = database.delete(dbNhanVien.TB_NHANVIEN, dbNhanVien.TB_NHANVIEN_MANV + " = " + manv, null);
        return check > 0;
    }

    public NhanVien LayNhanVien() {
        NhanVien nhanVien = new NhanVien();
        database = dbNhanVien.getReadableDatabase();

        String query = "SELECT * FROM " + DbNhanVien.TB_NHANVIEN;
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            int manv = cursor.getInt(0);
            String tennv = cursor.getString(1);
            String tendn = cursor.getString(2);
            String matkhau = cursor.getString(3);
            String diachi = cursor.getString(4);
            String ngaysinh = cursor.getString(5);
            String sdt = cursor.getString(6);
            String gioitinh = cursor.getString(7);
            String cmnd = cursor.getString(8);
            int maloainv = cursor.getInt(9);
            int trangthai = cursor.getInt(10);

            nhanVien.setMaNV(manv);
            nhanVien.setTenNV(tennv);
            nhanVien.setTenDangNhap(tendn);
            nhanVien.setMatKhau(matkhau);
            nhanVien.setDiaChi(diachi);
            nhanVien.setNgaySinh(ngaysinh);
            nhanVien.setSDT(sdt);
            nhanVien.setGioiTinh(gioitinh);
            nhanVien.setCMND(cmnd);
            nhanVien.setMaLoaiNV(maloainv);
            nhanVien.setTrangThai(trangthai);

            cursor.close();
            return nhanVien;
        }
        return null;
    }
}
