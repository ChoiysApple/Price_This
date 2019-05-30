package com.example.price_this.price_this;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ImageButton imgBtn_productImg;
    Button btn_register;
    EditText editTxt_productName, editTxt_description, editTxt_tag;
    EditText editTxt_spec, editTxt_price;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private Uri filePath;
    StorageReference pathReference;
    String pathImg;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        imgBtn_productImg = findViewById(R.id.imgBtn_productImg);
        btn_register = findViewById(R.id.btn_register);
        editTxt_description = findViewById(R.id.editTxt_description);
        editTxt_productName = findViewById(R.id.editTxt_productName);
        editTxt_tag = findViewById(R.id.editText_tag);
        editTxt_spec = findViewById(R.id.editTxt_spec);
        editTxt_price = findViewById(R.id.editText_priceToRegister);


        final String id = "000000";
        int date = 0;

        imgBtn_productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });


        final ArrayList<String> tags = new ArrayList<>();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = editTxt_description.getText().toString();
                String productName = editTxt_productName.getText().toString();
                String tag = editTxt_tag.getText().toString();
                String spec = editTxt_spec.getText().toString();
                String price = editTxt_price.getText().toString();
                //String img = pathReference.toString();
                if(tag.isEmpty()){
                    Toast.makeText(getApplicationContext(), "상품 태그를 하나 이상 입력해 주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(productName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "상품 이름을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(price.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "상품의 초기 가격을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadFile();
                    String img = pathImg;
                    tag = tag.replaceAll("\\p{Z}", "");
                    tag = tag.replaceAll(" ", "");//공백제거
                    String[] tagss = tag.split("#");
                    for(int i=0; i<tagss.length; i++){
                        tags.add(tagss[i]);
                    }
                    FirebasePost post = new FirebasePost(id, productName, img, description, tags, spec, price);
                    databaseReference.child("test").push().setValue(post.toMap());
                    finish();
                }
            }
        });
    }

    public class FirebasePost {
        public String id;
        public String name;
        public String img;
        public String desc;
        public ArrayList tags;
        public String spec;
        public String price;
        public FirebasePost(){}
        public FirebasePost(String id, String name, String img, String desc, ArrayList tags, String spec, String price){
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            //Log.d(TAG, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgBtn_productImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the image file
    private void uploadFile() {
        String filename = "";
        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://price-this.appspot.com").child(filename);
            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "등록 완료!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "등록 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://price-this.appspot.com");
        StorageReference storageRef = storage.getReference();
        pathReference = storageRef.child(filename);
        pathImg = filename;
    }

}
