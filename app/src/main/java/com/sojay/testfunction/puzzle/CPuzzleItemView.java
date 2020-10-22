package com.sojay.testfunction.puzzle;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.sojay.testfunction.Constant;

public class CPuzzleItemView extends AppCompatImageView {

    private PuzzleBean mBean;

    public CPuzzleItemView(Context context) {
        super(context);
        initView(context);
    }

    public CPuzzleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CPuzzleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {

        setOnLongClickListener(v -> {
            if (mBean == null || mBean.getBitmap() == null || getAlpha() == 0)
                return false;

            // 获取系统震动服务  记得添加VIBRATE权限
            Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
            // 震动20毫秒
            vib.vibrate(20);

            DragShadowBuilder builder = new DragShadowBuilder(v);
            // 第三个参数是传入一个关于这个view信息的任意对象（getLocalState），
            // 它即你需要在拖拽监听中的调用event.getLocalState()获取到这个对象来操作用的(比如传入一个RecyclerView中的position)。
            // 如果不需要这个对象，传null
            v.startDrag(null, builder, mBean, 0);

            return true;
        });

        setOnDragListener((v, event) -> {
            PuzzleBean bean = (PuzzleBean) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // 拖拽开始，隐藏当前的view
                    Constant.isOk = false;
                    if ((int)v.getTag() == bean.getPosition())
                        v.setAlpha(0);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    // 拖拽结束
                    // 接口回调判断是否显示
                    if ((int)v.getTag() == bean.getPosition())
                        v.setAlpha(Constant.isOk ? 0f : 1f);
                    break;

            }
            return true;
        });
    }

    public void setPuzzleBean(PuzzleBean bean) {
        this.mBean = bean;
        setTag(bean.getPosition());
    }
}
