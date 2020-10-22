package com.sojay.testfunction.puzzle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.LoadImgUtil;

import java.util.ArrayList;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity {

    private CPuzzleView puzzleView;
    private RecyclerView mRv;

    private List<PuzzleBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        intiView();

        initData();

    }

    private void initData() {

        LoadImgUtil.ins().loadBitmap(this, "https://res.xfanread.com/1603101152168.png", new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
                puzzleView.setBackground(new BitmapDrawable(resource));
            }
        });

        list = new ArrayList<>();

        mRv.setLayoutManager(new GridLayoutManager(this, 3));

        addList();

        PuzzleAdapter adapter = new PuzzleAdapter(list);
        mRv.setAdapter(adapter);
    }

    /**
     * 就当做是网络请求的数据吧
     */
    public void addList() {

        PuzzleBean bean1 = new PuzzleBean();
        bean1.setPosition(0);
        bean1.setLeft(0);
        bean1.setTop(0);
        bean1.setRight(0);
        bean1.setBottom(1);
        bean1.setUrl("https://res.xfanread.com/1603101157260.png");
        list.add(bean1);

        PuzzleBean bean7 = new PuzzleBean();
        bean7.setPosition(6);
        bean7.setLeft(0);
        bean7.setTop(0);
        bean7.setRight(1);
        bean7.setBottom(0);
        bean7.setUrl("https://res.xfanread.com/1603101172583.png");
        list.add(bean7);

        PuzzleBean bean4 = new PuzzleBean();
        bean4.setPosition(3);
        bean4.setLeft(0);
        bean4.setTop(0);
        bean4.setRight(0);
        bean4.setBottom(1);
        bean4.setUrl("https://res.xfanread.com/1603101165102.png");
        list.add(bean4);

        PuzzleBean bean8 = new PuzzleBean();
        bean8.setPosition(7);
        bean8.setLeft(0);
        bean8.setTop(1);
        bean8.setRight(0);
        bean8.setBottom(0);
        bean8.setUrl("https://res.xfanread.com/1603101174472.png");
        list.add(bean8);

        PuzzleBean bean2 = new PuzzleBean();
        bean2.setPosition(1);
        bean2.setLeft(1);
        bean2.setTop(0);
        bean2.setRight(1);
        bean2.setBottom(1);
        bean2.setUrl("https://res.xfanread.com/1603101159871.png");
        list.add(bean2);

        PuzzleBean bean6 = new PuzzleBean();
        bean6.setPosition(5);
        bean6.setLeft(0);
        bean6.setTop(1);
        bean6.setRight(0);
        bean6.setBottom(1);
        bean6.setUrl("https://res.xfanread.com/1603101169816.png");
        list.add(bean6);

        PuzzleBean bean3 = new PuzzleBean();
        bean3.setPosition(2);
        bean3.setLeft(0);
        bean3.setTop(0);
        bean3.setRight(0);
        bean3.setBottom(0);
        bean3.setUrl("https://res.xfanread.com/1603101162620.png");
        list.add(bean3);

        PuzzleBean bean5 = new PuzzleBean();
        bean5.setPosition(4);
        bean5.setLeft(1);
        bean5.setTop(0);
        bean5.setRight(1);
        bean5.setBottom(1);
        bean5.setUrl("https://res.xfanread.com/1603101167637.png");
        list.add(bean5);

        PuzzleBean bean9 = new PuzzleBean();
        bean9.setPosition(8);
        bean9.setLeft(1);
        bean9.setTop(0);
        bean9.setRight(0);
        bean9.setBottom(0);
        bean9.setUrl("https://res.xfanread.com/1603101176287.png");
        list.add(bean9);
    }

    private void intiView() {
        puzzleView = findViewById(R.id.puzzle_view);
        mRv = findViewById(R.id.rv);
    }
}