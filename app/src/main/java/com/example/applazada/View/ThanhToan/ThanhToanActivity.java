package com.example.applazada.View.ThanhToan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.applazada.API.ApiService;
import com.example.applazada.LocalVariablesAndMethods;
import com.example.applazada.Model.ChiTietHoaDon;
import com.example.applazada.Model.DangNhapSQLite.ModelNhanVien;
import com.example.applazada.Model.GioHangSQLite.ModelGioHang;
import com.example.applazada.Model.HoaDon;
import com.example.applazada.Model.HoaDonResponse;
import com.example.applazada.Model.MessageResponse;
import com.example.applazada.Model.NhanVien;
import com.example.applazada.Model.SanPham;
import com.example.applazada.Paypal.Config;
import com.example.applazada.R;
import com.example.applazada.View.TrangChu.TrangChuActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK) // ENVIRONMENT_SANDBOX, ENVIRONMENT_NO_NETWORK
            .clientId(Config.PAYPAL_CLIENT_ID);
    private String amount = "";
    private boolean isPay = false;
    private String maChuyenKhoan;
    protected Toolbar toolbar;
    protected EditText edTenNguoiNhan, edSoDienThoaiNhan, edDiaChiNhan;
    protected Button btnThanhToan;
    protected CheckBox checkboxCamKet;
    protected TextView txtThanhToanKhiNhanHang, txtThanhToanTrucTuyen;
    protected ImageButton imThanhToanKhiNhanHang, imThanhToanTrucTuyen;
    private int HinhThucThanhToan = 0; // 0 = Thanh toan khi nhan hang, 1 = Thanh toan truc tuyen
    ModelNhanVien modelNhanVien = new ModelNhanVien();
    ModelGioHang modelGioHang = new ModelGioHang();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        /**
         * Khởi tạo các control
         */
        InitControl();

        imThanhToanKhiNhanHang.setOnClickListener(this);
        imThanhToanTrucTuyen.setOnClickListener(this);
        btnThanhToan.setOnClickListener(this);

        /**
         * Chuyển sang màn hình thanh toán bằng Paypal
         */
        Intent intent = new Intent(ThanhToanActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        /**
         * Lấy hết thông tin sản phẩm đang lưu trong giỏ hàng và tính tổng tiền, sau đó đổi sang USD
         */
        modelGioHang.MoKetNoiSQL(ThanhToanActivity.this);
        List<SanPham> sanPhamList = modelGioHang.LayDanhSachSanPhamTrongGioHang();
        long giaTien = 0;
        for (SanPham sanPham : sanPhamList) {
            giaTien += sanPham.getGia() * sanPham.getSoLuong();
        }
        amount = String.valueOf(giaTien * LocalVariablesAndMethods.CURRENCY);

        modelNhanVien.MoKetNoiSQL(this);
        modelGioHang.MoKetNoiSQL(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /**
             * Nút thanh toán khi nhận hàng
             */
            case R.id.imThanhToanKhiNhanHang: {
                HinhThucThanhToan = 0;
                txtThanhToanKhiNhanHang.setBackgroundResource(R.drawable.bg_shape_corner_color_blue);
                txtThanhToanKhiNhanHang.setTextColor(Color.parseColor("#FFFFFF"));

                txtThanhToanTrucTuyen.setBackgroundResource(R.color.white);
                txtThanhToanTrucTuyen.setTextColor(Color.parseColor("#000000"));
                break;
            }
            /**
             * Nút thanh toán trực tuyến
             */
            case R.id.imThanhToanTrucTuyen: {
                HinhThucThanhToan = 1;
                txtThanhToanTrucTuyen.setBackgroundResource(R.drawable.bg_shape_corner_color_blue);
                txtThanhToanTrucTuyen.setTextColor(Color.parseColor("#FFFFFF"));

                txtThanhToanKhiNhanHang.setBackgroundResource(R.color.white);
                txtThanhToanKhiNhanHang.setTextColor(Color.parseColor("#000000"));

                processPayment();
                break;
            }
            /**
             * Nút tạo đơn hàng
             */
            case R.id.btnThanhToan: {
                HoaDon hoaDon = new HoaDon();
                NhanVien nhanVien = modelNhanVien.LayNhanVien();

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = cal.getTime();
                hoaDon.setNgayMua(sdf.format(date));

                cal.add(Calendar.DAY_OF_MONTH, 3);
                Date date3 = cal.getTime();
                hoaDon.setNgayGiao(sdf.format(date3));

                String diaChi = edDiaChiNhan.getText().toString().trim();
                String tenNguoiNhan = edTenNguoiNhan.getText().toString().trim();
                String SDT = edSoDienThoaiNhan.getText().toString().trim();

                if (diaChi.length() > 0 && tenNguoiNhan.length() > 0 && SDT.length() > 0) {
                    if (checkboxCamKet.isChecked()) {
                        hoaDon.setMaNV(nhanVien.getMaNV());
                        hoaDon.setTrangThai("Đang Xử Lý");
                        hoaDon.setDiaChi(diaChi);
                        hoaDon.setTenNguoiNhan(tenNguoiNhan);
                        hoaDon.setSoDT(SDT);
                        hoaDon.setChuyenKhoan(HinhThucThanhToan);
                        if (HinhThucThanhToan == 1) {
                            if (!isPay) {
                                Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng thẻ chưa thành công", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                hoaDon.setMaChuyenKhoan(maChuyenKhoan);
                            }
                        }
                        else {
                            hoaDon.setMaChuyenKhoan("NOTPAY");
                        }

                        /**
                         * Lưu hoá đơn vào DB
                         */
                        ApiService.apiService.TaoHoaDon(hoaDon).enqueue(new Callback<HoaDonResponse>() {
                            @Override
                            public void onResponse(Call<HoaDonResponse> call, Response<HoaDonResponse> response) {
                                HoaDonResponse res = response.body();
                                String MaHD = res.getMaHD();
                                if (Integer.parseInt(MaHD) != -1) {
                                    List<SanPham> sanPhamList = modelGioHang.LayDanhSachSanPhamTrongGioHang();
                                    for (SanPham item : sanPhamList) {
                                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                                        chiTietHoaDon.setMaHD(Integer.parseInt(MaHD));
                                        chiTietHoaDon.setMaSP(item.getMaSP());
                                        chiTietHoaDon.setSoLuong(item.getSoLuong());

                                        /**
                                         * Ứng với mỗi sản phẩm trong giỏ hàng, tạo chi tiết đơn hàng với MaDH được gửi lại từ DB
                                         */
                                        ApiService.apiService.TaoChiTietHoaDon(chiTietHoaDon).enqueue(new Callback<MessageResponse>() {
                                            @Override
                                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                                MessageResponse message = response.body();
                                                if (message.getMessage().equals("Fail")) {
                                                    Toast.makeText(ThanhToanActivity.this, "Có lỗi xảy ra trong quá trình tạo chi tiết đơn hàng", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                                Toast.makeText(ThanhToanActivity.this, "Có lỗi xảy ra khi tạo chi tiết đơn hàng", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    for (SanPham item : sanPhamList) {
                                        modelGioHang.XoaSanPhamTrongGioHang(item.getMaSP());
                                    }
                                    Toast.makeText(ThanhToanActivity.this, "Tạo đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ThanhToanActivity.this, TrangChuActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(ThanhToanActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(ThanhToanActivity.this, String.valueOf(MaHD), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<HoaDonResponse> call, Throwable t) {
                                //Toast.makeText(ThanhToanActivity.this, "Có lỗi xảy ra trong quá trình tạo đơn hàng", Toast.LENGTH_SHORT).show();
                                Toast.makeText(ThanhToanActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(ThanhToanActivity.this, "Bạn chưa đồng ý thoả thuận với chúng tôi", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ThanhToanActivity.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(ThanhToanActivity.this, PayPalService.class));
        super.onDestroy();
    }

    /**
     * Thanh toán Paypal
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation paymentConfirmActivity = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (paymentConfirmActivity != null) {
                    try {
                        String paymentDetails = paymentConfirmActivity.toJSONObject().toString(4);
                        /**
                         * Dữ liệu JSONObject sau khi thanh toán thành công
                         */
                        JSONObject jsonObject = new JSONObject(paymentDetails);
                        Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        /**
                         * Lưu lại mã chuyển khoản
                         */
                        maChuyenKhoan = jsonObject.getJSONObject("response").getString("id");
                        isPay = true;
                        imThanhToanKhiNhanHang.setEnabled(false);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                isPay = false;
                Toast.makeText(ThanhToanActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            isPay = false;
            Toast.makeText(ThanhToanActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Chuyển sang Activity Thanh toán Paypal
     */
    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Thanh toan cho don hang", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(ThanhToanActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    /**
     * Khởi tạo control
     */
    private void InitControl() {
        toolbar = findViewById(R.id.toolBar);
        edTenNguoiNhan = findViewById(R.id.edTenNguoiNhan);
        edSoDienThoaiNhan = findViewById(R.id.edSoDienThoaiNhan);
        edDiaChiNhan = findViewById(R.id.edDiaChiNhan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        checkboxCamKet = findViewById(R.id.checkboxCamKet);
        imThanhToanKhiNhanHang = findViewById(R.id.imThanhToanKhiNhanHang);
        imThanhToanTrucTuyen = findViewById(R.id.imThanhToanTrucTuyen);
        txtThanhToanKhiNhanHang = findViewById(R.id.txtThanhToanKhiNhanHang);
        txtThanhToanTrucTuyen = findViewById(R.id.txtThanhToanTrucTuyen);

        toolbar.setTitle("THANH TOÁN");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}