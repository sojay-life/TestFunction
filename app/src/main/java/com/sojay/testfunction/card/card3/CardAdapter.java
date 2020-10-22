package com.sojay.testfunction.card.card3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sojay.testfunction.R;

import java.util.Arrays;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter {

    private List<String> datas;
    private Context context;
    private List<Integer> imageUrls = Arrays.asList(
            R.drawable.xm2,
            R.drawable.xm3,
            R.drawable.xm4,
            R.drawable.xm5,
            R.drawable.xm6,
            R.drawable.xm7,
            R.drawable.xm1,
            R.drawable.xm8,
            R.drawable.xm9,
            R.drawable.xm1,
            R.drawable.xm2,
            R.drawable.xm3,
            R.drawable.xm4,
            R.drawable.xm5,
            R.drawable.xm6
    );

    public CardAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(imageUrls.get(Integer.valueOf(datas.get(position)))).into(((ViewHolder)holder).cover);
        ((ViewHolder)holder).index.setText(datas.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cover;
        TextView index;

        ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            index = itemView.findViewById(R.id.index);
        }

    }
}