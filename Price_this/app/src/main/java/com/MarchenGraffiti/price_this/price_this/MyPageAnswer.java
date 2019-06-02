package com.MarchenGraffiti.price_this.price_this;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPageAnswer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewGoodsName;
        TextView txtViewGoodsDate;
        TextView txtViewGoodsPrice;
        TextView txtViewGoodsTag;

        MyViewHolder(View view){
            super(view);
            txtViewGoodsName = view.findViewById(R.id.txtView_productName);
            txtViewGoodsName.setMaxLines(1);
            txtViewGoodsPrice = view.findViewById(R.id.txtView_date);
            txtViewGoodsTag = view.findViewById(R.id.txtView_goodsTag);
        }
    }

    private ArrayList<GoodsInfo> goodsInfoArrayList;
    MyPageAnswer(ArrayList<GoodsInfo> goodsInfoArrayList){
        this.goodsInfoArrayList = goodsInfoArrayList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_answer, parent, false);

        return new MyPageAnswer.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyPageAnswer.MyViewHolder myViewHolder = (MyPageAnswer.MyViewHolder) holder;

        myViewHolder.txtViewGoodsName.setText(goodsInfoArrayList.get(position).goodsName);
        myViewHolder.txtViewGoodsPrice.setText(goodsInfoArrayList.get(position).crrtgoodsPrice);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                Context context = v.getContext();
                intent = new Intent(context, Product.class);
                intent.putExtra("id", goodsInfoArrayList.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsInfoArrayList.size();
    }
}
