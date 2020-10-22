package com.sojay.testfunction.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.LoadImgUtil;

import java.util.List;

public class PuzzleAdapter extends RecyclerView.Adapter {

    private List<PuzzleBean> datas;
    private Context context;

    public PuzzleAdapter(List<PuzzleBean> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puzzle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LoadImgUtil.ins().loadImage(context, datas.get(position).getUrl(), 0, ((ViewHolder) holder).puzzle, new RequestListener<Bitmap>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                datas.get(position).setBitmap(bitmap);
                ((ViewHolder) holder).puzzle.setPuzzleBean(datas.get(position));
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CPuzzleItemView puzzle;

        ViewHolder(View itemView) {
            super(itemView);
            puzzle = itemView.findViewById(R.id.iv_puzzle);
        }

    }
}