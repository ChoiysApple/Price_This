package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference goodsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.activity_main);

        //리사이클러뷰 설정
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        goodsDatabase = FirebaseDatabase.getInstance().getReference().child("이름");

        ArrayList<String> tags = new ArrayList<>();
        tags.add("딸기");
        tags.add("음식");

        ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"5,000원", "딸기", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "4,600원", "빵", null));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원", "국수먹고싶다", null));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"115,000원", "금딸기", null));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "1,234,114,600원", "빵", null));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원", "요즘누가짜장면을사천원에팔아", null));

        MyAdapter myAdapter = new MyAdapter(GoodsInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
    }
}
