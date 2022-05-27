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
import com.example.applazada.Adapter.AdapterSanPhamDangGiao;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPhamMua;
import com.example.applazada.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDangGiao extends Fragment {
    ModelNhanVien modelNhanVien = new ModelNhanVien();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_damua, container, false);
        RecyclerView recyclerDonMua = view.findViewById(R.id.recyclerDonMua);

        modelNhanVien.MoKetNoiSQL(getContext());
        NhanVien nhanVien = modelNhanVien.LayNhanVien();

        /**
         * Lấy danh sách đơn hàng bằng tình trạng đơn là "ĐANG GIAO" với user hiện tại đăng nhập
         */
        ApiService.apiService.LayDonHangBangTinhTrangDon(nhanVien.getMaNV(), LocalVariablesAndMethods.DANGGIAO).enqueue(new Callback<List<SanPhamMua>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<SanPhamMua>> call, @NonNull Response<List<SanPhamMua>> response) {
                List<SanPhamMua> sanPhamList = response.body();
                recyclerDonMua.setLayoutManager(null);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                AdapterSanPhamDangGiao adapterSanPhamDangGiao = new AdapterSanPhamDangGiao(getContext(), sanPhamList);
                recyclerDonMua.setLayoutManager(layoutManager);
                recyclerDonMua.setAdapter(adapterSanPhamDangGiao);
                adapterSanPhamDangGiao.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<SanPhamMua>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
