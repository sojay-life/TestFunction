package com.sojay.testfunction.puzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.sojay.testfunction.Constant;

import java.util.ArrayList;
import java.util.List;

public class CPuzzleView extends View {

    private int itemWidth;
    private float itemBulgeWidth;    // 凸出的宽度
    private Context mContext;
    private List<PuzzleBean> list;


    public CPuzzleView(Context context) {
        super(context);
        initView(context);
    }

    public CPuzzleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CPuzzleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        list = new ArrayList<>();
        setOnDragListener((v, event) -> {
            if (event.getAction() == DragEvent.ACTION_DROP) {
                // 拖拽放下
                // 拿到view本身数据
                PuzzleBean bean = (PuzzleBean) event.getLocalState();
                int position = bean.getPosition();
                int left = position % 3 * itemWidth;
                int top = position / 3 * itemWidth;
                int right = left + itemWidth;
                int bottom = top + itemWidth;

                // 判断当前位置是否是在正确范围内
                boolean inFW = event.getX() > left && event.getX() < right &&
                        event.getY() > top && event.getY() < bottom;

                Constant.isOk = inFW;

                // 在范围内 并且没有添加进列表  添加进列表 刷新
                if (inFW && !list.contains(bean)) {
                    list.add(bean);
                    invalidate();
                }
            }
            return true;
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = (int) (w / 3f);
        itemBulgeWidth = itemWidth / 7f;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        for (PuzzleBean puzzle : list) {
            int position = puzzle.getPosition();
            float left = position % 3 * itemWidth - (itemBulgeWidth * puzzle.getLeft());
            float top = (position / 3) * itemWidth - (itemBulgeWidth * puzzle.getTop());
            float right = left + (itemBulgeWidth * puzzle.getLeft()) + itemWidth + (itemBulgeWidth * puzzle.getRight());
            float bottom = top + (itemBulgeWidth * puzzle.getTop()) + itemWidth + (itemBulgeWidth * puzzle.getBottom());
            canvas.drawBitmap(puzzle.getBitmap(), null, new RectF(left, top, right, bottom), new Paint());
        }
    }



}
