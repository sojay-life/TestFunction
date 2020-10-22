package com.sojay.testfunction.card.card1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.LoadImgUtil;

import java.util.List;

public class NewPagerAdapter extends PagerAdapter {

    private List<String> mList;

    public NewPagerAdapter(List<String> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.98f;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.aa, null);
        ImageView img = view.findViewById(R.id.img);
        LoadImgUtil.ins().loadImage(container.getContext(), mList.get(position % mList.size()), img);
        container.addView(view);
        return view;
    }

}
