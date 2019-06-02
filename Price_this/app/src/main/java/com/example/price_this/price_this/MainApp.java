package com.example.price_this.price_this;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ArrayList<String> oldPostKey;
    String oldPostID;
    MyAdapter myAdapter;
    SwipeRefreshLayout mSwipeContainer;

    TextView userName;
    TextView userEmail;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    SharedPreferences auto;
    SharedPreferences.Editor toEdit;
    private String PREF_USER_ACCOUNT = "account";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private boolean isUserHeader = false;

    final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
    final ArrayList<GoodsInfo> GoodsInfoArrayList_get = new ArrayList<>();

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

        mSwipeContainer = findViewById(R.id.swipe_layout);

        btn_floating = findViewById(R.id.btn_floating);
        btn_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        //change header info
        View navi_view = navigationView.getHeaderView(0);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userName = (TextView) navi_view.findViewById(R.id.txtView_userName);
        userEmail = (TextView) navi_view.findViewById(R.id.txtView_userEmail);
        if (user != null) {
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());
        }

        //DB load
        goodsDatabase = FirebaseDatabase.getInstance().getReference();

        //final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        //final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        //final ArrayList<GoodsInfo> GoodsInfoArrayList_get = new ArrayList<>();
        oldPostKey = new ArrayList<>();

        loadData();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //마지막 체크
                if(!mRecyclerView.canScrollVertically(-1)){
                    mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            loadData();
                            mSwipeContainer.setRefreshing(false);
                        }
                    });
                }
                if(!mRecyclerView.canScrollVertically(1)){
                    if(GoodsInfoArrayList_get.size()==10){
                        Toast.makeText(getApplicationContext(), "잠시만 기다려주세요~", Toast.LENGTH_SHORT).show();
                    }
                    mReference.orderByKey().endAt(oldPostID).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            GoodsInfoArrayList_get.clear(); //임시저장 위치
                            oldPostKey.clear();
                            for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                                FirebaseLoad msg = messageData.getValue(FirebaseLoad.class);
                                GoodsInfoArrayList_get.add(0, new GoodsInfo(msg.avgPrice, msg.id, msg.name, msg.img, msg.price));
                                oldPostKey.add(messageData.getKey());
                            }
                            //불러오는 중인지, 전부 불러왔는지 if문
                            if( GoodsInfoArrayList_get.size() > 1) {//1개라도 있으면 불러옴
                                //마지막 중복되는 부분 삭제
                                GoodsInfoArrayList_get.remove(0);
                                //contents 뒤에 추가
                                GoodsInfoArrayList.addAll( GoodsInfoArrayList_get);
                                oldPostID = oldPostKey.get(0);
                                //메시지 갱신 위치
                                myAdapter.notifyDataSetChanged();
                            } else {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "모든 상품을 다 살펴보았어요!", Snackbar.LENGTH_SHORT)
                                        .setAction("닫기", new View.OnClickListener() {@Override public void onClick(View view) {}}).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    public void loadData() {
        ArrayList<String> tags = new ArrayList<>();
        //final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
        oldPostKey = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("test"); // 변경값을 확인할 child 이름
        mReference.limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    FirebaseLoad msg = messageData.getValue(FirebaseLoad.class);
                    GoodsInfoArrayList.add(0, new GoodsInfo(msg.avgPrice, msg.id, msg.name, msg.img, msg.price));
                    GoodsInfoArrayList_get.add(0, new GoodsInfo(msg.avgPrice, msg.id, msg.name, msg.img, msg.price));
                    oldPostKey.add(messageData.getKey());
                    //Collections.reverse(GoodsInfoArrayList);
                }
                oldPostID = oldPostKey.get(0);
                myAdapter = new MyAdapter(GoodsInfoArrayList);
                mRecyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "게시글 불러오기를 실패했어요!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static class FirebaseLoad {
        public String avgPrice;
        public String id;
        public String name;
        public String img;
        public String price;
        public FirebaseLoad(){}
        public FirebaseLoad(String avgPrice, String id, String name, String img, String price){
            this.avgPrice = avgPrice;
            this.id = id;
            this.name = name;
            this.img = img;
            this.price = price;
        }
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("name", name);
            result.put("img", img);
            result.put("price", price);
            result.put("avgPrice", avgPrice);
            return result;
        }
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

    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("태그로 검색합니다.");

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               //CollectionReference citiesRef = db.collection("tags").whereEqualTo("tags", query);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.account) {
            Intent intent = new Intent(getApplicationContext(), MyPage.class);
            startActivity(intent);
        } else if (id == R.id.bug_report) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/jCL2tFMUjyY2feth6"));
            startActivity(intent);
        } else if (id == R.id.logout) {
            auto = getSharedPreferences(PREF_USER_ACCOUNT, Activity.MODE_PRIVATE);
            toEdit = auto.edit();
            toEdit.clear();
            toEdit.commit();
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
