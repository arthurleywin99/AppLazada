package com.example.applazada.View.GioHang;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.Adapter.AdapterGioHang;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;
import com.example.applazada.View.LoginScreen.DangNhapActivity;
import com.example.applazada.View.ThanhToan.ThanhToanActivity;

import java.util.List;

public class GioHangActivity extends AppCompatActivity implements View.OnClickListener {
    protected RecyclerView recyclerView;
    protected ModelGioHang modelGioHang = new ModelGioHang();
    protected Toolbar toolbar;
    protected Button btnMuaNgay;
    private final ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_giohang);

        /**
         * Khởi tạo control
         */
        InitControl();
        btnMuaNgay.setOnClickListener(this);

        modelGioHang.MoKetNoiSQL(this);

        /**
         * Khởi tạo Adapter
         */
        SetAdapter(modelGioHang.LayDanhSachSanPhamTrongGioHang());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /**
             * Submit mua ngay
             */
            case R.id.btnMuaNgay: {
                modelNhanVien.MoKetNoiSQL(GioHangActivity.this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();
                if (nhanVien == null) {
                    /**
                     * Chưa đăng nhập thì phải qua activity đăng nhập
                     */
                    startActivity(new Intent(GioHangActivity.this, DangNhapActivity.class));
                    finish();
                } else {
                    /**
                     * Đăng nhập rồi thì qua activity thanh toán
                     */
                    startActivity(new Intent(GioHangActivity.this, ThanhToanActivity.class));
                    finish();
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        recyclerView = findViewById(R.id.recyclerGioHang);
        toolbar = findViewById(R.id.toolBar);
        btnMuaNgay = findViewById(R.id.btnMuaNgay);

        toolbar.setTitle("GIỎ HÀNG");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * Khởi tạo Adapter giỏ hàng
     * @param sanphamList
     */
    @SuppressLint("NotifyDataSetChanged")
    private void SetAdapter(List<SanPham> sanphamList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        AdapterGioHang adapterGioHang = new AdapterGioHang(this, sanphamList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterGioHang);
        adapterGioHang.notifyDataSetChanged();
    }
}
