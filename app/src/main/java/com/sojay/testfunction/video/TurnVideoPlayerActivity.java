package com.sojay.testfunction.video;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;

import com.sojay.testfunction.R;
import com.sojay.testfunction.video.bean.VideoBean;
import com.sojay.testfunction.video.listener.OnAnimationListener;
import com.sojay.testfunction.video.view.TurnPageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TurnVideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turn_video_player);

        TurnPageView turnPageView = findViewById(R.id.turn);

        String dir = Environment.getExternalStorageDirectory() + "/.course/test/";

//        File file =  new File(dir);
//        File[] files = file.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            System.out.println("##############   " + files[i].getAbsolutePath());
//        }

        List<VideoBean> list = new ArrayList<>();
        list.add(new VideoBean("video_2ed3df85-1785d86ced0.png", "2ed3df85-1785d86ced0.mp4", "video_last_2ed3df85-1785d86ced0.png"));
        list.add(new VideoBean("video_3ce47229-1785d8d0094.png", "3ce47229-1785d8d0094.mp4", "video_last_3ce47229-1785d8d0094.png"));
        list.add(new VideoBean("video_4bff4023-1785d823dec.png", "4bff4023-1785d823dec.mp4", "video_last_4bff4023-1785d823dec.png"));
        list.add(new VideoBean("video_4d4cc102-1785d8b2c05.png", "4d4cc102-1785d8b2c05.mp4", "video_last_4d4cc102-1785d8b2c05.png"));

        turnPageView.setNextDrawable(list.get(0), dir, false, true);
        turnPageView.performExitAnimation();
        turnPageView.setOnAnimationListener(new OnAnimationListener() {
            @Override
            public void onAnimationEnd() {
                list.remove(0);
                if (list.isEmpty())
                    return;
                turnPageView.setNextDrawable(list.get(0), dir, false, true);
                turnPageView.performExitAnimation();
            }
        });

    }
}
