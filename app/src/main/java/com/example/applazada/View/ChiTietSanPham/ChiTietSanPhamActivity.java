package com.example.applazada.View.ChiTietSanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.applazada.API.ApiService;
import com.example.applazada.Adapter.AdapterDanhGia;
import com.example.applazada.Adapter.AdapterViewPagerSlider;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.DanhGia;
import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;
import com.example.applazada.View.GioHang.GioHangActivity;
import com.example.applazada.View.ThanhToan.ThanhToanActivity;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView[] textViewDots;
    private LinearLayout linearLayoutDot;
    private List<Fragment> fragmentSliderList;
    private TextView txtTenSP, txtGiaSP, txtThongTinCT, txtGioHang, txtSoLuongTon, txtSoDanhGia;
    protected Toolbar toolbarCTSP;
    protected Button btnMuaNgay;
    private ImageView imXemThem;
    private Boolean isExpanded = false;
    private ImageButton imgThemGioHang;
    protected ModelGioHang modelGioHang = new ModelGioHang();
    protected ModelNhanVien modelNhanVien = new ModelNhanVien();
    protected RatingBar rbDanhGia;
    protected RecyclerView recyclerDanhGia;
    private boolean onPause = false, onLiked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietsanpham);

        /**
         * Khởi tạo các control
         */
        InitControl();

        /**
         * Lấy dữ liệu được gửi từ AdapterCardSanPham
         */
        int MaSP = getIntent().getIntExtra("MASP", 0);
        modelNhanVien.MoKetNoiSQL(ChiTietSanPhamActivity.this);

        /**
         * Lấy sản phẩm từ DB thông qua MaSP và hiển thị lên View
         */
        ApiService.apiService.LaySanPhamBangMaSP(MaSP).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                SanPham sanPham = response.body();
                if (sanPham.getSoLuongTonKho() == 0) {
                    btnMuaNgay.setText("HẾT HÀNG");
                    btnMuaNgay.setEnabled(false);
                    imgThemGioHang.setEnabled(false);
                }
                String[] linkHinhSP = sanPham.getAnhLon().split(";");
                HienThiSliderSanPham(linkHinhSP);
                ThemDotSlider(0);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        /**
                         * Thêm hiệu ứng dấu chấm tròn dưới ảnh
                         */
                        ThemDotSlider(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                String gia = ChuyenDinhDangTien(sanPham.getGia()) + " VNĐ";
                txtTenSP.setText(sanPham.getTenSP());
                txtGiaSP.setText(gia);
                if (sanPham.getThongTin().length() > 100) {
                    txtThongTinCT.setText(sanPham.getThongTin().substring(0, 100));
                }
                else {
                    txtThongTinCT.setText(sanPham.getThongTin());
                }
                txtSoLuongTon.setText(String.valueOf(sanPham.getSoLuongTonKho()));

                /**
                 * Mũi tên xem thêm
                 */
                if (sanPham.getThongTin().length() < 100) {
                    imXemThem.setVisibility(View.GONE);
                }
                else {
                    imXemThem.setVisibility(View.VISIBLE);
                    imXemThem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isExpanded = !isExpanded;
                            if (isExpanded) {
                                txtThongTinCT.setText(sanPham.getThongTin());
                                imXemThem.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                            }
                            else {
                                txtThongTinCT.setText(sanPham.getThongTin().substring(0, 100));
                                imXemThem.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                            }
                        }
                    });
                }

                /**
                 * Nút thêm giỏ hàng
                 */
                imgThemGioHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = fragmentSliderList.get(viewPager.getCurrentItem());
                        View viewImage = fragment.getView();
                        ImageView imageView = viewImage.findViewById(R.id.imHinhSlider);
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] hinhGioHang = byteArrayOutputStream.toByteArray();

                        sanPham.setHinhGioHang(hinhGioHang);
                        sanPham.setSoLuong(1);

                        modelGioHang.MoKetNoiSQL(ChiTietSanPhamActivity.this);
                        boolean check = modelGioHang.ThemGioHang(sanPham);

                        if (check) {
                            Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));
                        }
                        else {
                            int soLuongDaTonTai = modelGioHang.LaySoLuongSanPhamDaCoTrongGioHang(sanPham.getMaSP());
                            int soLuongTonTrongKho = Integer.parseInt(txtSoLuongTon.getText().toString());

                            if (soLuongDaTonTai < soLuongTonTrongKho) {
                                boolean check2 = modelGioHang.CapNhatSanPhamTrongGioHang(sanPham.getMaSP(), soLuongDaTonTai + 1);
                                if (check2) {
                                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thất bại. Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thất bại. Kho đã hết hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                /**
                 * Nút mua ngay
                 */
                btnMuaNgay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = fragmentSliderList.get(viewPager.getCurrentItem());
                        View viewImage = fragment.getView();
                        ImageView imageView = viewImage.findViewById(R.id.imHinhSlider);
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] hinhGioHang = byteArrayOutputStream.toByteArray();

                        sanPham.setHinhGioHang(hinhGioHang);
                        sanPham.setSoLuong(1);

                        modelGioHang.MoKetNoiSQL(ChiTietSanPhamActivity.this);
                        boolean check = modelGioHang.ThemGioHang(sanPham);

                        if (check) {
                            Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));
                            startActivity(new Intent(ChiTietSanPhamActivity.this, ThanhToanActivity.class));
                        }
                        else {
                            int soLuongDaTonTai = modelGioHang.LaySoLuongSanPhamDaCoTrongGioHang(sanPham.getMaSP());
                            int soLuongTonTrongKho = Integer.parseInt(txtSoLuongTon.getText().toString());

                            if (soLuongDaTonTai < soLuongTonTrongKho) {
                                boolean check2 = modelGioHang.CapNhatSanPhamTrongGioHang(sanPham.getMaSP(), soLuongDaTonTai + 1);
                                if (check2) {
                                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChiTietSanPhamActivity.this, ThanhToanActivity.class));
                                }
                                else {
                                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thất bại. Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thất bại. Kho đã hết hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Load Đánh Giá Sản Phẩm (Số Sao Trung Bình, Bình Luận)
         */
        ApiService.apiService.LayDanhGiaSanPhamBangMaSP(MaSP).enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                List<DanhGia> danhGiaList = response.body();
                LoadSoSaoVaBinhLuan(danhGiaList);
                LoadBinhLuan(danhGiaList);
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Khởi tạo các Control
     */
    private void InitControl() {
        viewPager = findViewById(R.id.viewPagerSlider);
        linearLayoutDot = findViewById(R.id.layoutDots);
        txtTenSP = findViewById(R.id.txtTenSP);
        txtGiaSP = findViewById(R.id.txtGiaSP);
        toolbarCTSP = findViewById(R.id.toolBar);
        txtThongTinCT = findViewById(R.id.txtThongTinChiTiet);
        imXemThem = findViewById(R.id.imXemThemCT);
        //linearLayoutThongSoKyThuat = findViewById(R.id.lnThongSoKyThuat);
        imgThemGioHang = findViewById(R.id.imThemGioHang);
        txtSoLuongTon = findViewById(R.id.txtSoLuongTon);
        btnMuaNgay = findViewById(R.id.btnMuaNgay);
        rbDanhGia = findViewById(R.id.rbDanhGia);
        txtSoDanhGia = findViewById(R.id.txtSoDanhGia);
        recyclerDanhGia = findViewById(R.id.recyclerDanhGia);

        toolbarCTSP.setTitle("");
        setSupportActionBar(toolbarCTSP);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * Load danh sách bình luận vào recyclerDanhGia
     * @param danhGiaList
     */
    private void LoadBinhLuan(List<DanhGia> danhGiaList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChiTietSanPhamActivity.this);
        AdapterDanhGia adapterDanhGia = new AdapterDanhGia(ChiTietSanPhamActivity.this, danhGiaList);
        recyclerDanhGia.setLayoutManager(layoutManager);
        recyclerDanhGia.setAdapter(adapterDanhGia);
        adapterDanhGia.notifyDataSetChanged();
    }

    /**
     * Định dạng tiền: 10.000.000
     * @param giaTien
     * @return
     */
    private String ChuyenDinhDangTien(long giaTien) {
        return new DecimalFormat("###,###").format(giaTien).toString();
    }

    /**
     * Load số sao và bình luận
     * @param danhGiaList
     */
    private void LoadSoSaoVaBinhLuan(@NonNull List<DanhGia> danhGiaList) {
        float tong = 0.0f;
        for (DanhGia danhGia : danhGiaList) {
            tong += danhGia.getSoSao();
        }
        float soSao = tong / danhGiaList.size();
        if (soSao < 1) {
            if (soSao > 0) {
                soSao = 0.5f;
            }
        } else if (soSao < 2) {
            if (soSao >= 1.5) {
                soSao = 1.5f;
            } else {
                soSao = 1f;
            }
        } else if (soSao < 3) {
            if (soSao >= 2.5) {
                soSao = 2.5f;
            } else {
                soSao = 2f;
            }
        } else if (soSao < 4) {
            if (soSao >= 3.5) {
                soSao = 3.5f;
            } else {
                soSao = 3f;
            }
        } else if (soSao < 5) {
            if (soSao >= 4.5) {
                soSao = 4.5f;
            } else {
                soSao = 4f;
            }
        } else if (soSao == 5) {
            soSao = 5;
        } else {
            soSao = 0;
        }
        rbDanhGia.setRating(soSao);
        txtSoDanhGia.setText(String.valueOf(danhGiaList.size()) + " Đánh giá");
    }

    /**
     * Tạo menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trangchu, menu);

        modelGioHang.MoKetNoiSQL(ChiTietSanPhamActivity.this);
        MenuItem iGioHang = menu.findItem(R.id.itShoppingCart);
        View giaoDienCustomGioHang = MenuItemCompat.getActionView(iGioHang);

        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class));
            }
        });

        txtGioHang = giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);
        txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));

        return true;
    }

    /**
     * Tạo slider ảnh sản phẩm
     * @param linkHinhSP
     */
    private void HienThiSliderSanPham(String[] linkHinhSP) {
        fragmentSliderList = new ArrayList<>();

        for (int i = 0; i < linkHinhSP.length; ++i) {
            FragmentSliderChiTietSanPham fragmentSliderChiTietSanPham = new FragmentSliderChiTietSanPham();
            Bundle bundle = new Bundle();
            bundle.putString("LINKHINH", LocalVariablesAndMethods.domain + "/api/" + linkHinhSP[i]);
            fragmentSliderChiTietSanPham.setArguments(bundle);

            fragmentSliderList.add(fragmentSliderChiTietSanPham);
        }
        AdapterViewPagerSlider adapterViewPagerSlider = new AdapterViewPagerSlider(getSupportFragmentManager(), fragmentSliderList);
        viewPager.setAdapter(adapterViewPagerSlider);
        adapterViewPagerSlider.notifyDataSetChanged();
    }

    /**
     * Dấu chấm dưới hình slider
     * @param position
     */
    private void ThemDotSlider(int position) {
        textViewDots = new TextView[fragmentSliderList.size()];
        linearLayoutDot.removeAllViews();
        for (int i = 0; i < textViewDots.length; ++i) {
            textViewDots[i] = new TextView(this);
            textViewDots[i].setText(Html.fromHtml("&#8226;"));
            textViewDots[i].setTextSize(30);
            textViewDots[i].setTextColor(getResources().getColor(R.color.gray));

            linearLayoutDot.addView(textViewDots[i]);
        }
        textViewDots[position].setTextColor(getResources().getColor(R.color.bgToolbar));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (onPause) {
            modelGioHang.MoKetNoiSQL(ChiTietSanPhamActivity.this);
            txtGioHang.setText(String.valueOf(modelGioHang.LayDanhSachSanPhamTrongGioHang().size()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }
}
