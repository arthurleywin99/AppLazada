package com.example.applazada.View.TimKiem;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.Adapter.AdapterCardSanPham;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    protected Toolbar toolbar;
    protected RecyclerView recyclerView;
    String NoiDungTimKiem = "";
    protected LinearLayout lnTimKiem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timkiem);

        /**
         * Khởi tạo Control
         */
        InitControl();

        Intent intent = getIntent();
        NoiDungTimKiem = intent.getStringExtra("NOIDUNG");

        /**
         * Tìm kiếm sản phẩm bằng từ khoá "GẦN GIỐNG" với TenSP, LoaiSP, ThuongHieu
         */
        ApiService.apiService.TimKiemSanPham(NoiDungTimKiem).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> sanPhamList = response.body();
                if (sanPhamList.size() > 0) {
                    lnTimKiem.setVisibility(View.GONE);
                    SetAdapter(sanPhamList);
                }
                else {
                    lnTimKiem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(TimKiemActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Tạo menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timkiem, menu);
        MenuItem iTimKiem = menu.findItem(R.id.itSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(iTimKiem);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    /**
     * Submit text trong ô tìm kiếm
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        recyclerView.setAdapter(null);
        ApiService.apiService.TimKiemSanPham(query).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                List<SanPham> sanPhamList = response.body();
                if (sanPhamList.size() > 0) {
                    lnTimKiem.setVisibility(View.GONE);
                    SetAdapter(sanPhamList);
                }
                else {
                    lnTimKiem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(TimKiemActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        toolbar = findViewById(R.id.toolBar);
        recyclerView = findViewById(R.id.recyclerTimKiem);
        lnTimKiem = findViewById(R.id.lnTimKiem);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    /**
     * Set Adapter Card SanPham vào recyclerview của trang timkiem, trình bày dưới dạng GridView
     * @param sanPhamList
     */
    @SuppressLint("NotifyDataSetChanged")
    private void SetAdapter(List<SanPham> sanPhamList) {
        recyclerView.setLayoutManager(null);
        AdapterCardSanPham adapterTopDienThoaiDienTu = new AdapterCardSanPham(TimKiemActivity.this, sanPhamList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TimKiemActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterTopDienThoaiDienTu);
        adapterTopDienThoaiDienTu.notifyDataSetChanged();
    }
}
