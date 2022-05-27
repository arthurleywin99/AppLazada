package com.example.applazada.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.DanhGia;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.SanPhamMua;
import com.example.applazada.R;
import com.example.applazada.View.DanhGiaSanPham.DanhGiaSanPhamActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSanPhamDaNhan extends RecyclerView.Adapter<AdapterSanPhamDaNhan.ViewHolderDaNhan>{
    Context context;
    List<SanPhamMua> sanPhamList;
    ModelNhanVien modelNhanVien = new ModelNhanVien();

    public AdapterSanPhamDaNhan(Context context, List<SanPhamMua> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    public class ViewHolderDaNhan extends RecyclerView.ViewHolder {
        TextView txtTenSanPham, txtSoLuong, txtDonGia, txtTongTien, txtTrangThai;
        Button btnXuLy;

        public ViewHolderDaNhan(@NonNull View itemView) {
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
    public AdapterSanPhamDaNhan.ViewHolderDaNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_chitietdonmua, parent, false);
        AdapterSanPhamDaNhan.ViewHolderDaNhan viewHolderDaNhan = new AdapterSanPhamDaNhan.ViewHolderDaNhan(view);
        return viewHolderDaNhan;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AdapterSanPhamDaNhan.ViewHolderDaNhan holder, int position) {
        SanPhamMua sanPham = sanPhamList.get(position);

        NumberFormat numberFormat = new DecimalFormat("###,###");

        holder.txtTenSanPham.setText(sanPham.getTenSP());
        holder.txtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));

        String donGia = numberFormat.format(sanPham.getGia()).toString();
        holder.txtDonGia.setText(donGia);

        String tongTien = numberFormat.format(sanPham.getGia() * sanPham.getSoLuong()).toString();
        holder.txtTongTien.setText(tongTien + " VNĐ");

        holder.txtTrangThai.setText(LocalVariablesAndMethods.DANHAN);

        holder.btnXuLy.setBackground(context.getResources().getDrawable(R.drawable.bg_shape_corner_color_green));

        modelNhanVien.MoKetNoiSQL(context);
        int MaNV = modelNhanVien.LayNhanVien().getMaNV();

        ApiService.apiService.KiemTraDanhGia(sanPham.getMaSP(), MaNV, sanPham.getMaHD()).enqueue(new Callback<DanhGia>() {
            @Override
            public void onResponse(Call<DanhGia> call, Response<DanhGia> response) {
                DanhGia danhGia = response.body();
                if (danhGia != null) {
                    holder.btnXuLy.setText("Đã đánh Giá");
                    holder.btnXuLy.setEnabled(false);
                }
                else {
                    holder.btnXuLy.setText("Đánh Giá");
                    holder.btnXuLy.setEnabled(true);
                    holder.btnXuLy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iDanhGia = new Intent(context, DanhGiaSanPhamActivity.class);
                            iDanhGia.putExtra("MaSP", sanPham.getMaSP());
                            iDanhGia.putExtra("MaNV", MaNV);
                            iDanhGia.putExtra("MaHD", sanPham.getMaHD());
                            context.startActivity(iDanhGia);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DanhGia> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
