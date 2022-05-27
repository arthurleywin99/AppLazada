package com.example.applazada.View.DoiThongTinTaiKhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.example.applazada.View.DoiMatKhau.DoiMatKhauActivity;
import com.example.applazada.View.LoginScreen.DangNhapActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiThongTinTaiKhoanActivity extends AppCompatActivity implements View.OnClickListener, DatePicker.OnDateChangedListener {
    protected EditText edHoTen, edDiaChi, edSDT, edCMND;
    protected DatePicker dpNgaySinhDK;
    protected RadioButton rdNam, rdNu;
    protected Button btnDoiThongTin;
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_doithongtin);

        /**
         * Khởi tạo Control
         */
        InitControl();

        btnDoiThongTin.setOnClickListener(this);

        modelNhanVien.MoKetNoiSQL(DoiThongTinTaiKhoanActivity.this);
        NhanVien oldNhanVien = modelNhanVien.LayNhanVien();

        SetData(oldNhanVien);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /**
             * Submit đổi thông tin
             */
            case R.id.btnDoiThongTin: {
                if (edHoTen.getText().toString().trim().length() == 0
                        || edDiaChi.getText().toString().trim().length() == 0
                        || edSDT.getText().toString().trim().length() == 0
                        || edCMND.getText().toString().trim().length() == 0) {
                    Toast.makeText(DoiThongTinTaiKhoanActivity.this, "Bạn phải điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                NhanVien nhanVien = modelNhanVien.LayNhanVien();

                int manv = nhanVien.getMaNV();

                String HoTen = edHoTen.getText().toString().trim();
                String DiaChi = edDiaChi.getText().toString().trim();
                String SDT = edSDT.getText().toString().trim();
                String CMND = edCMND.getText().toString().trim();
                String NgaySinh = (dpNgaySinhDK.getDayOfMonth() + "/" + dpNgaySinhDK.getMonth() + "/" + dpNgaySinhDK.getYear()).trim();
                String GioiTinh;
                if (rdNam.isChecked()) {
                    GioiTinh = "Nam";
                }
                else {
                    GioiTinh = "Nữ";
                }

                nhanVien.setTenNV(HoTen);
                nhanVien.setDiaChi(DiaChi);
                nhanVien.setSDT(SDT);
                nhanVien.setCMND(CMND);
                nhanVien.setNgaySinh(NgaySinh);
                nhanVien.setGioiTinh(GioiTinh);

                ApiService.apiService.DoiThongTin(nhanVien).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse messageResponse = response.body();
                        if (messageResponse.getMessage().equals("Success")) {
                            Toast.makeText(DoiThongTinTaiKhoanActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                            modelNhanVien.XoaNhanVien(manv);
                            startActivity(new Intent(DoiThongTinTaiKhoanActivity.this, DangNhapActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(DoiThongTinTaiKhoanActivity.this, "Đổi thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(DoiThongTinTaiKhoanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                        Toast.makeText(DoiThongTinTaiKhoanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Kiểm tra hợp lệ với Datetimepicker
     * @param datePicker
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        Date currentDate = GetCurrentDate();

        String sDate = day + "/" + (month + 1) + "/" + year;
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            if (date.after(currentDate)) {
                Toast.makeText(DoiThongTinTaiKhoanActivity.this, "Ngày sinh cần phải nhỏ hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                btnDoiThongTin.setEnabled(false);
                return;
            }
            else {
                btnDoiThongTin.setEnabled(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi tạo Control
     */
    private void InitControl() {
        edHoTen = findViewById(R.id.edHoTen);
        edDiaChi = findViewById(R.id.edDiaChi);
        edSDT = findViewById(R.id.edSDT);
        edCMND = findViewById(R.id.edCMND);
        dpNgaySinhDK = findViewById(R.id.dpNgaySinhDK); // Chưa đổ dữ liệu
        rdNam = findViewById(R.id.rdNam);
        rdNu = findViewById(R.id.rdNu);
        btnDoiThongTin = findViewById(R.id.btnDoiThongTin);
    }

    /**
     * Set dữ liệu vào các control
     * @param oldNhanVien
     */
    private void SetData(NhanVien oldNhanVien) {
        edHoTen.setText(oldNhanVien.getTenNV());
        edDiaChi.setText(oldNhanVien.getDiaChi());
        edSDT.setText(oldNhanVien.getSDT());
        edCMND.setText(oldNhanVien.getCMND());
        if (oldNhanVien.getGioiTinh().equals("Nam")) {
            rdNam.setChecked(true);
        }
        else {
            rdNu.setChecked(true);
        }
    }

    /**
     * Lấy ngày hiện tại
     * @return
     */
    private Date GetCurrentDate() {
        LocalDate cDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currDate = dtf.format(cDate).toString();
        Date currentDate = null;

        try {
            currentDate = new SimpleDateFormat("dd/MM/yyyy").parse(currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }
}
