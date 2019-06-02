package com.example.price_this.price_this;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    LayoutInflater inflater;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewPicture;
        TextView txtViewPrice;
        TextView txtViewGoodsName;

        MyViewHolder(View view){
            super(view);
            imgViewPicture = view.findViewById(R.id.imgViewPicture);
            txtViewPrice = view.findViewById(R.id.txtViewPrice);
            txtViewPrice.setMaxLines(1);
            txtViewGoodsName = view.findViewById(R.id.txtViewName);
            txtViewGoodsName.setMaxLines(1);
        }
    }

    private ArrayList<GoodsInfo> goodsInfoArrayList;
    MyAdapter(ArrayList<GoodsInfo> goodsInfoArrayList){
        this.goodsInfoArrayList = goodsInfoArrayList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        context = parent.getContext();

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://price-this.appspot.com/");
        StorageReference storageRef = storage.getReference();
        StorageReference pathReference = storageRef.child(goodsInfoArrayList.get(position).goodsPicture);

        pathReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                myViewHolder.imgViewPicture.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                myViewHolder.txtViewGoodsName.setText(String.format("Failure: %s", exception.getMessage()));
            }
        });

        myViewHolder.txtViewPrice.setText(goodsInfoArrayList.get(position).crrtgoodsPrice);
        myViewHolder.txtViewGoodsName.setText(goodsInfoArrayList.get(position).goodsName);

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

    public GoodsInfo getItem(int position) {
        return goodsInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}