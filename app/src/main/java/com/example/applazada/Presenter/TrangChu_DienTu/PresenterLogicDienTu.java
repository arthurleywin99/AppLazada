package com.example.applazada.Presenter.TrangChu_DienTu;

import android.util.Log;
import android.widget.Toast;

import com.example.applazada.API.ApiService;
import com.example.applazada.Model.DienTu;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.ThuongHieu;
import com.example.applazada.View.TrangChu.ViewDienTu;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterLogicDienTu implements IPresenterDienTu {
    private ViewDienTu viewDienTu;
    private DienTu dienTu;

    public PresenterLogicDienTu(ViewDienTu viewDienTu) {
        this.viewDienTu = viewDienTu;
        this.dienTu = new DienTu();
    }

    @Override
    public void LayDanhSachDienTu() {
        ApiService.apiService.LayThuongHieu().enqueue(new Callback<List<ThuongHieu>>() {
            @Override
            public void onResponse(Call<List<ThuongHieu>> call, Response<List<ThuongHieu>> response) {
                List<ThuongHieu> thuongHieuList = response.body();

                ApiService.apiService.LayTopDienThoaiVaMayTinhBang().enqueue(new Callback<List<SanPham>>() {
                    @Override
                    public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                        List<SanPham> sanPhamList = response.body();

                        viewDienTu.HienThiDSThuongHieuVaTopDTMayTinhBang(thuongHieuList, sanPhamList);
                        dienTu.setThuongHieuList(thuongHieuList);
                        dienTu.setSanPhamList(sanPhamList);
                    }

                    @Override
                    public void onFailure(Call<List<SanPham>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ThuongHieu>> call, Throwable t) {

            }
        });
    }
}
