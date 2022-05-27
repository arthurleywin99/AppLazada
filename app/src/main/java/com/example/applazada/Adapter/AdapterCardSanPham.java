package com.example.applazada.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;
import com.example.applazada.View.ChiTietSanPham.ChiTietSanPhamActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterCardSanPham extends RecyclerView.Adapter<AdapterCardSanPham.ViewHolderTopDienThoaiDienTu> {
    Context context;
    List<SanPham> sanPhamList;

    public AdapterCardSanPham(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    public class ViewHolderTopDienThoaiDienTu extends RecyclerView.ViewHolder {
        ImageView imHinhSanPham;
        TextView txtTenSP, txtGiaTien, txtGiamGia;
        CardView cardView;

        public ViewHolderTopDienThoaiDienTu(@NonNull View itemView) {
            super(itemView);
            imHinhSanPham = itemView.findViewById(R.id.imTopDienThoaiDienTu);
            txtTenSP = itemView.findViewById(R.id.txtTieuDeTopDienThoaiDienTu);
            txtGiaTien = itemView.findViewById(R.id.txtGiaDienTu);
            txtGiamGia = itemView.findViewById(R.id.txtGiamGiaDienTu);
            cardView = itemView.findViewById(R.id.cardViewSanPham);
        }
    }

    @NonNull
    @Override
    public ViewHolderTopDienThoaiDienTu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_cardsanpham, parent, false);
        ViewHolderTopDienThoaiDienTu viewHolderTopDienThoaiDienTu = new ViewHolderTopDienThoaiDienTu(view);
        return viewHolderTopDienThoaiDienTu;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTopDienThoaiDienTu holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        String imageUrl = LocalVariablesAndMethods.domain + "/api/" + sanPham.getAnhLon().split(";")[0];
        Picasso.get().load(imageUrl).resize(140, 140).centerInside().into(holder.imHinhSanPham);
        holder.txtTenSP.setText(sanPham.getTenSP());

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(sanPham.getGia()).toString();
        holder.txtGiaTien.setText(gia + " VNĐ");
        holder.cardView.setTag(sanPham.getMaSP());
        //holder.txtGiamGia.setText(sanPham.getGia());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("MASP", (int) view.getTag());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
