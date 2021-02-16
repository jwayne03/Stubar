package com.example.stubar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity implements Runnable {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Program and fire the handler immediately
        handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }
}