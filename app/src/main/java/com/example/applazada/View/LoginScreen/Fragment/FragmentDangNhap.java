package com.example.applazada.View.LoginScreen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.applazada.API.ApiService;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.example.applazada.View.TrangChu.TrangChuActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDangNhap extends Fragment {
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_dangnhap, container, false);
        setupFloatingLabelError(view);

        TextView txtDangKy = view.findViewById(R.id.btnDangKyDN);
        /**
         * Đổi sang Tab đăng ký khi nhấn vào TextView Đăng ký
         */
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabLayout tabLayout = getActivity().findViewById(R.id.tabDangNhap);
                tabLayout.getTabAt(1).select();
            }
        });

        /**
         * Nút Đăng nhập
         */
        Button btnDangNhap = view.findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText input_username = getActivity().findViewById(R.id.edEmailDangNhap);
                TextInputEditText input_password = getActivity().findViewById(R.id.edPasswordDangNhap);

                String username = input_username.getText().toString();
                /**
                 * Mã hoá mật khẩu bằng MD5
                 */
                String password = LocalVariablesAndMethods.MD5Hash(input_password.getText().toString());

                /**
                 * Kiểm tra dữ liệu nhập vào có tồn tại trong DB hay không
                 */
                ApiService.apiService.DangNhap(username, password).enqueue(new Callback<NhanVien>() {
                    @Override
                    public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                        NhanVien res = response.body();
                        if (res == null) {
                            Toast.makeText(getActivity(), "Sai email hoặc mật khẩu", Toast.LENGTH_LONG).show();
                        }
                        else {
                            modelNhanVien.MoKetNoiSQL(getContext());
                            modelNhanVien.ThemNhanVien(res);
                            Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), TrangChuActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<NhanVien> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }

    /**
     * Bắt lỗi các Control
     * @param view
     */
    private void setupFloatingLabelError(View view) {
        /**
         * Bắt lỗi Email đăng nhập
         */
        final TextInputLayout input_edEmail = view.findViewById(R.id.input_edEmailDangNhap);
        input_edEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Email = new String(charSequence.toString());
                if (Email.length() == 0) {
                    input_edEmail.setError("Email không được để trống");
                    input_edEmail.setErrorEnabled(true);
                }
                else {
                    input_edEmail.setErrorEnabled(false);
                }

                if (!Email.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
                    input_edEmail.setError("Không đúng định dạng Email");
                    input_edEmail.setErrorEnabled(true);
                }
                else {
                    input_edEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /**
         * Bắt lỗi mật khẩu đăng nhập
         */

        final TextInputLayout input_edPassword = view.findViewById(R.id.input_edMatKhauDangNhap);
        input_edPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Password = new String(charSequence.toString());
                if (Password.length() == 0) {
                    input_edPassword.setError("Không được để trống");
                    input_edPassword.setErrorEnabled(true);
                }
                if (Password.length() < 6) {
                    input_edPassword.setError("Mật khẩu phải từ 6 ký tự trở lên");
                    input_edPassword.setErrorEnabled(true);
                }
                if (!Password.matches("^[A-Za-z0-9@#$%!-_]{6,20}$")) {
                    input_edPassword.setError("Mật khẩu chứa số từ 0 - 9, ký tự thường, in hoa và một số ký tự đặc biệt");
                    input_edPassword.setErrorEnabled(true);
                }
                else {
                    input_edPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
