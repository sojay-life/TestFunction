package com.sojay.testfunction.card.card3;

import androidx.recyclerview.widget.RecyclerView;

public interface OnSwipeListener<T> {

    /**
     * 卡片还在滑动时回调
     *
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio      滑动进度的比例
     */
    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio);

    /**
     * 卡片完全滑出时回调
     *
     * @param viewHolder 该滑出卡片的viewHolder
     * @param t          该滑出卡片的数据
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder, T t);

    /**
     * 所有的卡片全部滑出时回调
     */
    void onSwipedClear();
}
