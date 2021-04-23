package com.sojay.testfunction.usb;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
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

    private FrameLayout fl;
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

        fl = findViewById(R.id.fl);
        mIV = findViewById(R.id.iv1);

//        View view = new View(this);
//        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(200, 150);
//        view.setLayoutParams(params1);
//        view.setBackgroundColor(getResources().getColor(R.color.gray));
//        fl.addView(view);

        ImageView iv = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        iv.setImageResource(R.drawable.aa);
        iv.setX(-50);
        fl.addView(iv);

        mIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimatorSet as = new AnimatorSet();



                ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(iv, "translationX", 0, 200);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setDuration(1000L);
//
                ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(iv, "alpha", 0.5f, 1f);
                alphaAnimation.setDuration(1000L);
//
//
//
                ObjectAnimator translateAnimation1 = ObjectAnimator.ofFloat(iv, "translationY", 0, 200);
                translateAnimation1.setDuration(1000L);



                ObjectAnimator scaleAnimation = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 1.5f);
                scaleAnimation.setDuration(1000L);
                ObjectAnimator scaleAnimation1 = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 1.5f);
                scaleAnimation1.setDuration(1000L);

                ObjectAnimator translateAnimation2 = ObjectAnimator.ofFloat(iv, "translationX", 0, 200);
                translateAnimation2.setDuration(1000L);



                ObjectAnimator scaleAnimation2 = ObjectAnimator.ofFloat(iv, "scaleX", 1.5f, 1f);
                scaleAnimation2.setDuration(1000L);
                ObjectAnimator scaleAnimation3 = ObjectAnimator.ofFloat(iv, "scaleY", 1.5f, 1f);
                scaleAnimation3.setDuration(1000L);

                ObjectAnimator translateAnimation3 = ObjectAnimator.ofFloat(iv, "translationY", 0, 200);
                translateAnimation3.setDuration(1000L);




//                as.play(translateAnimation).with(alphaAnimation);
//                as.play(translateAnimation1).after(alphaAnimation);
//                as.play(scaleAnimation).after(translateAnimation1);
//                as.play(scaleAnimation1).after(translateAnimation1);
//                as.play(translateAnimation2).after(translateAnimation1);
//                as.play(scaleAnimation2).after(translateAnimation2);
//                as.play(scaleAnimation3).after(translateAnimation2);
//                as.play(translateAnimation3).after(translateAnimation2);

                as.play(translateAnimation2).with(scaleAnimation).with(scaleAnimation1);
                as.play(translateAnimation3).with(scaleAnimation2).with(scaleAnimation3).after(1000L);
                as.setDuration(2000L);
                as.start();

            }
        });
    }
}
