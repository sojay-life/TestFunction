package com.sojay.testfunction.card.card1;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class CardTransformer implements ViewPager.PageTransformer {

    private int mOffset = 20;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void transformPage(View page, float position) {

        // viewpager item平移到叠加状态
        // +mOffset 有叠加的效果
        if (position >= 0) {
            page.setTranslationX(- (page.getWidth() + mOffset) * position);
        }

        // 控制除最上面的item外，其余item进行缩放
        if (position != 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
            page.setScaleX(1 - (0.1f * position));
            page.setScaleY(1 - (0.1f * position));
        }

        // 控制最上面的item滑动透明渐变
        if (position <= 0) {
            page.setAlpha(1 + (position * 0.7f));
            page.setRotation(10 * position);
        }

    }

}