package com.sojay.testfunction.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.sojay.testfunction.R;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class PathMeasureView extends FrameLayout {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    private GifImageView mGif;

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化Paint
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.BLACK);
        //初始化Path
        mPath = new Path();
        //初始化PathMeasure
        pathMeasure = new PathMeasure();

        mGif = new GifImageView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mGif.setLayoutParams(params);
        try {
            GifDrawable gifFromResource =  new GifDrawable(getResources(), R.drawable.aa);
            mGif.setImageDrawable(gifFromResource);
            addView(mGif);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPath(Path path) {
        mPath = path;
        pathMeasure.setPath(mPath, false);
    }

    private boolean aa = false;

    public void startMove() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());

        animator.setDuration(5 * 1000);
        animator.setInterpolator(new LinearInterpolator());

        int w = mGif.getWidth();
        int h = mGif.getHeight();

        animator.addUpdateListener(animation -> {
            float distance = (float) animation.getAnimatedValue();
            //tan[0]是邻边 tan[1]是对边
            pathMeasure.getPosTan(distance, pos, tan);
            mGif.layout((int) (pos[0] - w / 2), (int) (pos[1] - h / 2), (int) (pos[0] + w / 2), (int) (pos[1] + h / 2));
            if (!aa) {
                mGif.setScaleX(1f);
                mGif.setScaleY(1f);
                mGif.animate().scaleX(1.5f).scaleY(1.5f).setStartDelay(3000L).setDuration(1000L);
                aa = true;
            }

        });

        animator.start();
    }

}
