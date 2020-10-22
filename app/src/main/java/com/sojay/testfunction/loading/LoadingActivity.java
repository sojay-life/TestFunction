package com.sojay.testfunction.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sojay.testfunction.R;

public class LoadingActivity extends AppCompatActivity {

    private MusicLoadingView musicLoadingView;
    private TextView tvStart;
    private TextView tvStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initView();

        tvStart.setOnClickListener(v -> musicLoadingView.startLoading());

        tvStop.setOnClickListener(v -> musicLoadingView.pauseLoading());

    }

    private void initView() {
        musicLoadingView = findViewById(R.id.music_loading);
        tvStart = findViewById(R.id.start_loading);
        tvStop = findViewById(R.id.stop_loading);
    }
}