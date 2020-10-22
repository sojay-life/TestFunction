package com.sojay.testfunction.loading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sojay.testfunction.R;

public class MusicLoadingView extends View {

    // loading的延伸方向
    private final int CENTER_TO_BOTH_SIDES = 0;   // 中间向两侧
    private final int BOTH_SIDES_TO_CENTER = 1;   // 两侧向中间
    private final int LEFT_TO_RIGHT = 2;          // 左侧向右侧
    private final int RIGHT_TO_LEFT = 3;          // 右侧向左侧

    private int mWidth;         // view的宽度
    private int mHeight;        // view的高度
    private float mDrawWidth;   // 已绘制的loading的宽度
    private boolean isStart;    // loading是否已开启
    private Paint mPaint;       // 画笔
    private Handler mHandler;
    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
            mHandler.postDelayed(this, mUpdateTime);
        }
    };

    /****************************** 可设置的参数 *************************************/
    private float mLoadMinWidth = 0f;                                       // loading初始显示的最小宽度，可为0
    private float mUpdateWith = 50f;                                        // loading每次增加的宽度，不能为0，默认50
    private int mUpdateTime = 10;                                           // 循环更新时间间隔
    private int mDirection = CENTER_TO_BOTH_SIDES;                          // loading的延伸方向
    private int mLoadingBgColor = Color.parseColor("#FFD25D");   // loading背景色

    public MusicLoadingView(Context context) {
        this(context, null);
    }

    public MusicLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicLoadingView);
        mLoadMinWidth = typedArray.getDimension(R.styleable.MusicLoadingView_loading_min_width, mLoadMinWidth);
        mUpdateWith = typedArray.getDimension(R.styleable.MusicLoadingView_loading_update_width, mUpdateWith);
        mUpdateTime = typedArray.getInt(R.styleable.MusicLoadingView_loading_update_time, mUpdateTime);
        mLoadingBgColor = typedArray.getColor(R.styleable.MusicLoadingView_loading_bg_color, mLoadingBgColor);
        mDirection = typedArray.getInt(R.styleable.MusicLoadingView_loading_direction, mDirection);
    }

    private void initView() {
        mPaint = new Paint();
        //设置画笔模式为填充带边框
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(mLoadingBgColor);

        mHandler = new Handler();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (mWidth < mLoadMinWidth) {
            // 如果设置的最小绘制宽度大于view宽度，则把最小宽度设置为0
            mLoadMinWidth = 0;
        }
        mPaint.setStrokeWidth(mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 未开始loading，显示不划线，显示空白的
        if (!isStart) {
            canvas.drawLine(0f, 0f, 0f, 0f, mPaint);
        } else {

            // 当宽度大于等于最大宽度 或 已绘制宽度为0（初始状态），设置为设定的最小宽度
            if (mDrawWidth >= mWidth || mDrawWidth == 0)
                mDrawWidth = mLoadMinWidth;
            else  // 绘制宽度需要增加
                mDrawWidth += mUpdateWith;

            // 已经开始loading，如果需要绘制的长度为0，则加上mUpdateWith
            if (mDrawWidth == 0)
                mDrawWidth += mUpdateWith;

            if (mDirection == BOTH_SIDES_TO_CENTER) {
                // 左侧线
                canvas.drawLine(0, 0f, mDrawWidth / 2, 0f, mPaint);
                // 右侧线
                canvas.drawLine(mWidth - (mDrawWidth / 2), 0f, mWidth, 0f, mPaint);
            } else {
                float startX;
                float stopX;

                if (mDirection == LEFT_TO_RIGHT) {
                    startX = 0;
                    stopX = mDrawWidth;
                } else if (mDirection == RIGHT_TO_LEFT) {
                    startX = mWidth - mDrawWidth;
                    stopX = mWidth;
                } else {
                    // 计算线的起始位置和结束为止
                    startX = (mWidth - mDrawWidth) / 2;
                    stopX = startX + mDrawWidth;
                }

                // 画线
                canvas.drawLine(startX, 0f, stopX, 0f, mPaint);
            }
        }
    }


    public void startLoading() {
        if (isStart)
            return;
        mDrawWidth = 0;
        isStart = true;
        mHandler.postDelayed(mLoopRunnable, mUpdateTime);
    }

    public void pauseLoading() {
        if (!isStart)
            return;
        mHandler.removeCallbacks(mLoopRunnable);
        isStart = false;
        invalidate();
    }
}
