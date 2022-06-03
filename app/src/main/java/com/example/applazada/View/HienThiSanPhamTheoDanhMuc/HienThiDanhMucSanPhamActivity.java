package com.example.applazada.View.HienThiSanPhamTheoDanhMuc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.Adapter.AdapterCardSanPham;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;
import com.example.applazada.View.DonDatHang.DonMuaActivity;
import com.example.applazada.View.GioHang.GioHangActivity;
import com.example.applazada.View.LoginScreen.DangNhapActivity;
import com.example.applazada.View.ThongTinTaiKhoan.ThongTinTaiKhoanActivity;
import com.example.applazada.View.TrangChu.TrangChuActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HienThiDanhMucSanPhamActivity extends AppCompatActivity {
    Button btnLoc, btnSapXep;
    ImageView imLoc, imSapXep;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ModelGioHang modelGioHang = new ModelGioHang();
    ModelNhanVien modelNhanVien = new ModelNhanVien();
    TextView txtGioHang;
    private boolean onPause = false;
    private boolean isUp_Loc = false, isUp_SapXep = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthisanphamtheodanhmuc);

        /**
         * Khởi tạo control
         */
        InitControl();

        /**
         * Nhận dữ liệu MaTH, TenTH từ Trang chủ với Adapter thuonghieulon
         */
        Intent intent = getIntent();
        int MaTH = intent.getIntExtra("MATH", 0);
        String TenTH = intent.getStringExtra("TENTH");

        /**
         * Lấy danh sách sản phẩm theo MaTH từ DB
         */
        ApiService.apiService.LayDanhSachSanPhamTheoMaTH(MaTH, TenTH).enqueue(new Callback<List<SanPham>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> sanPhamList = response.body();
                if (sanPhamList.size() != 0) {
                    SetAdapter(sanPhamList);
                }
                else {
                    Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Dữ liệu trống", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(HienThiDanhMucSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Nút sắp xếp theo tên sản phẩm
         */
        btnSapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUp_SapXep) {
                    imSapXep.setImageResource(R.drawable.arrowdown);
                    ApiService.apiService.LayDanhSachSanPhamTheoMaTH(MaTH, TenTH).enqueue(new Callback<List<SanPham>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                            List<SanPham> sanPhamList = response.body();

                            /*Sắp xếp*/
                            Comparator<SanPham> compareByName = new Comparator<SanPham>() {
                                @Override
                                public int compare(SanPham o1, SanPham o2) {
                                    return o1.getTenSP().compareTo(o2.getTenSP());
                                }
                            };
                            Collections.sort(sanPhamList, compareByName);

                            if (sanPhamList.size() != 0) {
                                SetAdapter(sanPhamList);
                            }
                            else {
                                Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Dữ liệu trống", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SanPham>> call, Throwable t) {
                            Toast.makeText(HienThiDanhMucSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Sắp xếp A - Z", Toast.LENGTH_SHORT).show();
                }
                else {
                    imSapXep.setImageResource(R.drawable.arrowup);
                    ApiService.apiService.LayDanhSachSanPhamTheoMaTH(MaTH, TenTH).enqueue(new Callback<List<SanPham>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                            List<SanPham> sanPhamList = response.body();

                            /*Sắp xếp*/
                            Comparator<SanPham> compareByName = new Comparator<SanPham>() {
                                @Override
                                public int compare(SanPham o1, SanPham o2) {
                                    return o1.getTenSP().compareTo(o2.getTenSP());
                                }
                            };
                            Collections.sort(sanPhamList, compareByName.reversed());

                            if (sanPhamList.size() != 0) {
                                SetAdapter(sanPhamList);
                            }
                            else {
                                Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Dữ liệu trống", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SanPham>> call, Throwable t) {
                            Toast.makeText(HienThiDanhMucSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Sắp xếp Z - A", Toast.LENGTH_SHORT).show();
                }
                isUp_SapXep = !isUp_SapXep;
            }
        });

        /**
         * Nút sắp xếp theo giá sản phẩm
         */
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUp_Loc) {
                    imLoc.setImageResource(R.drawable.arrowup);
                    ApiService.apiService.LayDanhSachSanPhamTheoMaTH(MaTH, TenTH).enqueue(new Callback<List<SanPham>>() {
                        @Override
                        public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                            List<SanPham> sanPhamList = response.body();

                            /*Sắp xếp*/
                            Comparator<SanPham> compareByName = new Comparator<SanPham>() {
                                @Override
                                public int compare(SanPham o1, SanPham o2) {
                                    if (o1.getGia() > o2.getGia()) {
                                        return 1;
                                    }
                                    else if (o1.getGia() == o2.getGia()) {
                                        return 0;
                                    }
                                    return -1;
                                }
                            };
                            Collections.sort(sanPhamList, compareByName);

                            if (sanPhamList.size() != 0) {
                                SetAdapter(sanPhamList);
                            }
                            else {
                                Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Dữ liệu trống", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SanPham>> call, Throwable t) {
                            Toast.makeText(HienThiDanhMucSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Sắp xếp tăng dần theo giá", Toast.LENGTH_SHORT).show();
                }
                else {
                    imLoc.setImageResource(R.drawable.arrowdown);
                    ApiService.apiService.LayDanhSachSanPhamTheoMaTH(MaTH, TenTH).enqueue(new Callback<List<SanPham>>() {
                        @Override
                        public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                            List<SanPham> sanPhamList = response.body();

                            /*Sắp xếp*/
                            Comparator<SanPham> compareByName = new Comparator<SanPham>() {
                                @Override
                                public int compare(SanPham o1, SanPham o2) {
                                    if (o1.getGia() > o2.getGia()) {
                                        return 1;
                                    }
                                    else if (o1.getGia() == o2.getGia()) {
                                        return 0;
                                    }
                                    return -1;
                                }
                            };
                            Collections.sort(sanPhamList, compareByName.reversed());

                            if (sanPhamList.size() != 0) {
                                SetAdapter(sanPhamList);
                            }
                            else {
                                Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Dữ liệu trống", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<SanPham>> call, Throwable t) {
                            Toast.makeText(HienThiDanhMucSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(HienThiDanhMucSanPhamActivity.this, "Sắp xếp giảm dần theo giá", Toast.LENGTH_SHORT).show();
                }
                isUp_Loc = !isUp_Loc;
            }
        });
    }

    /**
     * Khởi tạo Adapter Card sản phẩm nhận từ DB và trình bày dưới dạng GridLayout
     * @param sanPhamList
     */
    @SuppressLint("NotifyDataSetChanged")
    private void SetAdapter(List<SanPham> sanPhamList) {
        AdapterCardSanPham adapterTopDienThoaiDienTu = new AdapterCardSanPham(HienThiDanhMucSanPhamActivity.this, sanPhamList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HienThiDanhMucSanPhamActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterTopDienThoaiDienTu);
        adapterTopDienThoaiDienTu.notifyDataSetChanged();
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        recyclerView = findViewById(R.id.recyclerViewHienThiSanPhamTheoDanhMuc);
        toolbar = findViewById(R.id.toolBar);
        btnLoc = findViewById(R.id.btnLoc);
        btnSapXep = findViewById(R.id.btnSapXep);
        imLoc = findViewById(R.id.imLoc);
        imSapXep = findViewById(R.id.imSapXep);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    /**
     * Tạo menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);

        modelGioHang.MoKetNoiSQL(HienThiDanhMucSanPhamActivity.this);
        MenuItem iGioHang = menu.findItem(R.id.itShoppingCart);
        View giaoDienCustomGioHang = MenuItemCompat.getActionView(iGioHang);

        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, GioHangActivity.class));
            }
        });

        txtGioHang = giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);
        txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));

        return true;
    }

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
                    startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, DangNhapActivity.class));
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
                    startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, DangNhapActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, ThongTinTaiKhoanActivity.class));
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
                    startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, DangNhapActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, DonMuaActivity.class));
                }
                break;
            }
            /**
             * Đăng xuất
             */
            case R.id.itDangXuat: {
                startActivity(new Intent(HienThiDanhMucSanPhamActivity.this, DangNhapActivity.class));
                modelNhanVien.MoKetNoiSQL(this);
                NhanVien nhanVien = modelNhanVien.LayNhanVien();
                if (nhanVien != null) {
                    modelNhanVien.XoaNhanVien(nhanVien.getMaNV());
                }
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
}