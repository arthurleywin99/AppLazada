package com.example.applazada.View.ThongTinTaiKhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.LoaiNhanVien;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.example.applazada.View.DoiMatKhau.DoiMatKhauActivity;
import com.example.applazada.View.DoiThongTinTaiKhoan.DoiThongTinTaiKhoanActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinTaiKhoanActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView txtTenDangNhap, txtHoTen, txtDiaChi, txtNTNS, txtSDT, txtGioiTinh, txtCMND, txtLoaiTaiKhoan;
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();
    protected Button btnDoiMatKhau, btnDoiThongTin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtintaikhoan);

        /**
         * Khởi tạo control
         */
        InitControl();

        btnDoiMatKhau.setOnClickListener(this);
        btnDoiThongTin.setOnClickListener(this);

        modelNhanVien.MoKetNoiSQL(this);

        NhanVien nhanVien = modelNhanVien.LayNhanVien();

        SetData(nhanVien);

        ApiService.apiService.LayLoaiNhanVienBangMaNV(nhanVien.getMaLoaiNV()).enqueue(new Callback<LoaiNhanVien>() {
            @Override
            public void onResponse(Call<LoaiNhanVien> call, Response<LoaiNhanVien> response) {
                LoaiNhanVien loaiNhanVien = response.body();
                if (loaiNhanVien != null) {
                    txtLoaiTaiKhoan.setText(loaiNhanVien.getTenLoaiNV());
                }
                else {
                    txtLoaiTaiKhoan.setText("Loading");
                }
            }

            @Override
            public void onFailure(Call<LoaiNhanVien> call, Throwable t) {
                Toast.makeText(ThongTinTaiKhoanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtNTNS = findViewById(R.id.txtNTNS);
        txtSDT = findViewById(R.id.txtSDT);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtCMND = findViewById(R.id.txtCMND);
        txtLoaiTaiKhoan = findViewById(R.id.txtLoaiTaiKhoan);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnDoiThongTin = findViewById(R.id.btnDoiThongTin);
    }

    /**
     * Set dữ liệu vào các control
     * @param nhanVien
     */
    private void SetData(NhanVien nhanVien) {
        txtTenDangNhap.setText(nhanVien.getTenDangNhap());
        txtHoTen.setText(nhanVien.getTenNV());
        txtDiaChi.setText(nhanVien.getDiaChi());
        txtNTNS.setText(nhanVien.getNgaySinh());
        txtSDT.setText(nhanVien.getSDT());
        txtGioiTinh.setText(nhanVien.getGioiTinh());
        txtCMND.setText(nhanVien.getCMND());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /**
             * Chuyển sang Activity Đổi mật khẩu
             */
            case R.id.btnDoiMatKhau: {
                startActivity(new Intent(ThongTinTaiKhoanActivity.this, DoiMatKhauActivity.class));
                break;
            }
            /**
             * Chuyển sang Activity Đổi thông tin
             */
            case R.id.btnDoiThongTin: {
                startActivity(new Intent(ThongTinTaiKhoanActivity.this, DoiThongTinTaiKhoanActivity.class));
                break;
            }
        }
    }
}
