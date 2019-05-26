package com.example.price_this.price_this;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference goodsDatabase;
    FloatingActionButton btn_floating;
    Toolbar naviBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        */
        setContentView(R.layout.activity_main);

        //네비게이션바(액션바) 설정
        naviBar = (Toolbar)findViewById(R.id.rabbit_toolbar);
        setSupportActionBar(naviBar);
        getSupportActionBar().setIcon(R.drawable.app_banner);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);

        //리사이클러뷰 설정
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        btn_floating = findViewById(R.id.btn_floating);
        btn_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

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


        MyAdapter myAdapter = new MyAdapter(GoodsInfoArrayList);

        mRecyclerView.setAdapter(myAdapter);
    }
//네비게이션바 설정하기
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.rgst:
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);

        }
    }
}
