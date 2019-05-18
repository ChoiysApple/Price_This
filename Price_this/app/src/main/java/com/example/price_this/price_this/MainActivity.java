package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

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

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"5,000원", "딸기"));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "4,600원", "빵"));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원", "국수먹고싶다"));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"115,000원", "금딸기"));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "1,234,114,600원", "빵"));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원", "요즘누가짜장면을사천원에팔아"));

        MyAdapter myAdapter = new MyAdapter(GoodsInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
    }
}
