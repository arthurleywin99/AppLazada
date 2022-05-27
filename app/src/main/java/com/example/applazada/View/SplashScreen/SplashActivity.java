package com.example.applazada.View.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applazada.R;
import com.example.applazada.View.TrangChu.TrangChuActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_screen);

        /**
         * Chờ delayMillis
         */
        try {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, TrangChuActivity.class));
                finish();
            }, 1500);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
