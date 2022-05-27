package com.example.applazada.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.SanPham;
import com.example.applazada.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterGioHang extends RecyclerView.Adapter<AdapterGioHang.ViewHolderGioHang> {
    Context context;
    List<SanPham> sanPhamList;
    ModelGioHang modelGioHang = new ModelGioHang();

    public AdapterGioHang(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        modelGioHang.MoKetNoiSQL(context);
    }

    public class ViewHolderGioHang extends RecyclerView.ViewHolder {
        TextView txtGiaTienGioHang, txtTenSanPham, txtSoLuong;
        ImageView imTru, imCong, imXoa, imHinhGioHang;

        public ViewHolderGioHang(@NonNull View itemView) {
            super(itemView);
            imHinhGioHang = itemView.findViewById(R.id.imHinhGioHang);
            txtGiaTienGioHang = itemView.findViewById(R.id.txtGiaTien);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imTru = itemView.findViewById(R.id.imTru);
            imCong = itemView.findViewById(R.id.imCong);
            imXoa = itemView.findViewById(R.id.imXoa);
        }
    }

    @NonNull
    @Override
    public ViewHolderGioHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_sanpham, parent, false);
        ViewHolderGioHang viewHolder = new ViewHolderGioHang(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGioHang holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = sanPhamList.get(position);

        byte[] hinhSanPham = sanPham.getHinhGioHang();
        Bitmap bitmapHinhGioHang = BitmapFactory.decodeByteArray(hinhSanPham, 0, hinhSanPham.length);
        holder.imHinhGioHang.setImageBitmap(bitmapHinhGioHang);
        holder.txtTenSanPham.setText(sanPham.getTenSP());
        holder.txtSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
        SetGiaTien(holder, sanPham);

        holder.imCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.txtSoLuong.getText().toString());
                int soLuongTonKho = sanPham.getSoLuongTonKho();

                if (soLuong < soLuongTonKho) {
                    holder.txtSoLuong.setText(String.valueOf(soLuong + 1));
                    SetGiaTien(holder, sanPham);
                    modelGioHang.CapNhatSanPhamTrongGioHang(sanPham.getMaSP(), soLuong + 1);
                }
                else {
                    Toast.makeText(context, "Kho đã hết hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(holder.txtSoLuong.getText().toString());
                if (soLuong > 1) {
                    holder.txtSoLuong.setText(String.valueOf(soLuong - 1));
                    SetGiaTien(holder, sanPham);
                    modelGioHang.CapNhatSanPhamTrongGioHang(sanPham.getMaSP(), soLuong);
                }
                else if (soLuong == 1) {
                    Toast.makeText(context, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imXoa.setTag(sanPham.getMaSP());
        holder.imXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelGioHang modelGioHang = new ModelGioHang();
                modelGioHang.MoKetNoiSQL(context);
                boolean check = modelGioHang.XoaSanPhamTrongGioHang((int) view.getTag());
                if (check) {
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                }
                sanPhamList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, sanPhamList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    private void SetGiaTien(ViewHolderGioHang holder, SanPham sanPham) {
        NumberFormat numberFormat = new DecimalFormat("###,###");
        int soLuong = Integer.parseInt(holder.txtSoLuong.getText().toString());
        String gia = numberFormat.format(sanPham.getGia() * soLuong).toString();
        holder.txtGiaTienGioHang.setText(gia + " VNĐ");
    }
}
