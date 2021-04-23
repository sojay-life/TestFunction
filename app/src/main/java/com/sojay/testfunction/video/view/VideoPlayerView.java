package com.sojay.testfunction.video.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.BitmapUtil;
import com.sojay.testfunction.video.listener.OnMediaPlayerListener;

public class VideoPlayerView extends FrameLayout {

    private SurfaceView mSurfaceView;
    private ImageView mIvThumb;
    private MediaPlayer mMediaPlayer;
    private OnMediaPlayerListener mListener;
    private Handler mHandler;
    private Runnable mRunnable;

    private boolean isNeedResumePlayer;    // 是否需要恢复播放
    private String mPath;
    private boolean isPrepare;
    private boolean isCompletion;
    private String mLastFramePath;
    private Bitmap mLastFrameBitmap;

    public VideoPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public VideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.video_player_layout, this);
        mSurfaceView = findViewById(R.id.surface_view);
        mIvThumb = findViewById(R.id.iv_thumb);

        mHandler = new Handler();
        mRunnable = () -> {
            if (mIvThumb.getVisibility() != GONE)
                mIvThumb.setVisibility(GONE);
        };

        mMediaPlayer = new MediaPlayer();

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mMediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mMediaPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        mMediaPlayer.setOnPreparedListener(mp -> {
            mMediaPlayer.start();
            isNeedResumePlayer = true;
            isPrepare = false;
            mHandler.postDelayed(mRunnable, 350);
        });

        mMediaPlayer.setOnCompletionListener(mp -> {
            mIvThumb.setVisibility(VISIBLE);
            if (mLastFrameBitmap != null)
                mIvThumb.setImageBitmap(mLastFrameBitmap);
            else
                mIvThumb.setImageBitmap(BitmapUtil.getLocalBitmap(mLastFramePath));
            isCompletion = true;
            mMediaPlayer.reset();
            isNeedResumePlayer = false;
            isPrepare = false;
            if (mListener != null)
                mListener.onCompletion(mPath);
        });

        mMediaPlayer.setOnErrorListener((mp, what, extra) -> {
            isNeedResumePlayer = false;
            isPrepare = false;
            return false;
        });

    }

    /**
     * 设置播放地址并播放
     */
    public void startPlayer(String path, String thumbPath, Bitmap thumbBitmap) {
        if (TextUtils.isEmpty(path))
            return;

        if (path.equals(mPath) && mMediaPlayer.isPlaying())
            return;

        mPath = path;

        if (mIvThumb.getVisibility() != VISIBLE)
            mIvThumb.setVisibility(VISIBLE);

        if (thumbBitmap != null)
            mIvThumb.setImageBitmap(thumbBitmap);
        else
            mIvThumb.setImageBitmap(BitmapUtil.getLocalBitmap(thumbPath));

        if (mMediaPlayer.isPlaying())
            mMediaPlayer.stop();

        mMediaPlayer.reset();
        isPrepare = true;
        isCompletion = false;

        try {
            mMediaPlayer.setDataSource(path);
            // 通过异步的方式装载媒体资源
            mMediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLastFramePath(String path) {
        mLastFramePath = path;
        mLastFrameBitmap = BitmapUtil.getLocalBitmap(mLastFramePath);
    }

    public String getLastFramePath() {
        return mLastFramePath;
    }

    /**
     * 设置播放地址并播放
     */
    public void startPlayer(String path, Bitmap thumbBitmap) {
        startPlayer(path, "", thumbBitmap);
    }

    /**
     * 设置播放地址并播放
     */
    public void startPlayer(String path, String thumbPath) {
        startPlayer(path, thumbPath, null);
    }

    public void seekTo(long time) {
        if (time > mMediaPlayer.getDuration())
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mMediaPlayer.seekTo(time, MediaPlayer.SEEK_CLOSEST);
        } else {
            mMediaPlayer.seekTo((int) time);
        }
    }

    /**
     * 用于页面的生命周期使用
     */
    public void resume() {
        if (isNeedResumePlayer && !mMediaPlayer.isPlaying())
            mMediaPlayer.start();
    }

    /**
     * 用于页面的生命周期使用
     */
    public void pause() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
    }

    public void resumePlayer() {
        if (!TextUtils.isEmpty(mPath) && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            isNeedResumePlayer = true;
        }
    }

    public void pausePlayer() {
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        isNeedResumePlayer = false;
    }

    public void stopPlayer() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        mMediaPlayer.setDisplay(null);

        isNeedResumePlayer = false;
        isPrepare = false;
    }

    public void showLastThumb() {
        mIvThumb.setVisibility(VISIBLE);
        if (mLastFrameBitmap != null)
            mIvThumb.setImageBitmap(mLastFrameBitmap);
        else
            mIvThumb.setImageBitmap(BitmapUtil.getLocalBitmap(mLastFramePath));
    }

    public boolean isCompletion() {
        return isCompletion;
    }

    public boolean isPlaying() {
        if (mMediaPlayer == null || TextUtils.isEmpty(mPath))
            return false;
        return isPrepare || mMediaPlayer.isPlaying();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void setOnMediaPlayerListener(OnMediaPlayerListener listener) {
        mListener = listener;
    }

}
