package com.sojay.testfunction.video.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.BitmapUtil;
import com.sojay.testfunction.video.bean.VideoBean;
import com.sojay.testfunction.video.listener.CAnimatorListener;
import com.sojay.testfunction.video.listener.OnAnimationListener;

/**
 * 可翻页的view
 * 包含翻页，平移翻页
 */
public class TurnPageView extends FrameLayout {

    private FrameLayout mLeftTotalLayout;      // 左侧总容器
    private FrameLayout mLeftSubLayout;        // 左侧子容器
    private ImageView mIvRight;               // 右侧的图片
    private VideoPlayerView mVideoPlayer;      // 视频播放
    private SurfaceView mSurfaceView;
    private Rect mRectLeft = new Rect();       // 左侧的矩阵
    private Rect mRectRight = new Rect();      // 右侧的矩阵
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();
    private VideoBean mVideoBean;

    private Scroller mScroller;
    private int mWidth;
    private int mHeight;
    private boolean isFlipping = false;        // 是否正在翻转中
    private Bitmap mRightBitmap;               // 右侧图片
    private String mCourseFilePath;            // 根目录
    private int mSwitchType;                   // 底图入场动画类型
    private boolean isVideo;                   // 记录右侧第一个元素是都是视频
    private boolean isEndPosition;             // 是否是某一个大步的最后一个子步
    private boolean isAuto;                    // 是否自动跳转下一步
    private BitmapDrawable mBgDrawable;        // 背景图
    private int mBitmapWidth = 0;              // 图片宽度
    private int mBitmapHeight = 0;             // 图片高度
    private float mLastDownX;
    private long downTime = 0L;

    private OnAnimationListener mListener;

    public TurnPageView(@NonNull Context context) {
        this(context, null);
    }

    public TurnPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurnPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());// 减速 // 动画插入器

        mIvRight = new ImageView(context);
        LayoutParams rightParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mIvRight.setLayoutParams(rightParams);
        mIvRight.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mIvRight);

        mLeftTotalLayout = new FrameLayout(context);
        LayoutParams leftTotalParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLeftTotalLayout.setLayoutParams(leftTotalParams);

        mLeftSubLayout = new FrameLayout(context);
        LayoutParams leftSubParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLeftSubLayout.setLayoutParams(leftSubParams);
        mLeftTotalLayout.addView(mLeftSubLayout);
        addView(mLeftTotalLayout);

        mSurfaceView = new SurfaceView(context);
        LayoutParams playerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mSurfaceView.setLayoutParams(playerParams);

        mLeftTotalLayout.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                mLastDownX = event.getX();
            return false;
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    mLeftSubLayout.removeView(mSurfaceView);
                    performExitAnimation1();
                    break;

                case 2:
                    mLeftTotalLayout.animate().cancel();
                    mLeftTotalLayout.setTranslationX(0f);
                    mLeftTotalLayout.setAlpha(1f);
                    mLeftSubLayout.setTranslationX(0f);
                    mLeftTotalLayout.setBackground(null);

                    mLeftSubLayout.removeAllViews();
                    addCurrentVideo(mVideoBean, mCourseFilePath, isEndPosition);
                    isFlipping = false;
                    isVideo = false;
                    mRightBitmap = null;
                    mIvRight.setImageBitmap(null);
                    break;

                case 3:
                    mLeftSubLayout.removeView(mSurfaceView);
                    break;
            }
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
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

        mRectRight.left = getWidth() / 2;
        mRectRight.top = 0;
        mRectRight.right = getWidth();
        mRectRight.bottom = getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            drawR2L_LeftHalf(canvas);
            drawR2L_RightHalf(canvas);
            drawR2L_FlipHalf(canvas);
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

        if (view != null)
            drawChild(canvas, view, 0);

        mCamera.restore();
        canvas.restore();
    }


    private float getDegX() {
        return mScroller.getCurrX() * 1.0f / mWidth * 180;
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
        mHandler.sendEmptyMessageDelayed(2, 0);
    }

    /**
     * 右中移入
     */
    private void startTranslationLeftIn() {
        if (mRightBitmap == null) {
            mLeftTotalLayout.setTranslationX(-mLeftTotalLayout.getWidth());
            mLeftTotalLayout.animate().translationX(0).setDuration(1000L);
        } else {
            mLeftTotalLayout.animate().translationX(mLeftTotalLayout.getWidth()).setDuration(1000L);
            mIvRight.setTranslationX(-mIvRight.getWidth());
            mIvRight.animate().translationX(0).setDuration(1000L);
        }
        mHandler.sendEmptyMessageDelayed(2, 950);
    }

    /**
     * 右中移入
     */
    private void startTranslationRightIn() {
        if (mRightBitmap == null) {
            mLeftTotalLayout.setTranslationX(mLeftTotalLayout.getWidth());
            mLeftTotalLayout.animate().translationX(0).setDuration(1000L);
        } else {
            mLeftTotalLayout.animate().translationX(-mLeftTotalLayout.getWidth()).setDuration(1000L);
            mIvRight.setTranslationX(mIvRight.getWidth());
            mIvRight.animate().translationX(0).setDuration(1000L);
        }
        mHandler.sendEmptyMessageDelayed(2, 950);
    }

    /**
     * 右移然后翻转
     */
    private void startTranslationRightFlip() {
        mLeftSubLayout.setTranslationX(0);
        mLeftSubLayout.animate().translationX(500).setDuration(1000L).setListener(new CAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                startFlip();
            }

        });
    }

    /**
     * 左移然后翻转
     */
    private void startTranslationLeftFlip() {
        mLeftSubLayout.setTranslationX(0);
        mLeftSubLayout.animate().translationX(-500).setDuration(1000L).setListener(new CAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                startFlip();
            }

        });
    }

    /**
     * 居中翻转
     */
    private void startFlip() {
        MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.fanshu);
        mp.start();
        mScroller.startScroll(0, 0, mWidth, 0, 1000);
        mHandler.sendEmptyMessageDelayed(2, 950);
        postInvalidate();
    }

    /**
     * 淡入
     */
    private void startAlphaIn() {
        if (mRightBitmap == null) {
            mLeftTotalLayout.setAlpha(0f);
            mLeftTotalLayout.animate().alpha(1).setDuration(1000L);
        } else {
            mLeftTotalLayout.animate().alpha(0).setDuration(1000L);
        }
        mHandler.sendEmptyMessageDelayed(2, 950);
    }


    /*********************************************  对外API  **************************************/

    public void setBgDrawable(String path1) {
        mBgDrawable = BitmapUtil.getLocalBitmapDrawable(path1);
        mLeftTotalLayout.setBackground(mBgDrawable);
    }

    public void setVideoBean(VideoBean videoBean) {
        mVideoBean = videoBean;
    }

    /**
     * 设置右侧的底图
     */
    public void setNextDrawable(VideoBean videoBean, String courseFilePath, boolean isEndPosition, boolean isVideo) {
        this.isVideo = isVideo;
        this.isEndPosition = isEndPosition;
        this.isAuto = true;
        this.mCourseFilePath = courseFilePath;
        mVideoBean = videoBean;
        mSwitchType = 19;
        mRightBitmap = BitmapUtil.getLocalBitmap(courseFilePath + videoBean.getFirstFrame());
        mIvRight.setImageBitmap(mRightBitmap);
        postInvalidate();
    }

    /**
     * 当前页页面添加视频
     */
    public void addCurrentVideo(VideoBean videoBean, String courseFilePath, boolean isEndPosition) {
        this.isEndPosition = isEndPosition;
        this.isAuto = true;

        if (isAdd()) {
            mSurfaceView.bringToFront();

        } else {
            mLeftSubLayout.addView(mSurfaceView);
        }

        MediaPlayerHelper.getInstance().setSurfaceView(mSurfaceView).playUrl(getContext(), courseFilePath + videoBean.getVideoPath(), true);
        MediaPlayerHelper.getInstance().setOnStatusCallbackListener(new MediaPlayerHelper.OnStatusCallbackListener() {
            @Override
            public void onStatusonStatusCallbackNext(MediaPlayerHelper.CallBackState status, Object... args) {
                if(status == MediaPlayerHelper.CallBackState.COMPLETE){
                    mHandler.sendEmptyMessageDelayed(3, 80);
                    if (mListener != null)
                        mListener.onAnimationEnd();
                }
            }
        });
    }

    /**
     * 是否有播放器
     */
    public boolean isAdd() {
        boolean isAdd = false;
        for (int i = 0; i < mLeftSubLayout.getChildCount(); i++) {
            if (mLeftSubLayout.getChildAt(i) instanceof VideoPlayerView)
                isAdd = true;
        }
        return isAdd;
    }

    /**
     * 获取音乐播放器层级
     */
    public int getLevel() {
        for (int i = 0; i < mLeftSubLayout.getChildCount(); i++) {
            if (mLeftSubLayout.getChildAt(i) instanceof VideoPlayerView)
                return i;
        }
        return -1;
    }

    /**
     * 当前步是否已经添加图层
     */
    public boolean isAddCurrent() {
        if (mLeftSubLayout.getChildCount() == 0)
            return true;
        return false;
    }

    /**
     * 执行离场动画
     * 如view处于放大状态，先恢复到正常大小
     */
    public void performExitAnimation() {
        if (isFlipping)
            return;

        isFlipping = true;

//        if (mLeftSubLayout.getChildCount() > 0) {
//            mLeftSubLayout.getChildAt(0).setScaleX(1f);
//            mLeftSubLayout.getChildAt(0).setScaleY(1f);
//        }
//
//        if (mRightBitmap == null) {
//            // 淡入
//            startAlphaIn();
//            return;
//        }

        if (isAdd()) {
//            changeVideo();
            mHandler.sendEmptyMessageDelayed(1, 100);
            return;
        }

        performExitAnimation1();
    }

    /**
     * 将视频最后一帧替换成图片
     */
    private void changeVideo() {
        MediaPlayerHelper.getInstance().stop();

        ImageView ivFrame = new ImageView(getContext());
        LayoutParams ivFrameParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        ivFrame.setLayoutParams(ivFrameParams);
        ivFrame.setImageBitmap(BitmapUtil.getLocalBitmap(mVideoPlayer.getLastFramePath()));
        ivFrame.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        mLeftSubLayout.addView(ivFrame, getLevel());
//
//        ImageView iv = new ImageView(getContext());
//        LayoutParams ivParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        iv.setLayoutParams(ivParams);
//        mLeftSubLayout.setDrawingCacheEnabled(true);
//        mLeftSubLayout.buildDrawingCache();  //启用DrawingCache并创建位图
//        iv.setImageBitmap(Bitmap.createBitmap(mLeftSubLayout.getDrawingCache()));
//        mLeftSubLayout.setDrawingCacheEnabled(false);
//        mLeftSubLayout.addView(iv);
    }

    public void performExitAnimation1() {
        if (mSwitchType == 3) {
            // 左中移入
            startTranslationLeftIn();
        } else if (mSwitchType == 8) {
            // 右中移入
            startTranslationRightIn();
        } else if (mSwitchType == 19) {
            // 正常：居中向左翻页      上一步：居中向右翻页
            startFlip();
        } else if (mSwitchType == 20) {
            // 右移向左翻页
            startTranslationRightFlip();
        } else {
            // 淡入
            startAlphaIn();
        }
    }

    /**
     * 清空所有的子view
     */
    public void clear() {
        mLeftSubLayout.removeAllViews();
        mSwitchType = 0;
        mRightBitmap = null;
        mIvRight.setImageBitmap(null);
        postInvalidate();
    }

    public void removeTheLastTwoView() {
        if (mLeftSubLayout.getChildCount() == 1) {
            mLeftSubLayout.removeView(mLeftSubLayout.getChildAt(mLeftSubLayout.getChildCount() - 1));
        } else if (mLeftSubLayout.getChildCount() > 1) {
            mLeftSubLayout.removeView(mLeftSubLayout.getChildAt(mLeftSubLayout.getChildCount() - 1));
            mLeftSubLayout.removeView(mLeftSubLayout.getChildAt(mLeftSubLayout.getChildCount() - 1));
        }
    }

    public void clearLeft() {
        mLeftSubLayout.removeAllViews();
        mVideoPlayer.stopPlayer();
    }

    public VideoPlayerView getVideoPlayer() {
        return mVideoPlayer;
    }

    /**
     * 设置翻页监听
     */
    public void setOnAnimationListener(OnAnimationListener listener) {
        mListener = listener;
    }

}
