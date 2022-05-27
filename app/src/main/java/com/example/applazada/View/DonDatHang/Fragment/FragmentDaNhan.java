package com.example.applazada.View.DonDatHang.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.Adapter.AdapterSanPhamDaNhan;
import com.example.applazada.Adapter.AdapterSanPhamDangXuLy;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.SanPhamMua;
import com.example.applazada.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDaNhan extends Fragment {
    ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_damua, container, false);
        RecyclerView recyclerDonMua = view.findViewById(R.id.recyclerDonMua);

        modelNhanVien.MoKetNoiSQL(getContext());
        NhanVien nhanVien = modelNhanVien.LayNhanVien();

        /**
         * Lấy danh sách đơn hàng bằng tình trạng đơn là "ĐÃ NHẬN" với user hiện tại đăng nhập
         */
        ApiService.apiService.LayDonHangBangTinhTrangDon(nhanVien.getMaNV(), LocalVariablesAndMethods.DANHAN).enqueue(new Callback<List<SanPhamMua>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<SanPhamMua>> call, Response<List<SanPhamMua>> response) {
                List<SanPhamMua> sanPhamList = response.body();
                recyclerDonMua.setLayoutManager(null);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                AdapterSanPhamDaNhan adapterSanPhamDaNhan = new AdapterSanPhamDaNhan(getContext(), sanPhamList);
                recyclerDonMua.setLayoutManager(layoutManager);
                recyclerDonMua.setAdapter(adapterSanPhamDaNhan);
                adapterSanPhamDaNhan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SanPhamMua>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
