package com.sojay.testfunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sojay.testfunction.code.CodeActivity;
import com.sojay.testfunction.puzzle.PuzzleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv1).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CodeActivity.class)));

        findViewById(R.id.tv2).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PuzzleActivity.class)));

    }
}