package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference goodsDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.mypage);

        //리사이클러뷰 설정
        mRecyclerView = findViewById(R.id.mypage_question);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView2 = findViewById(R.id.mypage_answer);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView2.setLayoutManager(mLayoutManager);


        goodsDatabase = FirebaseDatabase.getInstance().getReference().child("이름");
        ArrayList<String> tags = new ArrayList<>();
        tags.add("딸기");
        tags.add("음식");
        ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"5,000원", "6,000원", "A", "딸기", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "4,600원","6,000원", "A",  "빵", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원","8,000원", "B",  "국수먹고싶다", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.berry,"115,000원", "116,000원", "S", "금딸기", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.bread, "1,234,114,600원", "2,136,500원", "S", "빵", tags));
        GoodsInfoArrayList.add(new GoodsInfo(R.drawable.noodle, "4,000원","6,000원", "C",  "요즘누가짜장면을사천원에팔아", tags));


        MyPageQuestion myPageQuestion = new MyPageQuestion(GoodsInfoArrayList);
        MyPageAnswer myPageAnswer = new MyPageAnswer(GoodsInfoArrayList);

        mRecyclerView.setAdapter(myPageQuestion);
        mRecyclerView2.setAdapter(myPageAnswer);

    }
}
