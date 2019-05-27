package com.example.price_this.price_this;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference goodsDatabase;
    FloatingActionButton btn_floating;
    Toolbar naviBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);

        //네비게이션바(액션바) 설정
        naviBar = (Toolbar)findViewById(R.id.rabbit_toolbar);
        setSupportActionBar(naviBar);
        getSupportActionBar().setIcon(R.drawable.app_banner);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, naviBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        goodsDatabase = FirebaseDatabase.getInstance().getReference();

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rgst) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
        } else if (id == R.id.rgst) {
        } else if (id == R.id.command) {
        } else if (id == R.id.bug_report) {
        } else if (id == R.id.setting) {
        } else if (id == R.id.logout) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
