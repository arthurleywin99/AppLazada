package com.example.applazada.Model.DangNhapSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbNhanVien extends SQLiteOpenHelper {
    public static String TB_NHANVIEN = "NHANVIEN";
    public static String TB_NHANVIEN_MANV = "MANV";
    public static String TB_NHANVIEN_TENNV = "TENNV";
    public static String TB_NHANVIEN_TENDN = "TENDN";
    public static String TB_NHANVIEN_MATKHAU = "MATKHAU";
    public static String TB_NHANVIEN_DIACHI = "DIACHI";
    public static String TB_NHANVIEN_NGAYSINH = "NGAYSINH";
    public static String TB_NHANVIEN_SDT = "SDT";
    public static String TB_NHANVIEN_GIOITINH = "GIOITINH";
    public static String TB_NHANVIEN_CMND = "CMND";
    public static String TB_NHANVIEN_MALOAINV = "MALOAINV";
    public static String TB_NHANVIEN_TRANGTHAI = "TRANGTHAI";

    public DbNhanVien(Context context) {
        super(context, "SQLNHANVIEN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbNhanVien = "CREATE TABLE " + TB_NHANVIEN + " (" + TB_NHANVIEN_MANV + " INTEGER PRIMARY KEY, "
                + TB_NHANVIEN_TENNV + " TEXT, " + TB_NHANVIEN_TENDN + " TEXT, " + TB_NHANVIEN_MATKHAU + " TEXT, "
                + TB_NHANVIEN_DIACHI + " TEXT, " + TB_NHANVIEN_NGAYSINH + " TEXT, " + TB_NHANVIEN_SDT + " TEXT, "
                + TB_NHANVIEN_GIOITINH + " TEXT, " + TB_NHANVIEN_CMND + " TEXT, " + TB_NHANVIEN_MALOAINV + " INTEGER, "
                + TB_NHANVIEN_TRANGTHAI + " INTEGER);";

        sqLiteDatabase.execSQL(tbNhanVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
