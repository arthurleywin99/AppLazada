package com.example.applazada.View.DoiMatKhau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.example.applazada.View.LoginScreen.DangNhapActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiMatKhauActivity extends AppCompatActivity implements View.OnClickListener {
    protected EditText edMatKhauCu, edMatKhauMoi, edNhapLaiMatKhauMoi;
    protected Button btnDoiMatKhau;
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_doimatkhau);

        /**
         * Khởi tạo Control
         */
        InitControl();

        btnDoiMatKhau.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * Đổi mật khẩu
             */
            case R.id.btnDoiMatKhau: {
                if (edMatKhauCu.getText().toString().trim().length() == 0
                        || edMatKhauMoi.getText().toString().trim().length() == 0
                        || edNhapLaiMatKhauMoi.getText().toString().trim().length() == 0) {
                    Toast.makeText(DoiMatKhauActivity.this, "Bạn phải điền đầy đủ nội dung", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String matKhauCu = edMatKhauCu.getText().toString();
                    String matKhauMoi = edMatKhauMoi.getText().toString();
                    String nhapLaiMatKhauMoi = edNhapLaiMatKhauMoi.getText().toString();

                    if (!matKhauMoi.equals(nhapLaiMatKhauMoi)) {
                        Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        modelNhanVien.MoKetNoiSQL(DoiMatKhauActivity.this);
                        NhanVien nhanVien = modelNhanVien.LayNhanVien();

                        int manv = nhanVien.getMaNV();

                        if (!matKhauCu.equals(nhanVien.getMatKhau())) {
                            Toast.makeText(DoiMatKhauActivity.this, "Sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (matKhauCu.equals(matKhauMoi)) {
                            Toast.makeText(DoiMatKhauActivity.this, "Phải nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ApiService.apiService.DoiMatKhau(manv, matKhauMoi).enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                MessageResponse messageResponse = response.body();
                                if (messageResponse.getMessage().equals("Success")) {
                                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    modelNhanVien.XoaNhanVien(manv);
                                    startActivity(new Intent(DoiMatKhauActivity.this, DangNhapActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(DoiMatKhauActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Khởi tạo Control
     */
    private void InitControl() {
        edMatKhauCu = findViewById(R.id.edMatKhauCu);
        edMatKhauMoi = findViewById(R.id.edMatKhauMoi);
        edNhapLaiMatKhauMoi = findViewById(R.id.edNhapLaiMatKhauMoi);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
    }
}
