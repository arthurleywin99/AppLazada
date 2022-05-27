package com.example.applazada.View.DonDatHang;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.applazada.Adapter.ViewPagerAdapterDonMua;
import com.example.applazada.R;
import com.google.android.material.tabs.TabLayout;

public class DonMuaActivity extends AppCompatActivity {
    protected TabLayout tabLayout;
    protected ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_donhangdadat);

        /**
         * Khởi tạo control
         */
        InitControl();

        /**
         * Gán viewpager đơn mua (đã huỷ, đang giao, đang xử lý, đã nhận) vào tablayout
         */
        ViewPagerAdapterDonMua viewPagerAdapterDonMua = new ViewPagerAdapterDonMua(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDonMua);
        viewPagerAdapterDonMua.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        tabLayout = findViewById(R.id.tabDonMua);
        viewPager = findViewById(R.id.viewPagerDonMua);
    }
}
