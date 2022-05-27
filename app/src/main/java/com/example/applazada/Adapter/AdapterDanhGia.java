package com.example.applazada.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DanhGia;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDanhGia extends RecyclerView.Adapter<AdapterDanhGia.ViewHolderDanhGia> {
    Context context;
    List<DanhGia> danhGiaList;

    public AdapterDanhGia(Context context, List<DanhGia> danhGiaList) {
        this.context = context;
        this.danhGiaList = danhGiaList;
    }

    public class ViewHolderDanhGia extends RecyclerView.ViewHolder {
        ImageView imAnhDaiDien;
        TextView txtTenNguoiDanhGia, txtNoiDungDanhGia;
        RatingBar rbDanhGia;

        public ViewHolderDanhGia(@NonNull View itemView) {
            super(itemView);
            imAnhDaiDien = itemView.findViewById(R.id.imAnhDaiDien);
            txtTenNguoiDanhGia = itemView.findViewById(R.id.txtTenNguoiDanhGia);
            txtNoiDungDanhGia = itemView.findViewById(R.id.txtNoiDungDanhGia);
            rbDanhGia = itemView.findViewById(R.id.rbDanhGia);
        }
    }

    @NonNull
    @Override
    public AdapterDanhGia.ViewHolderDanhGia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_danhgia, parent, false);
        AdapterDanhGia.ViewHolderDanhGia viewHolder = new AdapterDanhGia.ViewHolderDanhGia(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDanhGia holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);

        ApiService.apiService.LayNhanVienBangMaNV(danhGia.getMaNV()).enqueue(new Callback<NhanVien>() {
            @Override
            public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                NhanVien nhanVien = response.body();
                holder.imAnhDaiDien.setImageBitmap(null);
                holder.txtTenNguoiDanhGia.setText(nhanVien.getTenNV().toString());
                holder.txtNoiDungDanhGia.setText(danhGia.getNoiDung());
                holder.rbDanhGia.setRating((float) danhGia.getSoSao());
            }

            @Override
            public void onFailure(Call<NhanVien> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }
}
