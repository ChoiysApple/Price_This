package com.MarchenGraffiti.price_this.price_this;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    SharedPreferences auto;
    SharedPreferences.Editor toEdit;
    static final String PREF_USER_ACCOUNT = "account";
    String saved_email, saved_password;

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
                try {
                    saved_email = mEmailField.getText().toString();
                    saved_password = mPasswordField.getText().toString();
                    loginUser(mEmailField.getText().toString(), mPasswordField.getText().toString());
                    Toast.makeText(signIn.this, "로그인 중입니다...", Toast.LENGTH_SHORT).show();
                }catch (IllegalArgumentException e){
                    Toast.makeText(signIn.this, "이메일, 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        autoSignin();

    }//     [END onCreate]

    private void loginUser(String email, String password) {
        if (mEmailField.getText().toString() == null || mPasswordField.getText().toString() == null)
            return;
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            saveAccountInfo(saved_email, saved_password);
                            Toast.makeText(signIn.this, "환영합니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signIn.this, MainApp.class);
                            startActivity(intent);

                            finish();
                        } else {
                            // 로그인 실패
                            Toast.makeText(signIn.this, "올바르지 않은 패스워드 또는 이메일입니다!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveAccountInfo(String email, String password) {
        auto = getSharedPreferences(PREF_USER_ACCOUNT, Activity.MODE_PRIVATE);
        toEdit = auto.edit();
        toEdit.putString("saved_email", email);
        toEdit.putString("saved_password", password);
        toEdit.commit();
    }

    private void autoSignin() {
        auto = getSharedPreferences(PREF_USER_ACCOUNT, MODE_PRIVATE);
        if (auto != null && auto.contains("saved_email")&& auto.contains("saved_password")) {
            Toast.makeText(getApplicationContext(), "자동 로그인 중입니다", Toast.LENGTH_LONG).show();
            String email = auto.getString("saved_email", "noname");
            String password = auto.getString("saved_password", "noname");
            this.loginUser(email, password);
        }

    }

}
