package com.example.applazada.View.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.applazada.Adapter.ViewPagerAdapterTrangChu;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.R;
import com.example.applazada.View.DonDatHang.DonMuaActivity;
import com.example.applazada.View.GioHang.GioHangActivity;
import com.example.applazada.View.LoginScreen.DangNhapActivity;
import com.example.applazada.View.ThongTinTaiKhoan.ThongTinTaiKhoanActivity;
import com.example.applazada.View.TimKiem.TimKiemActivity;
import com.google.android.material.tabs.TabLayout;

public class TrangChuActivity extends AppCompatActivity implements View.OnClickListener {
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected DrawerLayout drawerLayout;
    protected ModelGioHang modelGioHang = new ModelGioHang();
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();
    private TextView txtGioHang;
    protected Button btnTimKiem;
    protected EditText edNoiDungTimKiem;
    private boolean onPause = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        /**
         * Khởi tạo control
         */
        InitControl();

        btnTimKiem.setOnClickListener(this);

        /**
         * Gán viewpager vào tablayout (nhưng ở đây chỉ có Điện Tử vì thời gian phát triển không đủ)
         */
        ViewPagerAdapterTrangChu adapter = new ViewPagerAdapterTrangChu(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /**
             * Nút tìm kiếm, gửi nội dung qua Activity TimKiem
             */
            case R.id.btnTimKiem: {
                String noiDungTimKiem = edNoiDungTimKiem.getText().toString().trim();
                Intent intent = new Intent(TrangChuActivity.this, TimKiemActivity.class);
                intent.putExtra("NOIDUNG", noiDungTimKiem);
                startActivity(intent);
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Tạo menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);

        modelGioHang.MoKetNoiSQL(TrangChuActivity.this);
        MenuItem iGioHang = menu.findItem(R.id.itShoppingCart);
        View giaoDienCustomGioHang = MenuItemCompat.getActionView(iGioHang);

        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangChuActivity.this, GioHangActivity.class));
            }
        });

        txtGioHang = giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);
        txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));

        return true;
    }

    /**
     * Các OptionMenuItem trong dấu ...
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            /**
             * Đăng nhập
             */
            case R.id.itLogin: {
                modelNhanVien.MoKetNoiSQL(this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();

                if (nhanVien != null) {
                    Toast.makeText(this, "Bạn đã đăng nhập rồi", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    startActivity(new Intent(TrangChuActivity.this, DangNhapActivity.class));
                    finish();
                }
                break;
            }
            /**
             * Thông tin tài khoản
             */
            case R.id.itSetting: {
                modelNhanVien.MoKetNoiSQL(this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();
                if (nhanVien == null) {
                    startActivity(new Intent(TrangChuActivity.this, DangNhapActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(TrangChuActivity.this, ThongTinTaiKhoanActivity.class));
                }
                break;
            }
            /**
             * Đơn hàng của tôi
             */
            case R.id.itMyOrder: {
                modelNhanVien.MoKetNoiSQL(this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();
                if (nhanVien == null) {
                    startActivity(new Intent(TrangChuActivity.this, DangNhapActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(TrangChuActivity.this, DonMuaActivity.class));
                }
                break;
            }
            /**
             * Đăng xuất
             */
            case R.id.itDangXuat: {
                startActivity(new Intent(TrangChuActivity.this, DangNhapActivity.class));
                modelNhanVien.MoKetNoiSQL(this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();
                modelNhanVien.XoaNhanVien(nhanVien.getMaNV());
                finish();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (onPause) {
            txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        onPause = true;
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        toolbar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        drawerLayout = findViewById(R.id.drawerLayout);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        edNoiDungTimKiem = findViewById(R.id.edNoiDungTimKiem);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
}
