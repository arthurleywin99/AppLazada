package com.example.applazada.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.SanPhamMua;
import com.example.applazada.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSanPhamDaHuy extends RecyclerView.Adapter<AdapterSanPhamDaHuy.ViewHolderSanPhamDaHuy>{
    Context context;
    List<SanPhamMua> sanPhamList;

    public AdapterSanPhamDaHuy(Context context, List<SanPhamMua> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    public class ViewHolderSanPhamDaHuy extends RecyclerView.ViewHolder {
        TextView txtTenSanPham, txtSoLuong, txtDonGia, txtTongTien, txtTrangThai;
        Button btnXuLy;

        public ViewHolderSanPhamDaHuy(@NonNull View itemView) {
            super(itemView);

            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            btnXuLy = itemView.findViewById(R.id.btnXuLy);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
        }
    }

    @NonNull
    @Override
    public AdapterSanPhamDaHuy.ViewHolderSanPhamDaHuy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_chitietdonmua, parent, false);
        AdapterSanPhamDaHuy.ViewHolderSanPhamDaHuy viewHolderSanPhamDaHuy = new AdapterSanPhamDaHuy.ViewHolderSanPhamDaHuy(view);
        return viewHolderSanPhamDaHuy;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AdapterSanPhamDaHuy.ViewHolderSanPhamDaHuy holder, int position) {
        SanPhamMua sanPham = sanPhamList.get(position);

        NumberFormat numberFormat = new DecimalFormat("###,###");

        holder.txtTenSanPham.setText(sanPham.getTenSP());
        holder.txtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));

        String donGia = numberFormat.format(sanPham.getGia()).toString();
        holder.txtDonGia.setText(donGia);

        String tongTien = numberFormat.format(sanPham.getGia() * sanPham.getSoLuong()).toString();
        holder.txtTongTien.setText(tongTien + " VNĐ");

        holder.txtTrangThai.setText(LocalVariablesAndMethods.DAHUY);

        holder.btnXuLy.setText("Đã Huỷ");
        holder.btnXuLy.setBackground(context.getResources().getDrawable(R.drawable.bg_shape_corner_color_red));
        holder.btnXuLy.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
