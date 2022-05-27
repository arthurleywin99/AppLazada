package com.example.applazada.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.ThuongHieu;
import com.example.applazada.R;
import com.example.applazada.View.HienThiSanPhamTheoDanhMuc.HienThiDanhMucSanPhamActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterThuongHieuLon extends RecyclerView.Adapter<AdapterThuongHieuLon.ViewHolderThuongHieuLon> {
    Context context;
    List<ThuongHieu> thuongHieus;

    public AdapterThuongHieuLon(Context context, List<ThuongHieu> thuongHieus) {
        this.context = context;
        this.thuongHieus = thuongHieus;
    }

    public class ViewHolderThuongHieuLon extends RecyclerView.ViewHolder {
        TextView txtTieuDeThuongHieu;
        ImageView imHinhThuongHieu;
        ProgressBar progressBar;
        LinearLayout linearLayout;

        public ViewHolderThuongHieuLon(View itemView) {
            super(itemView);
            txtTieuDeThuongHieu = itemView.findViewById(R.id.txtTieuDeDienTu);
            imHinhThuongHieu = itemView.findViewById(R.id.imHinhDienTu);
            progressBar = itemView.findViewById(R.id.progress_bar_download);
            linearLayout = itemView.findViewById(R.id.lnLayoutThuongHieuLon);
        }
    }

    @NonNull
    @Override
    public ViewHolderThuongHieuLon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_layout_recycler_thuonghieulon, parent, false);
        ViewHolderThuongHieuLon viewHolder = new ViewHolderThuongHieuLon(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderThuongHieuLon holder, int position) {
        ThuongHieu thuongHieu = thuongHieus.get(position);
        holder.txtTieuDeThuongHieu.setText(thuongHieu.getTenTH());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSanPhamTheoDanhMuc = new Intent(context, HienThiDanhMucSanPhamActivity.class);
                iSanPhamTheoDanhMuc.putExtra("MATH", thuongHieu.getMaTH());
                iSanPhamTheoDanhMuc.putExtra("TENTH", thuongHieu.getTenTH());
                context.startActivity(iSanPhamTheoDanhMuc);
            }
        });

        Picasso.get().load(LocalVariablesAndMethods.domain + "/api/" + thuongHieu.getHinhTH()).into(holder.imHinhThuongHieu, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return thuongHieus.size();
    }
}
