package com.sojay.testfunction.fanye.sojay;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sojay.testfunction.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 可翻页的view
 */
public class TurnPageView extends FrameLayout {

    private FrameLayout mLeftTotalLayout;      // 左侧总容器
    private FrameLayout mLeftSubLayout;        // 左侧子容器
    private ImageView mIvRight;                // 右侧的图片
    private Rect mRectLeft = new Rect();       // 左侧的矩阵
    private Rect mRectTop = new Rect();       // 左侧的矩阵
    private Rect mRectRight = new Rect();      // 右侧的矩阵
    private Rect mRectBottom = new Rect();      // 右侧的矩阵
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();

    private Scroller mScroller;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mWidth;
    private int mHeight;
    private boolean isFlipping = false;        // 是否正在翻转中
    private Bitmap mRightBitmap;               // 右侧图片
    // 11:左边移出  12:右边移除  13:上边移出  14:下边移出
    // 21:居中向左翻页  22:居中向右翻页  23:居中向上翻页  24:居中向下翻页
    // 3:右移翻页
    private int mLeftType;
    private int mRightType;

    public TurnPageView(@NonNull Context context) {
        this(context, null);
    }

    public TurnPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TurnPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());// 减速 // 动画插入器

        mIvRight = new ImageView(context);
        FrameLayout.LayoutParams rightParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        rightParams.gravity = Gravity.CENTER;
        mIvRight.setLayoutParams(rightParams);
        addView(mIvRight);

        mLeftTotalLayout = new FrameLayout(context);
        FrameLayout.LayoutParams leftTotalParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        leftTotalParams.gravity = Gravity.CENTER;
        mLeftTotalLayout.setLayoutParams(leftTotalParams);

        mLeftSubLayout = new FrameLayout(context);
        FrameLayout.LayoutParams leftSubParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLeftSubLayout.setLayoutParams(leftSubParams);
        leftSubParams.gravity = Gravity.CENTER;
        mLeftTotalLayout.addView(mLeftSubLayout);
        addView(mLeftTotalLayout);

//        mIvLeft = new ImageView(context);
//        FrameLayout.LayoutParams leftParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        leftParams.gravity = Gravity.CENTER;
//        mIvLeft.setLayoutParams(leftParams);
//        mIvLeft.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        addView(mIvLeft);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // 中间向左翻页
                mLeftTotalLayout.setTranslationX(0f);
                mLeftTotalLayout.setTranslationY(0f);
                mLeftSubLayout.setTranslationX(0f);
                mLeftSubLayout.setTranslationY(0f);
                mLeftSubLayout.removeAllViews();
                ImageView iv = new ImageView(getContext());
                FrameLayout.LayoutParams leftParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                leftParams.gravity = Gravity.CENTER;
                iv.setLayoutParams(leftParams);
                iv.setImageBitmap(mRightBitmap);
                mLeftSubLayout.addView(iv);

                mLeftType = mRightType;
                mLeftTotalLayout.setBackgroundColor(getContext().getResources().getColor(R.color.tr));
                mRightType = 0;
                mRightBitmap = null;
                mIvRight.setImageBitmap(null);

                isFlipping = false;


            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int count = getChildCount();
        //将两个textView放置进去
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, mWidth, mHeight);
        }

        mRectLeft.left = 0;
        mRectLeft.top = 0;
        mRectLeft.right = getWidth() / 2;
        mRectLeft.bottom = getHeight();

        mRectTop.left = 0;
        mRectTop.top = 0;
        mRectTop.right = getWidth();
        mRectTop.bottom = getHeight() / 2;

        mRectRight.left = getWidth() / 2;
        mRectRight.top = 0;
        mRectRight.right = getWidth();
        mRectRight.bottom = getHeight();

        mRectBottom.left = 0;
        mRectBottom.top = getHeight() / 2;
        mRectBottom.right = getWidth();
        mRectBottom.bottom = getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            if (mLeftType == 22) {
                drawL2R_LeftHalf(canvas);
                drawL2R_RightHalf(canvas);
                drawL2R_FlipHalf(canvas);
            } else if (mLeftType == 23) {
                drawB2T_LeftHalf(canvas);
                drawB2T_RightHalf(canvas);
                drawB2T_FlipHalf(canvas);
            } else if (mLeftType == 24) {
                drawT2B_LeftHalf(canvas);
                drawT2B_RightHalf(canvas);
                drawT2B_FlipHalf(canvas);
            } else {
                drawR2L_LeftHalf(canvas);
                drawR2L_RightHalf(canvas);
                drawR2L_FlipHalf(canvas);
            }
            postInvalidate();
        }
    }

    /**
     * 由右向左翻页-画左半部分
     */
    private void drawR2L_LeftHalf(Canvas canvas) {
            canvas.save();
            canvas.clipRect(mRectLeft);
            View drawView = mLeftTotalLayout;
            drawChild(canvas, drawView, 0);
            canvas.restore();
    }

    /**
     * 由右向左翻页-画右半部分
     */
    private void drawR2L_RightHalf(Canvas canvas) {
            canvas.save();
            canvas.clipRect(mRectRight);
            View drawView = mIvRight;
            drawChild(canvas, drawView, 0);
            canvas.restore();
    }

    /**
     * 由右向左翻页-画翻页部分
     */
    private void drawR2L_FlipHalf(Canvas canvas) {
        canvas.save();
        mCamera.save();

        View view = null;
        float deg = getDegX();
        if (deg < 90) {
            canvas.clipRect(mRectRight);
            mCamera.rotateY(-deg);
            view = mLeftTotalLayout;
        } else if (deg >= 90){
            canvas.clipRect(mRectLeft);
            mCamera.rotateY(-deg - 180);
            view = mIvRight;
        }

        mCamera.getMatrix(mMatrix);
        positionMatrix();
        canvas.concat(mMatrix);

        if (view != null) {
            drawChild(canvas, view, 0);
        }

        mCamera.restore();
        canvas.restore();
    }



     /**
     * 由左向右翻页-画左半部分
     */
    private void drawL2R_LeftHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectLeft);
        View drawView = mIvRight;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由左向右翻页-画右半部分
     */
    private void drawL2R_RightHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectRight);
        View drawView = mLeftTotalLayout;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由左向右翻页-画翻页部分
     */
    private void drawL2R_FlipHalf(Canvas canvas) {
        canvas.save();
        mCamera.save();

        View view = null;
        float deg = getDegX();
        if (deg < 90) {
            mCamera.rotateY(deg);
            canvas.clipRect(mRectLeft);
            view = mLeftTotalLayout;
        } else if (deg >= 90) {
            mCamera.rotateY(deg + 180);
            canvas.clipRect(mRectRight);
            view = mIvRight;
        }

        mCamera.getMatrix(mMatrix);
        positionMatrix();
        canvas.concat(mMatrix);

        if (view != null) {
            drawChild(canvas, view, 0);
        }

        mCamera.restore();
        canvas.restore();
    }



     /**
     * 由上向下翻页-画左半部分
     */
    private void drawT2B_LeftHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectTop);
        View drawView = mIvRight;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由上向下翻页-画右半部分
     */
    private void drawT2B_RightHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectBottom);
        View drawView = mLeftTotalLayout;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由上向下翻页-画翻页部分
     */
    private void drawT2B_FlipHalf(Canvas canvas) {
        canvas.save();
        mCamera.save();

        View view = null;
        float deg = getDegY();
        if (deg < 90) {
            mCamera.rotateX(-deg);
            canvas.clipRect(mRectTop);
            view = mLeftTotalLayout;
        } else if (deg >= 90) {
            mCamera.rotateX(-deg - 180);
            canvas.clipRect(mRectBottom);
            view = mIvRight;
        }

        mCamera.getMatrix(mMatrix);
        positionMatrix();
        canvas.concat(mMatrix);

        if (view != null) {
            drawChild(canvas, view, 0);
        }

        mCamera.restore();
        canvas.restore();
    }



     /**
     * 由下向上翻页-画左半部分
     */
    private void drawB2T_LeftHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectTop);
        View drawView = mLeftTotalLayout;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由下向上翻页-画右半部分
     */
    private void drawB2T_RightHalf(Canvas canvas) {
        canvas.save();
        canvas.clipRect(mRectBottom);
        View drawView = mIvRight;
        drawChild(canvas, drawView, 0);
        canvas.restore();
    }

    /**
     * 由下向上翻页-画翻页部分
     */
    private void drawB2T_FlipHalf(Canvas canvas) {
        canvas.save();
        mCamera.save();

        View view = null;
        float deg = getDegY();
        if (deg < 90) {
            mCamera.rotateX(deg);
            canvas.clipRect(mRectBottom);
            view = mLeftTotalLayout;
        } else if (deg >= 90) {
            mCamera.rotateX(deg - 180);
            canvas.clipRect(mRectTop);
            view = mIvRight;
        }

        mCamera.getMatrix(mMatrix);
        positionMatrix();
        canvas.concat(mMatrix);

        if (view != null) {
            drawChild(canvas, view, 0);
        }

        mCamera.restore();
        canvas.restore();
    }



    private float getDegX() {
        return mScroller.getCurrX() * 1.0f / mWidth * 180;
    }

    private float getDegY() {
        return mScroller.getCurrY() * 1.0f / mHeight * 180;
    }

    private void positionMatrix() {
        mMatrix.preScale(0.25f, 0.25f);
        mMatrix.postScale(4.0f, 4.0f);
        mMatrix.preTranslate(-getWidth() / 2f, -getHeight() / 2f);
        mMatrix.postTranslate(getWidth() / 2f, getHeight() / 2f);
    }

    /**
     * 直接替换,不需要任何动画
     */
    private void startReplacement() {
        mHandler.postDelayed(mRunnable, 0);
    }

    /**
     * 平移出场
     */
    private void startTranslation() {
        if (mLeftType == 12) {  // 右侧移出
            mLeftTotalLayout.animate().translationX(mLeftTotalLayout.getWidth()).setDuration(1000L);
            mIvRight.setTranslationX(-mIvRight.getWidth());
            mIvRight.animate().translationX(0).setDuration(1000L);
        } else if (mLeftType == 13) {  // 上侧移出
            mLeftTotalLayout.animate().translationY(-mLeftTotalLayout.getHeight()).setDuration(1000L);
            mIvRight.setTranslationY(mIvRight.getHeight());
            mIvRight.animate().translationY(0).setDuration(1000L);
        }  else if (mLeftType == 14) {  // 下侧移出
            mLeftTotalLayout.animate().translationY(mLeftTotalLayout.getHeight()).setDuration(1000L);
            mIvRight.setTranslationY(-mIvRight.getHeight());
            mIvRight.animate().translationY(0).setDuration(1000L);
        } else {  // 左侧移出
            mLeftTotalLayout.animate().translationX(-mLeftTotalLayout.getWidth()).setDuration(1000L);
            mIvRight.setTranslationX(mIvRight.getWidth());
            mIvRight.animate().translationX(0).setDuration(1000L);
        }
        mHandler.postDelayed(mRunnable, 950);
    }

    /**
     * 右移翻转
     */
    private void startTranslationFlip() {
        mLeftTotalLayout.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        mLeftSubLayout.setTranslationX(0);
        mLeftSubLayout.animate().translationX(480).setDuration(1000L).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startFlip();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 翻转
     */
    private void startFlip() {
        if (mLeftType == 23 || mLeftType == 24)
            mScroller.startScroll(0, 0, 0, mHeight, 1000);
        else
            mScroller.startScroll(0, 0, mWidth, 0, 1000);
        mHandler.postDelayed(mRunnable, 950);
        postInvalidate();
    }

    /**
     * 加载本地图片
     * @param url
     * @return
     */
    private Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 设置当前图片的离场动画类型
     */
    public void setCurrentExitAnimationType(int type) {
        mLeftType = type;
    }

    /**
     * 设置左侧的图片
     */
    public void setLeftDrawable(String localPath) {
        if (TextUtils.isEmpty(localPath))
            return;

        ImageView iv = new ImageView(getContext());
        FrameLayout.LayoutParams leftParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        leftParams.gravity = Gravity.CENTER;
        iv.setLayoutParams(leftParams);
        iv.setImageBitmap(getLocalBitmap(localPath));
        mLeftSubLayout.addView(iv);

        postInvalidate();
    }

    /**
     * 设置下一张图片的离场动画类型
     */
    public void setNextExitAnimationType(int type) {
        mRightType = type;
    }

    /**
     * 设置右侧的图片
     */
    public void setRightDrawable(String localPath) {
        if (TextUtils.isEmpty(localPath))
            return;

        mRightBitmap = getLocalBitmap(localPath);
        mIvRight.setImageBitmap(mRightBitmap);
        postInvalidate();
    }

    /**
     * 执行离场动画
     */
    public void performExitAnimation() {
        if (isFlipping || mLeftSubLayout.getChildCount() == 0 || mRightBitmap == null)
            return;

        isFlipping = true;

        if (mLeftType == 11 || mLeftType == 12 || mLeftType == 13 || mLeftType == 14) {
            // 左边移出
            startTranslation();
        } else if (mLeftType == 21 || mLeftType == 22 || mLeftType == 23 || mLeftType == 24) {
            // 居中翻页
            startFlip();
        } else if (mLeftType == 3) {
            // 右移翻页
            startTranslationFlip();
        } else {
            startReplacement();
        }
    }


}
