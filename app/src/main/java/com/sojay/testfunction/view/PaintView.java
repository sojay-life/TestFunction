package com.sojay.testfunction.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.sojay.testfunction.Constant;
import com.sojay.testfunction.R;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2016-12-28.
 */

public class PaintView extends View {

    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    private Bitmap mBitmap;
    private Paint mPaint;

    private ArrayList<DrawPath> savePath;
    private DrawPath dp;
    private int mColor = R.color.black;
    private int mPaintSize = 2;  //默认画笔粗细为2

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 0;

    private int bitmapWidth;
    private int bitmapHeight;

    private int left;
    private int top;

    public PaintView(Context context) {
        super(context);
        //得到屏幕的分辨率
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels ;

        initCanvas();
        savePath = new ArrayList<>();
    }

    public boolean isEmpty(){
        return savePath.size() == 0;
    }

    public Bitmap getDrawBitmap(){
        return mBitmap;
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //得到屏幕的分辨率
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels ;

        initCanvas();
        savePath = new ArrayList<>();
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置画笔颜色
     */
    public void setPaintColor(int color){
        mColor = color;
        mPaint.setColor(getResources().getColor(color));
    }

    /**
     * 设置橡皮擦
     */
    public void setXfermode() {
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        savePath.clear();
    }

    /**
     * 全清
     */
    public void clearAll() {
        initCanvas();
        invalidate();
    }

    /**
     * 设置画笔粗细
     */
    public void setPaintSize(int paintSize){
        mPaintSize = paintSize;
        mPaint.setStrokeWidth(mPaintSize);
    }

    /**
     * 空白处的点击事件,用于别的模式下,点击空白处的事件捕捉.
     * @param listener
     */
//    public void setEmptyClick(NoResultListener listener){
//        mListener = listener;
//    }

    /**
     * 初始化画布
     */
    public void initCanvas(){

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(mColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mPaintSize);

        //画布大小
        mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中

        mCanvas.drawColor(Color.TRANSPARENT);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);     //显示旧的画布
        if (mPath != null) {
            // 实时的显示
            mCanvas.drawPath(mPath, mPaint);
        }
    }

    /**
     * 路径对象
     */
    class DrawPath{
        Paint paint;
        Path path;
        int paintColor;
        int paintSize;
    }

    /**
     * 撤销的核心思想就是将画布清空，
     * 将保存下来的Path路径最后一个移除掉，
     * 重新将路径画在画布上面。
     */
    public void undo(){
        if(savePath != null && savePath.size() > 0){
            //调用初始化画布函数以清空画布
            initCanvas();

            //将路径保存列表中的最后一个元素删除 ,并将其保存在路径删除列表中
            savePath.remove(savePath.size() - 1);
            //将路径保存列表中的路径重绘在画布上
            Iterator<DrawPath> iter = savePath.iterator();      //重复保存
            while (iter.hasNext()) {
                DrawPath dp = iter.next();
                dp.paint.setColor(getResources().getColor(dp.paintColor));
                dp.paint.setStrokeWidth(dp.paintSize);
                mCanvas.drawPath(dp.path, dp.paint);

//                mPaint.setColor(getResources().getColor(dp.paintColor));
//                mColor = dp.paintColor;
//                mPaint.setStrokeWidth(dp.paintSize);
//                mCanvas.drawPath(dp.path, mPaint);
            }
            invalidate();// 刷新
        }
    }

    private void touch_start(float x, float y) {
        mPath.reset();//清空path
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        mPath.quadTo(mX, mY, x+0.01f, y+0.01f);
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //mPath.quadTo(mX, mY, x, y);
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        savePath.add(dp);

        Constant.path = mPath;


        mPath = null;

    }

    private boolean isEnable = true;

    public void setCanEdit(boolean can){
        isEnable = can;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnable){
//            if (mListener!=null)
//                mListener.onResult();
            return false;
        }
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                dp = new DrawPath();
                dp.paint = mPaint;
                dp.path = mPath;
                dp.paintColor = mColor;
                dp.paintSize = mPaintSize;
                touch_start(x, y);
                invalidate(); //清屏
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

}
