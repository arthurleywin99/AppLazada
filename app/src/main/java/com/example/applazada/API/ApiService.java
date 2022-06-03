package com.example.applazada.API;

import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.ChiTietHoaDon;
import com.example.applazada.Model.DanhGia;
import com.example.applazada.Model.HoaDon;
import com.example.applazada.Model.HoaDonResponse;
import com.example.applazada.Model.LoaiNhanVien;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Model.SanPhamMua;
import com.example.applazada.Model.ThuongHieu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    OkHttpClient client = new OkHttpClient();

    Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(LocalVariablesAndMethods.domain)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    /**
     * NhanVien API
     */
    @FormUrlEncoded
    @POST("api/nhanvien/login.php")
    Call<NhanVien> DangNhap(@Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("api/nhanvien/check.php")
    Call<MessageResponse> KiemTraDuLieu(@Field("key") String key,
                               @Field("value") String value);

    @Headers("Content-Type: application/json")
    @POST("api/nhanvien/register.php")
    Call<MessageResponse> DangKy(@Body NhanVien nhanVien);

    @GET("api/loainhanvien/getbyid.php")
    Call<LoaiNhanVien> LayLoaiNhanVienBangMaNV(@Query("maloainv") int maLoaiNV);

    @GET("api/nhanvien/getbyid.php")
    Call<NhanVien> LayNhanVienBangMaNV(@Query("manv") int maNV);

    @FormUrlEncoded
    @POST("api/nhanvien/updatepassword.php")
    Call<MessageResponse> DoiMatKhau(@Field("manv") int maNV,
                                     @Field("newpassword") String newPassword);

    @Headers("Content-Type: application/json")
    @POST("api/nhanvien/updateinformation.php")
    Call<MessageResponse> DoiThongTin(@Body NhanVien nhanVien);

    /**
     * ThuongHieu API
     */
    @GET("api/thuonghieu/get.php")
    Call<List<ThuongHieu>> LayThuongHieu();

    /**
     * SanPham API
     */
    @GET("api/sanpham/gettop.php")
    Call<List<SanPham>> LayTopDienThoaiVaMayTinhBang();

    @GET("api/sanpham/getbybrand.php")
    Call<List<SanPham>> LayDanhSachSanPhamTheoMaTH(@Query("mathuonghieu") int MaTH,
                                                     @Query("tenthuonghieu") String TenTH);

    @GET("api/sanpham/getbyid.php")
    Call<SanPham> LaySanPhamBangMaSP(@Query("masanpham") int MaSP);

    /**
     * HoaDon API
     */
    @Headers("Content-Type: application/json")
    @POST("api/hoadon/create.php")
    Call<HoaDonResponse> TaoHoaDon(@Body HoaDon hoaDon);

    @GET("api/hoadon/getbyclause.php")
    Call<List<SanPhamMua>> LayDonHangBangTinhTrangDon(@Query("manv") int MaNV,
                                                      @Query("tinhtrang") String tinhtrang);

    @FormUrlEncoded
    @POST("api/hoadon/cancel.php")
    Call<MessageResponse> HuyDonHang(@Field("tinhtrangcu") String TinhTrangCu,
                                     @Field("tinhtrangmoi") String TinhTrangMoi,
                                     @Field("mahd") int MaHD,
                                     @Field("manv") int MaNV,
                                     @Field("masp") int MaSP);

    /**
     * DanhGia API
     */
    @GET("api/hoadon/getdanhgia.php")
    Call<DanhGia> KiemTraDanhGia(@Query("masanpham") int MaSP,
                                         @Query("manhanvien") int MaNV,
                                         @Query("mahoadon") int MaHD);

    @Headers("Content-Type: application/json")
    @POST("api/danhgia/create.php")
    Call<MessageResponse> TaoDanhGia(@Body DanhGia danhGia);

    /**
     * ChiTietHoaDon API
     */
    @POST("api/chitiethoadon/create.php")
    Call<MessageResponse> TaoChiTietHoaDon(@Body ChiTietHoaDon chiTietHoaDon);

    /**
     * TimKiem API
     */
    @GET("api/timkiem/search.php")
    Call<List<SanPham>> TimKiemSanPham(@Query("noidung") String noidung);

    /**
     * DanhGia API
     */
    @GET("api/sanpham/getaveragerating.php")
    Call<List<DanhGia>> LayDanhGiaSanPhamBangMaSP(@Query("masp") int MaSP);
}
