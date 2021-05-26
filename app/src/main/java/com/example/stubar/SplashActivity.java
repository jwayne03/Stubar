package com.example.stubar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Runnable {

    private Handler handler;
    private int stepCounter;
    private TextView tvCurrentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Program and fire the handler immediately
        handler = new Handler();
        stepCounter = 0;
        handler.postDelayed(this, 0);
    }

    @Override
    public void run() {
        tvCurrentStep = findViewById(R.id.tvStep);

        if (stepCounter == 0) {
            // First step
            tvCurrentStep.setText(R.string.lbl_step_initializing);
            stepCounter++;
            handler.postDelayed(this, 1000);
        } else if (stepCounter == 1) {
            // Second step
            tvCurrentStep.setText(R.string.lbl_step_checkingUpdates);
            stepCounter++;
            handler.postDelayed(this, 1000);
        } else if (stepCounter == 2) {
            // Third step
            tvCurrentStep.setText(R.string.lbl_step_loading_data);
            stepCounter++;
            handler.postDelayed(this, 1000);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
        }
    }
}