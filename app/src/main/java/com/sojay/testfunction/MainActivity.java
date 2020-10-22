package com.sojay.testfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sojay.testfunction.card.CardActivity;
import com.sojay.testfunction.code.CodeActivity;
import com.sojay.testfunction.loading.LoadingActivity;
import com.sojay.testfunction.puzzle.PuzzleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv1).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CodeActivity.class)));

        findViewById(R.id.tv2).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PuzzleActivity.class)));

        findViewById(R.id.tv3).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoadingActivity.class)));

        findViewById(R.id.tv4).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CardActivity.class)));

    }
}