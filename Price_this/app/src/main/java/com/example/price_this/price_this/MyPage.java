package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPage extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    FirebaseUser userInfo = FirebaseAuth.getInstance().getCurrentUser();
    String userUid = userInfo.getUid();

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


        mReference = FirebaseDatabase.getInstance().getReference().child("test");
        Query search = mReference.orderByChild("userId").equalTo(userUid);
        search.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String data = dataSnapshot.getValue().toString();
                    String[] temp = data.split("\\{");
                    System.out.println("tempppp" + temp[1]);
                    String[] temp2 = data.split(", ");


                    ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();
                    GoodsInfoArrayList.add(new GoodsInfo(temp2[5].substring(temp2[5].indexOf("=")+1), temp2[3].substring(temp2[3].indexOf("=")+1),  temp2[1].substring(temp2[1].indexOf("=")+1)));

                    MyPageQuestion myPageQuestion = new MyPageQuestion(GoodsInfoArrayList);
                    //MyPageAnswer myPageAnswer = new MyPageAnswer(GoodsInfoArrayList);

                    mRecyclerView.setAdapter(myPageQuestion);
                    //mRecyclerView2.setAdapter(myPageAnswer);


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
    }
}
