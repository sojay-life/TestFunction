package com.sojay.testfunction.random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.sojay.testfunction.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomActivity extends AppCompatActivity {

    private List<View.OnClickListener> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        initClickListener();

        setupClickListener();
    }

    /**
     * 设置点击事件的监听
     * 因为list是乱序，所以每次设置的监听都不一样
     */
    private void setupClickListener() {
        findViewById(R.id.back).setOnClickListener(mList.get(0));
        findViewById(R.id.btn1).setOnClickListener(mList.get(1));
        findViewById(R.id.btn2).setOnClickListener(mList.get(2));
        findViewById(R.id.btn3).setOnClickListener(mList.get(3));
        findViewById(R.id.btn4).setOnClickListener(mList.get(4));
        findViewById(R.id.volume_up).setOnClickListener(mList.get(5));
        findViewById(R.id.volume_down).setOnClickListener(mList.get(6));
    }

    /**
     * 初始化点击事件
     * 添加进list，list进行乱序
     */
    private void initClickListener() {
        if (mList == null)
            mList = new ArrayList<>();

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【1】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【2】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【3】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【4】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(RandomActivity.this, "响应按钮【返回按键】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【音量+】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        mList.add(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RandomActivity.this, "响应按钮【音量-】的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        Collections.shuffle(mList);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:
                findViewById(R.id.back).callOnClick();
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                findViewById(R.id.volume_up).callOnClick();
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                findViewById(R.id.volume_down).callOnClick();
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
