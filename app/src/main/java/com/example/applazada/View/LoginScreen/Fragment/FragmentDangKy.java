package com.example.applazada.View.LoginScreen.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applazada.API.ApiService;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDangKy extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_dangky, container, false);
        setupFloatingLabelError(view);

        TextView txtDangNhap = view.findViewById(R.id.btnDangNhapDK);
        /**
         * Đổi sang Tab đăng nhập khi click vào TextView Đăng nhập
         */
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeTab();
            }
        });

        /**
         * Nút đăng ký
         */
        Button btnDangKy = view.findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText input_HoTen = getActivity().findViewById(R.id.edHoTenDK);
                TextInputEditText input_Email = getActivity().findViewById(R.id.edEmailDK);
                TextInputEditText input_Password = getActivity().findViewById(R.id.edMatKhauDK);
                TextInputEditText input_DiaChi = getActivity().findViewById(R.id.edDiaChiDK);
                DatePicker input_NgaySinh = getActivity().findViewById(R.id.dpNgaySinhDK);
                TextInputEditText input_SDT = getActivity().findViewById(R.id.edSDTDK);
                RadioButton rdNam = getActivity().findViewById(R.id.rdNamDK);
                TextInputEditText input_CMND = getActivity().findViewById(R.id.edCMNDDK);

                String HoTen = input_HoTen.getText().toString().trim();
                String Email = input_Email.getText().toString().trim();
                String Password = LocalVariablesAndMethods.MD5Hash(input_Password.getText().toString().trim());
                String DiaChi = input_DiaChi.getText().toString().trim();
                String NgaySinh = (input_NgaySinh.getDayOfMonth() + "/" + input_NgaySinh.getMonth() + "/" + input_NgaySinh.getYear()).trim();
                String SDT = input_SDT.getText().toString().trim();
                String GT = "Nữ";
                if (rdNam.isChecked()) {
                    GT = "Nam";
                }
                String CMND = input_CMND.getText().toString().trim();

                NhanVien nhanVien = new NhanVien();
                nhanVien.setTenNV(HoTen);
                nhanVien.setTenDangNhap(Email);
                nhanVien.setMatKhau(Password);
                if (DiaChi.isEmpty()) {
                    nhanVien.setDiaChi(null);
                } else {
                    nhanVien.setDiaChi(DiaChi);
                }
                nhanVien.setNgaySinh(NgaySinh);
                nhanVien.setSDT(SDT);
                nhanVien.setGioiTinh(GT);
                nhanVien.setCMND(CMND);
                nhanVien.setMaLoaiNV(2);
                nhanVien.setTrangThai(1);

                /**
                 * Tạo một record mới vào bảng NhanVien trong DB
                 */
                ApiService.apiService.DangKy(nhanVien).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse messageRes = response.body();
                        if (messageRes.getMessage().equals("Success")) {
                            Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            TabLayout tabLayout = getActivity().findViewById(R.id.tabDangNhap);
                            tabLayout.getTabAt(0).select();
                        }
                        else {
                            Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    /**
     * Đổi sang Tab Đăng Nhập
     */
    public void ChangeTab() {
        TabLayout tabLayout = getActivity().findViewById(R.id.tabDangNhap);
        tabLayout.getTabAt(0).select();
    }

    /**
     * Tuỳ chỉnh màu và action với Button
     * @param b
     * @param c
     */
    public void setupButton(Button b, Boolean c) {
        if (c) {
            b.setEnabled(true);
            b.setBackgroundResource(R.color.orange_red);
        }
        else {
            b.setEnabled(false);
            b.setBackgroundResource(R.color.light_grey);
        }
    }

    /**
     * Bắt lỗi các Control
     * @param view
     */
    private void setupFloatingLabelError(View view) {
        Button btnDangKy = view.findViewById(R.id.btnDangKy);

        /**
         * Bắt lỗi Họ và tên
         */

        final TextInputLayout input_txtHoTen = view.findViewById(R.id.input_edHoTenDK);
        input_txtHoTen.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String HoTen = new String(charSequence.toString());
                if (HoTen.length() == 0) {
                    input_txtHoTen.setError("Họ và tên không được để trống");
                    input_txtHoTen.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtHoTen.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }

                if (!HoTen.matches("[A-Za-zàáảãạèéẻẽẹỳýỷỹỵùúủũụìíỉĩịòóỏõọêôơăâđ ]+$")) {
                    input_txtHoTen.setError("Họ và tên không chứa ký tự đặc biệt");
                    input_txtHoTen.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtHoTen.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * Bắt lỗi Địa chỉ Email
         */

        final TextInputLayout input_txtEmail = view.findViewById(R.id.input_edEmailDK);
        input_txtEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Email = new String(charSequence.toString());
                if (Email.length() == 0) {
                    input_txtEmail.setError("Email không được để trống");
                    input_txtEmail.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtEmail.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }

                if (!Email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
                    input_txtEmail.setError("Không đúng định dạng Email");
                    input_txtEmail.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtEmail.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ApiService.apiService.KiemTraDuLieu("TenDN", editable.toString()).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse res = response.body();
                        if (res.getMessage().equals("Exist")) {
                            input_txtEmail.setError("Email đã được đăng ký trong hệ thống");
                            input_txtEmail.setErrorEnabled(true);
                            setupButton(btnDangKy, false);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        /**
         * Bắt lỗi Mật khẩu
         */

        final TextInputLayout input_txtMatKhau = view.findViewById(R.id.input_edMatKhauDK);
        input_txtMatKhau.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Password = new String(charSequence.toString());
                if (Password.length() == 0) {
                    input_txtMatKhau.setError("Không được để trống");
                    input_txtMatKhau.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtMatKhau.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }

                if (Password.length() < 6) {
                    input_txtMatKhau.setError("Mật khẩu phải từ 6 ký tự trở lên");
                    input_txtMatKhau.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtMatKhau.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }

                if (!Password.matches("^[A-Za-z0-9@#$%!-_]{6,20}$")) {
                    input_txtMatKhau.setError("Mật khẩu chứa số từ 0 - 9, ký tự thường, in hoa và một số ký tự đặc biệt");
                    input_txtMatKhau.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtMatKhau.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * Bắt lỗi Nhập lại mật khẩu
         */
        final TextInputLayout input_txtNhapLaiMatKhau = view.findViewById(R.id.input_edNhapLaiMatKhauDK);
        input_txtNhapLaiMatKhau.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                TextInputEditText edPassword = getActivity().findViewById(R.id.edMatKhauDK);
                String password = edPassword.getText().toString();
                if (!charSequence.toString().equals(password)) {
                    input_txtNhapLaiMatKhau.setError("Mật khẩu không khớp");
                    input_txtNhapLaiMatKhau.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_txtNhapLaiMatKhau.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * Bắt lỗi Ngày Sinh
         */

        final TextInputLayout input_dpNgaySinh = view.findViewById(R.id.input_edNgaySinhDK);
        final DatePicker dpNgaySinh = view.findViewById(R.id.dpNgaySinhDK);


        Date currentDate = GetCurrentDate();

        dpNgaySinh.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                String sDate = day + "/" + (month + 1) + "/" + year;
                try {
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate);
                    if (date.after(currentDate)) {
                        input_dpNgaySinh.setError("Ngày lựa chọn phải nhỏ hơn ngày hiện tại");
                        input_dpNgaySinh.setErrorEnabled(true);
                        setupButton(btnDangKy, false);
                    }
                    else {
                        input_dpNgaySinh.setErrorEnabled(false);
                        setupButton(btnDangKy, true);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Bắt lỗi Số điện thoại
         */

        TextInputLayout input_SDTDK = view.findViewById(R.id.input_edSDTDK);
        input_SDTDK.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String SDT = new String(charSequence.toString());
                if (!SDT.matches("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
                    input_SDTDK.setError("Sai định dạng số điện thoại");
                    input_SDTDK.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_SDTDK.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ApiService.apiService.KiemTraDuLieu("SDT", editable.toString()).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse res = response.body();
                        if (res.getMessage().equals("Exist")) {
                            input_SDTDK.setError("Số điện thoại này đã được đăng ký trong hệ thống");
                            input_SDTDK.setErrorEnabled(true);
                            setupButton(btnDangKy, false);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        /**
         * Bắt lỗi Số CMND/CCCD
         */

        TextInputLayout input_edCMND = view.findViewById(R.id.input_edCMNDDK);
        input_edCMND.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!(charSequence.toString().length() == 9 || charSequence.toString().length() == 12)) {
                    input_edCMND.setError("CMND/CCCD là dãy 9 hoặc 12 số");
                    input_edCMND.setErrorEnabled(true);
                    setupButton(btnDangKy, false);
                }
                else {
                    input_edCMND.setErrorEnabled(false);
                    setupButton(btnDangKy, true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ApiService.apiService.KiemTraDuLieu("CMND", editable.toString()).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse res = response.body();
                        if (res.getMessage().equals("Exist")) {
                            input_edCMND.setError("Số CMND/CCCD này đã được đăng ký trong hệ thống");
                            input_edCMND.setErrorEnabled(true);
                            setupButton(btnDangKy, false);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
