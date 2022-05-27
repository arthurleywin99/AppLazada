package com.example.applazada.View.TrangChu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.Adapter.AdapterDienTu;
import com.example.applazada.Model.DienTu;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.ThuongHieu;
import com.example.applazada.Presenter.TrangChu_DienTu.PresenterLogicDienTu;
import com.example.applazada.R;
import com.example.applazada.View.TrangChu.ViewDienTu;

import java.util.ArrayList;
import java.util.List;

public class FragmentDienTu extends Fragment implements ViewDienTu {
    RecyclerView recyclerView;
    List<DienTu> dienTuList;
    PresenterLogicDienTu presenterLogicDienTu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dientu, container, false);
        recyclerView = view.findViewById(R.id.recyclerDienTu);
        presenterLogicDienTu = new PresenterLogicDienTu(this);
        presenterLogicDienTu.LayDanhSachDienTu();
        dienTuList = new ArrayList<>();
        return view;
    }

    @Override
    public void HienThiDSThuongHieuVaTopDTMayTinhBang(List<ThuongHieu> thuongHieuList, List<SanPham> sanPhamList) {
        DienTu dienTu = new DienTu();
        dienTu.setThuongHieuList(thuongHieuList);
        dienTu.setSanPhamList(sanPhamList);
        dienTuList.add(dienTu);

        AdapterDienTu adapterDienTu = new AdapterDienTu(getContext(), dienTuList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDienTu);
        adapterDienTu.notifyDataSetChanged();
    }
}
