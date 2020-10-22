package com.sojay.testfunction.card.card2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.DensityUtil;
import com.sojay.testfunction.utils.LoadImgUtil;
import com.sojay.testfunction.view.RoundedImageView;

import java.util.List;

public class CardLayout extends FrameLayout {

    private Context mContext;
    private OnShowTopListener mListener;
    private int mChildWidth;
    private int mChildHeight;
    private int mChildShowCount;
    private int mChildRebound;  // 回弹值
    private int mChildDisappear;  // 消失值
    private int mChildMargin;  // 每个view之间的距离
    private float mChildScalingBase; // view的缩放基数值
    private float mChildCornerRadius;
    private int mScaleModel;  // 缩放模式，0:全部缩放   1:缩放前2个

    public CardLayout(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public CardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CardLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CardLayout);
            mChildWidth = ta.getDimensionPixelSize(R.styleable.CardLayout_child_width, 0);
            mChildHeight = ta.getDimensionPixelSize(R.styleable.CardLayout_child_height, 0);
            mChildCornerRadius = ta.getDimensionPixelSize(R.styleable.CardLayout_child_cornerRadius, 0);
            mChildRebound = ta.getDimensionPixelSize(R.styleable.CardLayout_child_rebound, 0);
            mChildDisappear = ta.getDimensionPixelSize(R.styleable.CardLayout_child_disappear, 0);
            mChildDisappear = ta.getDimensionPixelSize(R.styleable.CardLayout_child_margin, 0);
            mChildScalingBase = ta.getFloat(R.styleable.CardLayout_child_scaling_base, 0);
            mChildShowCount = ta.getInteger(R.styleable.CardLayout_child_show_count, 0);

            if (mChildWidth == 0)
                mChildWidth = DensityUtil.dp2px(mContext, 82f);
            if (mChildHeight == 0)
                mChildHeight = DensityUtil.dp2px(mContext, 82f);
            if (mChildCornerRadius == 0)
                mChildCornerRadius = DensityUtil.dp2px(mContext, 2.5f);
            if (mChildRebound == 0)
                mChildRebound = DensityUtil.dp2px(mContext, 55f);
            if (mChildDisappear == 0)
                mChildDisappear = DensityUtil.dp2px(mContext, 112f);
            if (mChildMargin == 0)
                mChildMargin = DensityUtil.dp2px(mContext, 8f);
            if (mChildScalingBase == 0)
                mChildScalingBase = 0.14f;
            if (mChildShowCount == 0)
                mChildShowCount = 3;
        }
    }

    public void addAllView(List<BookBean> list) {

        if (list == null || list.size() == 0)
            return;

        removeAllViews();
        if (mChildShowCount > list.size())
            mChildShowCount = list.size();

        if (mListener != null)
            mListener.onShowTop(list.get(0));

        if (mChildHeight > getHeight())
            mChildWidth = mChildHeight = getHeight();

        for (int i = list.size() - 1; i >= 0; i--) {

            int position = i;

            RoundedImageView cardView = new RoundedImageView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(mChildWidth, mChildHeight);
            cardView.setLayoutParams(params);
            cardView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cardView.setCornerRadius(mChildCornerRadius);
            cardView.setRebound(mChildRebound);
            cardView.setDisappear(mChildDisappear);
            LoadImgUtil.ins().loadImage(mContext, list.get(i).getCover(), cardView);

            if (list.size() == 1 || mChildShowCount == 1) {
                cardView.setX(getWidth() - mChildWidth);
            } else {
                if (i < mChildShowCount) {
                    cardView.setX(getWidth() - mChildWidth - (mChildMargin * (mChildShowCount - 1 - i)));
                } else {
                    cardView.setX(getWidth() - mChildWidth);
                }
            }

            cardView.setPivotX(mChildWidth);
            cardView.setPivotY(mChildHeight);
            cardView.setScaleX(1 - (mChildScalingBase * i));
            cardView.setScaleY(1 - (mChildScalingBase * i));
            cardView.setCanSwipe(mChildShowCount > 1 && list.size() > 1 && i == 0);

            cardView.setOnSwipeListener(new RoundedImageView.OnSwipeListener() {
                @Override
                public void onSwiped() {
                    BookBean bean = list.get(position);
                    list.remove(position);
                    list.add(bean);
                    addAllView(list);
                }

                @Override
                public void onSwiping(int left) {

                    if (left < -mChildRebound)
                        return;

                    cardView.setScaleX(-left * 1f / mChildWidth * 0.1f + 1f);
                    cardView.setScaleY(-left* 1f / mChildWidth * 0.1f + 1f);

                    float sca = (mChildRebound + left) * 1f / mChildRebound;

                    for (int j = 0; j < mChildShowCount; j++) {
                        if (j > list.size() - 2)
                            return;
                        View view = getChildAt(list.size() - 2 - j);
                        view.setScaleX(1 - (mChildScalingBase * (sca + j)));
                        view.setScaleY(1 - (mChildScalingBase *  (sca + j)));
                        if (j < list.size() - 2)
                            view.setTranslationX(getWidth() - mChildWidth - (mChildMargin * (mChildShowCount - 1 - j - sca)));
                    }

                }

            });

            addView(cardView);

        }
    }

    public void setOnShowTopListener(OnShowTopListener listener) {
        this.mListener = listener;
    }

    public interface OnShowTopListener {
        void onShowTop(BookBean bean);
    }

}
