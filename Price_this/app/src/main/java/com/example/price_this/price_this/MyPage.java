package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

        final ArrayList<GoodsInfo> GoodsInfoArrayList = new ArrayList<>();


        mReference = FirebaseDatabase.getInstance().getReference().child("test");
        Query search = mReference.orderByChild("userId").equalTo(userUid);
        search.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // child 내에 있는 데이터만큼 반복합니다.
                String data = dataSnapshot.getValue().toString();
                String[] temp2 = data.split(", ");

                GoodsInfoArrayList.add(new GoodsInfo(temp2[5].substring(temp2[5].indexOf("=")+1), temp2[3].substring(temp2[3].indexOf("=")+1),  temp2[1].substring(temp2[1].indexOf("=")+1)));

                MyPageQuestion myPageQuestion = new MyPageQuestion(GoodsInfoArrayList);
                //MyPageAnswer myPageAnswer = new MyPageAnswer(GoodsInfoArrayList);

                mRecyclerView.setAdapter(myPageQuestion);
                //mRecyclerView2.setAdapter(myPageAnswer);

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
        });

    }
}
