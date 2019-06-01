package com.example.price_this.price_this;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product extends AppCompatActivity {
    TextView txtView_productName;
    TextView txtView_avgPrice, txtView_regPrice, txtView_userPrice;
    EditText editTxt_priceToRegister;
    TextView txtView_goodsTag;
    TextView txtView_spec, txtView_desc;
    ImageView imgView_productImg;
    Button btn_register;
    String id;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        txtView_productName = findViewById(R.id.txtView_productName);
        txtView_avgPrice = findViewById(R.id.txtView_avgPrice);
        txtView_regPrice = findViewById(R.id.txtView_regPrice);
        txtView_userPrice = findViewById(R.id.txtView_userPrice);
        editTxt_priceToRegister = findViewById(R.id.editTxt_priceToRegister);
        txtView_goodsTag = findViewById(R.id.txtView_goodsTag);
        txtView_spec = findViewById(R.id.txtView_spec);
        txtView_desc = findViewById(R.id.txtVIew_desc);

        imgView_productImg = findViewById(R.id.imgView_productImg);
        btn_register = findViewById(R.id.btn_register);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        mReference = FirebaseDatabase.getInstance().getReference().child("test").child(id);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    FirebaseLoad data = dataSnapshot.getValue(FirebaseLoad.class);
                    FirebaseStorage storage = FirebaseStorage.getInstance("gs://price-this.appspot.com/");
                    StorageReference storageRef = storage.getReference();
                    StorageReference pathReference = storageRef.child(data.img);

                    pathReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            Bitmap result = Bitmap.createScaledBitmap(bitmap, 1000, 1000, false);
                            imgView_productImg.setImageBitmap(result);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //txtViewGoodsName.setText(String.format("Failure: %s", exception.getMessage()));
                        }
                    });
                    txtView_productName.setText(data.name);
                    txtView_regPrice.setText(data.price);
                    txtView_desc.setText(data.description);
                    txtView_spec.setText(data.spec);

                    //태그띄우기
                    ArrayList<String> tags = data.tags;
                    if(tags!=null){
                        for(int i=1; i<tags.size();i++) {
                            txtView_goodsTag.append(tags.get(i));
                            if (i != tags.size()-1){
                                txtView_goodsTag.append(", ");
                            }
                        }
                    }

                    ArrayList<String> userPrice = data.userPrice;
                    if(userPrice!=null){
                        for(int i=1; i<userPrice.size();i++) {
                            if(userPrice.get(i) == null)
                                continue;
                            txtView_userPrice.append("user: " + userPrice.get(i) + "\n");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mReference = FirebaseDatabase.getInstance().getReference().child("test").child(id);
                mReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String user = editTxt_priceToRegister.getText().toString();
                            FirebaseLoad data = dataSnapshot.getValue(FirebaseLoad.class);
                            System.out.println("ddddddddd" + user);
                            //ArrayList<String> userPrice = data.userPrice;
                            data.userPrice.add(user);
                            mReference.child("userPrice").setValue(data.userPrice);
                            txtView_userPrice.append("user: " + user +"\n");
                            Toast.makeText(getApplicationContext(), "가격 추천 완료", Toast.LENGTH_LONG);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //finish();
            }
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
        public ArrayList<String> userPrice;

        public FirebaseLoad() {

        }
        public FirebaseLoad(String id, String name, String img, String desc, ArrayList tags, String spec, String price, ArrayList userPrice){
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