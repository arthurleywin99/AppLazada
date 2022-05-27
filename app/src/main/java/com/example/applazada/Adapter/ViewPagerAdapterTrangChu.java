package com.example.applazada.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.applazada.View.TrangChu.Fragment.FragmentDienTu;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterTrangChu extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<>();
    List<String> listTitle = new ArrayList<>();

    public ViewPagerAdapterTrangChu(@NonNull FragmentManager fm) {
        super(fm);

        /*listFragment.add(new FragmentNoiBat());
        listFragment.add(new FragmentChuongTrinhKhuyenMai());*/
        listFragment.add(new FragmentDienTu());
        /*listFragment.add(new FragmentNhaCuaVaDoiSong());
        listFragment.add(new FragmentMeVaBe());
        listFragment.add(new FragmentLamDep());
        listFragment.add(new FragmentThoiTrang());
        listFragment.add(new FragmentTheThaoVaDuLich());
        listFragment.add(new FragmentThuongHieu());*/

        /*listTitle.add("Nổi Bật");
        listTitle.add("Chương trình khuyến mãi");*/
        listTitle.add("Điện Tử");
        /*listTitle.add("Nhà cửa & đời sống");
        listTitle.add("Mẹ và bé");
        listTitle.add("Làm đẹp");
        listTitle.add("Thời trang");
        listTitle.add("Thể thao & du lịch");
        listTitle.add("Thương hiệu");*/
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}
