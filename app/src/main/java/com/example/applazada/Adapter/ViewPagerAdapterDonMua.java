package com.example.applazada.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.applazada.View.DonDatHang.Fragment.FragmentDaHuy;
import com.example.applazada.View.DonDatHang.Fragment.FragmentDaNhan;
import com.example.applazada.View.DonDatHang.Fragment.FragmentDangGiao;
import com.example.applazada.View.DonDatHang.Fragment.FragmentDangXyLy;

public class ViewPagerAdapterDonMua extends FragmentPagerAdapter {
    public ViewPagerAdapterDonMua(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                FragmentDangXyLy fragmentDangXyLy = new FragmentDangXyLy();
                return fragmentDangXyLy;
            }
            case 1: {
                FragmentDangGiao fragmentDangGiao = new FragmentDangGiao();
                return fragmentDangGiao;
            }
            case 2: {
                FragmentDaNhan fragmentDaNhan = new FragmentDaNhan();
                return fragmentDaNhan;
            }
            case 3: {
                FragmentDaHuy fragmentDaHuy = new FragmentDaHuy();
                return fragmentDaHuy;
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: {
                return "Đang Xử Lý";
            }
            case 1: {
                return "Đang Giao";
            }
            case 2: {
                return "Đã Nhận";
            }
            case 3: {
                return "Đã Huỷ";
            }
            default: {
                return "";
            }
        }
    }
}
