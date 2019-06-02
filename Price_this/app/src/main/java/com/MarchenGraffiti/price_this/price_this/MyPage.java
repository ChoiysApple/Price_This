package com.MarchenGraffiti.price_this.price_this;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPage extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.LayoutManager mLayoutManager;

    TextView txtView_email;
    TextView txtView_name;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userUid = user.getUid();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        setContentView(R.layout.mypage);

        String name = "";
        String email = "";
        Uri photoUrl;

        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
        }

        txtView_email = findViewById(R.id.txtView_email);
        txtView_name = findViewById(R.id.txtView_name);
        txtView_email.setText(email);
        txtView_name.setText(name);

        //리사이클러뷰 설정
        mRecyclerView = findViewById(R.id.mypage_question);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView2 = findViewById(R.id.mypage_answer);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView2.setLayoutManager(mLayoutManager);

        final ArrayList<GoodsInfo> GoodsInfoArrayListQ = new ArrayList<>();
        final ArrayList<GoodsInfo> GoodsInfoArrayListA = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference().child("test");
        Query searchQ = mReference.orderByChild("userId").equalTo(userUid);
        searchQ.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // child 내에 있는 데이터만큼 반복합니다.
                FirebaseLoad data = dataSnapshot.getValue(FirebaseLoad.class);
                GoodsInfoArrayListQ.add(new GoodsInfo(data.id, data.name, data.price));
                MyPageQuestion myPageQuestion = new MyPageQuestion(GoodsInfoArrayListQ);
                mRecyclerView.setAdapter(myPageQuestion);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        Query searchA = mReference.orderByChild("userPrice").startAt(userUid);
        searchA.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseLoad data = dataSnapshot.getValue(FirebaseLoad.class);
                //HashMap<String, String> user = data.userPrice;
                if(data.userPrice.get(userUid) != null)
                    GoodsInfoArrayListA.add(new GoodsInfo(data.id, data.name, data.price));
                MyPageAnswer myPageAnswer = new MyPageAnswer(GoodsInfoArrayListA);
                mRecyclerView2.setAdapter(myPageAnswer);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
    public static class FirebaseLoad {
        public String id;
        public String name;
        public String img;
        public String description;
        public ArrayList<String> tags;
        public String spec;
        public String price;
        public HashMap<String, String> userPrice;

        public FirebaseLoad() {

        }
        public FirebaseLoad(String id, String name, String img, String desc, ArrayList tags, String spec, String price, HashMap userPrice){
            this.id = id;
            this.name = name;
            this.img = img;
            this.description = desc;
            this.tags = tags;
            this.spec = spec;
            this.price = price;
            this.userPrice = userPrice;
        }
    }
}
