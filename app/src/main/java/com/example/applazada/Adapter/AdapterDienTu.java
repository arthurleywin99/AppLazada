package com.example.applazada.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.Model.DienTu;
import com.example.applazada.R;

import java.util.List;

public class AdapterDienTu extends RecyclerView.Adapter<AdapterDienTu.ViewHolderDienTu> {
    Context context;
    List<DienTu> dienTuList;

    public AdapterDienTu(Context context, List<DienTu> dienTuList) {
        this.context = context;
        this.dienTuList = dienTuList;
    }

    public class ViewHolderDienTu extends RecyclerView.ViewHolder {
        ImageView imHinhKhuyenMaiDienTu;
        RecyclerView recyclerViewThuongHieuLon, recyclerViewTopSanPham;

        public ViewHolderDienTu(@NonNull View itemView) {
            super(itemView);

            recyclerViewThuongHieuLon = itemView.findViewById(R.id.recyclerThuongHieuLon);
            recyclerViewTopSanPham = itemView.findViewById(R.id.recyclerTopDienThoaiMayTinhBang);
            imHinhKhuyenMaiDienTu = itemView.findViewById(R.id.imHinhDienTu);
        }
    }

    @NonNull
    @Override
    public ViewHolderDienTu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_recyclerview_dientu, parent, false);
        ViewHolderDienTu viewHolderDienTu = new ViewHolderDienTu(view);
        return viewHolderDienTu;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDienTu holder, int position) {
        DienTu dienTu = dienTuList.get(position);

        // Hiển thị Danh sách thương hiệu lớn (RecyclerView Thương Hiệu Lớn)
        AdapterThuongHieuLon adapterThuongHieuLon = new AdapterThuongHieuLon(context, dienTu.getThuongHieuList());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false);
        holder.recyclerViewThuongHieuLon.setLayoutManager(layoutManager);
        holder.recyclerViewThuongHieuLon.setAdapter(adapterThuongHieuLon);
        adapterThuongHieuLon.notifyDataSetChanged();

        // Hiển thị Danh sách top sản phẩm (RecyclerView Top Sản Phẩm)
        AdapterCardSanPham adapterTopDienThoaiDienTu = new AdapterCardSanPham(context, dienTu.getSanPhamList());
        RecyclerView.LayoutManager layoutManagerTop = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewTopSanPham.setLayoutManager(layoutManagerTop);
        holder.recyclerViewTopSanPham.setAdapter(adapterTopDienThoaiDienTu);
        adapterTopDienThoaiDienTu.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dienTuList.size();
    }
}
