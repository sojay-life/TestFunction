package com.sojay.testfunction.fanye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.sojay.testfunction.R;
import com.sojay.testfunction.fanye.sojay.TurnPageView;

public class FanYeActivity extends AppCompatActivity implements MyClockView.DownCountTimerListener {

    private Button mBtn;
    private MyClockView myClockView;
    private TurnPageView mTurnPageView;
    private String BASE_PATH = Environment.getExternalStorageDirectory() + "/course/";
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan_ye);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏


        mBtn = findViewById(R.id.button1);
        myClockView = findViewById(R.id.clockView);
        mTurnPageView = findViewById(R.id.turn_page_view);

        myClockView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        myClockView.setDownCountTimerListener(this);

        int type = 11;

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myClockView.setDownCountTime(1000L * 60L + 1000L * 12L);
//                myClockView.startDownCountTimer();


                mTurnPageView.setNextExitAnimationType(type);
                if (i % 2 == 1) {
                    mTurnPageView.setRightDrawable(Environment.getExternalStorageDirectory() + "/course/test/cc.jpg");
                } else {
                    mTurnPageView.setRightDrawable(Environment.getExternalStorageDirectory() + "/course/test/bb.jpg");
                }

//                if (i % 3 == 1) {
//                    mTurnPageView.setNextExitAnimationType(2);
//                    mTurnPageView.setRightDrawable(Environment.getExternalStorageDirectory() + "/course/test/aa.jpg");
//                } else if (i % 3 == 2) {
//                    mTurnPageView.setNextExitAnimationType(3);
//                    mTurnPageView.setRightDrawable(Environment.getExternalStorageDirectory() + "/course/test/bb.jpg");
//                } else {
//                    mTurnPageView.setNextExitAnimationType(1);
//                    mTurnPageView.setRightDrawable(Environment.getExternalStorageDirectory() + "/course/test/cc.jpg");
//                }

                i++;
                mTurnPageView.performExitAnimation();
            }
        });

        mTurnPageView.setCurrentExitAnimationType(type);
        mTurnPageView.setLeftDrawable(Environment.getExternalStorageDirectory() + "/course/test/bb.jpg");


    }

    @Override
    public void stopDownCountTimer() {
        Toast.makeText(this,"结束了",Toast.LENGTH_SHORT).show();
    }

}
