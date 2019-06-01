package com.example.price_this.price_this;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class signIn extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private ImageButton sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);


        sign_in = (ImageButton) findViewById(R.id.btn_signin);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

    }//     [END onCreate]

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(signIn.this,"success_login", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(signIn.this,MainApp.class);
                            startActivity(intent);

                        } else {
                            // 로그인 실패
                            Toast.makeText(signIn.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
