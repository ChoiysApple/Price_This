package com.example.price_this.price_this;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference goodsDatabase;
    FloatingActionButton btn_floating;
    Toolbar naviBar;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

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

        //final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("test"); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {

                    // child 내에 있는 데이터만큼 반복합니다.
                   FirebaseLoad msg = messageData.getValue(FirebaseLoad.class);
                   GoodsInfoArrayList.add(new GoodsInfo(msg.img, msg.price, msg.price, "A", msg.name, msg.tags));
                   MyAdapter myAdapter = new MyAdapter(GoodsInfoArrayList);

                   mRecyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class FirebaseLoad {
        public String id;
        public String name;
        public String img;
        public String desc;
        public ArrayList<String> tags;
        public String spec;
        public String price;
        public FirebaseLoad(){}
        public FirebaseLoad(String id, String name, String img, String desc, ArrayList<String> tags, String spec, String price){
            this.id = id;
            this.name = name;
            this.img = img;
            this.desc = desc;
            this.tags = tags;
            this.spec = spec;
            this.price = price;
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("name", name);
            result.put("img", img);
            result.put("price", price);
            result.put("description", desc);
            result.put("spec", spec);
            result.put("tags", tags);
            return result;
        }
    }

    private void initDatabase(){
        mChild = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
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
