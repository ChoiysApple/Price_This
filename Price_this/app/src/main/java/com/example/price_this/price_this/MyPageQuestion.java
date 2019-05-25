package com.example.price_this.price_this;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPageQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewGoodsName;
        TextView txtViewGoodsDate;
        TextView txtViewGoodsTag;

        MyViewHolder(View view){
            super(view);
            txtViewGoodsName = view.findViewById(R.id.txtView_productName);
            txtViewGoodsName.setMaxLines(1);
            txtViewGoodsDate = view.findViewById(R.id.txtView_date);
            txtViewGoodsTag = view.findViewById(R.id.txtView_goodsTag);
        }
    }

    private ArrayList<GoodsInfo> goodsInfoArrayList;
    MyPageQuestion(ArrayList<GoodsInfo> goodsInfoArrayList){
        this.goodsInfoArrayList = goodsInfoArrayList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_question, parent, false);

        return new MyPageQuestion.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyPageQuestion.MyViewHolder myViewHolder = (MyPageQuestion.MyViewHolder) holder;

        myViewHolder.txtViewGoodsName.setText(goodsInfoArrayList.get(position).goodsName);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                Context context = v.getContext();
                intent = new Intent(context, Product.class);
                intent.putExtra("name", goodsInfoArrayList.get(position).goodsName);
                intent.putExtra("image", goodsInfoArrayList.get(position).goodsPicture);
                intent.putExtra("price", goodsInfoArrayList.get(position).goodsPrice);
                intent.putExtra("Tags", goodsInfoArrayList.get(position).goodsTag);
                context.startActivity(intent);
                Toast.makeText(context, "이거는 "+goodsInfoArrayList.get(position).goodsName+ "이야 으아악 누르지마", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsInfoArrayList.size();
    }
}
