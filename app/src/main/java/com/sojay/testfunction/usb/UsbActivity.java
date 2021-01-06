package com.sojay.testfunction.usb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sojay.testfunction.Constant;
import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.USBHelper;
import com.sojay.testfunction.view.PathMeasureView;
import com.ycbjie.webviewlib.utils.ToastUtils;

import java.util.List;

public class UsbActivity extends AppCompatActivity {

    private ImageView mIV;

    private int x = 1148;
    private int y = 845;
    private int mWidth = 1920;
    private int mHeight = 1080;
    private boolean aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏

        mIV = findViewById(R.id.iv1);

        mIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mIV.setPivotX(x);
                mIV.setPivotY(y);

                if (aa)
                mIV.animate().scaleX(1.0f).scaleY(1.0f).setDuration(1000L);
                else
                mIV.animate().scaleX(1.5f).scaleY(1.5f).setDuration(1000L);
                aa = !aa;
            }
        });
    }
}
