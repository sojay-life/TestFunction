package com.sojay.testfunction.gxgy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sojay.testfunction.R;
import com.sojay.testfunction.utils.DataUtil;

import java.util.List;

public class GuangXianAdapter extends RecyclerView.Adapter {

    private List<GuangXianBean> datas;

    public GuangXianAdapter(List<GuangXianBean> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guangxian, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).max.setText("最大:" + DataUtil.keepTwo(datas.get(position).getMax()));
        ((ViewHolder)holder).min.setText("最小:" + DataUtil.keepTwo(datas.get(position).getMin()));
        ((ViewHolder)holder).py.setText("平均:" + DataUtil.keepTwo(datas.get(position).getPingjun()));
        ((ViewHolder)holder).date.setText("Save Time ：" + datas.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView max;
        TextView min;
        TextView py;
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            max = itemView.findViewById(R.id.item_tv_max);
            min = itemView.findViewById(R.id.item_tv_min);
            py = itemView.findViewById(R.id.item_tv_py);
            date = itemView.findViewById(R.id.item_tv_date);
        }

    }
}