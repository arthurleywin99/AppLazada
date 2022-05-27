package com.example.applazada.View.LoginScreen;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.applazada.Adapter.ViewPagerAdapterDangNhap;
import com.example.applazada.R;
import com.google.android.material.tabs.TabLayout;

public class DangNhapActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        tabLayout = findViewById(R.id.tabDangNhap);
        viewPager = findViewById(R.id.viewPagerDangNhap);

        ViewPagerAdapterDangNhap viewPagerAdapterDangNhap = new ViewPagerAdapterDangNhap(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDangNhap);
        viewPagerAdapterDangNhap.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
    }
}
