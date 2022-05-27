package com.example.applazada.View.DanhGiaSanPham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DanhGia;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.R;
import com.example.applazada.View.TrangChu.TrangChuActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhGiaSanPhamActivity extends AppCompatActivity {
    protected RatingBar rbDanhGia;
    protected EditText edNoiDungDanhGia;
    protected Button btnGuiDanhGia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danhgiasanpham);

        /**
         * Dữ liệu MaSP, MaNV, MaHD nhận từ chitietdonmua
         */
        Intent intent = getIntent();
        int MaSP = intent.getIntExtra("MaSP", 0);
        int MaNV = intent.getIntExtra("MaNV", 0);
        int MaHD = intent.getIntExtra("MaHD", 0);

        /**
         * Khởi tạo Control
         */
        InitControl();

        /**
         * Submit đánh giá
         */
        btnGuiDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double soSaoDanhGia = rbDanhGia.getRating();
                String noiDung = edNoiDungDanhGia.getText().toString().trim();

                DanhGia danhGia = new DanhGia();
                danhGia.setMaHD(MaHD);
                danhGia.setMaNV(MaNV);
                danhGia.setMaSP(MaSP);
                danhGia.setNoiDung(noiDung);
                danhGia.setSoSao(soSaoDanhGia);

                ApiService.apiService.TaoDanhGia(danhGia).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse messageResponse = response.body();
                        if (messageResponse.getMessage().equals("Success")) {
                            Toast.makeText(DanhGiaSanPhamActivity.this, "Gửi đánh giá thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DanhGiaSanPhamActivity.this, TrangChuActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(DanhGiaSanPhamActivity.this, "Gửi đánh giá thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(DanhGiaSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Khởi tạo Control
     */
    private void InitControl() {
        rbDanhGia = findViewById(R.id.rbDanhGia);
        edNoiDungDanhGia = findViewById(R.id.edNoiDungDanhGia);
        btnGuiDanhGia = findViewById(R.id.btnGuiDanhGia);
    }
}
