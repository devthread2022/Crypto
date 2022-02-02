package com.jvt.devthread.cryptoworld.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.jvt.devthread.cryptoworld.R;
import com.jvt.devthread.cryptoworld.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this,
                    MainActivity.class));
            finish();
        }, 1000);
    }
}