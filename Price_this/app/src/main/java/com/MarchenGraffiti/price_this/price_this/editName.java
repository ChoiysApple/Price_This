package com.MarchenGraffiti.price_this.price_this;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class editName extends AppCompatActivity {

    private Button modity_btn;
    private EditText targetName;
    FirebaseUser user;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_modify);

        targetName = (EditText) findViewById(R.id.editTxt_name);

        modity_btn = (Button) findViewById(R.id.btn_modifyName);
        modity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                try {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(targetName.getText().toString())
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("edit", "new name: " + user.getDisplayName());
                                        Toast.makeText(getApplicationContext(), "앱을 재시작해 주세요", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
