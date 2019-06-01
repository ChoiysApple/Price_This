package com.example.price_this.price_this;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mUsernameField;
    private ImageButton sign_up;
    private ImageButton goto_sign_in;
    private TextView account_guide;
    private static final String TAG = "EmailPassword";
    static final String PREF_USER_ACCOUNT = "account";
    SharedPreferences auto;
    SharedPreferences.Editor toEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mEmailField = findViewById(R.id.field_email);
        mPasswordField = findViewById(R.id.field_password);
        mUsernameField = findViewById(R.id.field_username);
        account_guide = findViewById(R.id.account_guide);

        sign_up = (ImageButton) findViewById(R.id.btn_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        goto_sign_in = (ImageButton) findViewById(R.id.btn_gotoSignin);
        goto_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,signIn.class);
                startActivity(intent);
                finish();
            }
        });

        autoSignin();


    }//     [END onCreate]

    private void createUser(String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "SIgn up succesful", Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(MainActivity.this,signIn.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            if(password.length() < 6)
                                account_guide.setTextColor(Color.RED);
                            else{
                                account_guide.setTextColor(Color.RED);
                                account_guide.setText("@string/email_condition");
                            }


                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // [END_EXCLUDE]
                    }
                });
    }

    private void autoSignin() {
        auto = getSharedPreferences(PREF_USER_ACCOUNT, Activity.MODE_PRIVATE);
        toEdit = auto.edit();
        auto = getSharedPreferences(PREF_USER_ACCOUNT, MODE_PRIVATE);
        if (auto != null && auto.contains("saved_email")&& auto.contains("saved_password")) {
            String email = auto.getString("saved_email", "noname");
            String password = auto.getString("saved_password", "noname");
            Intent intent=new Intent(MainActivity.this,signIn.class);
            startActivity(intent);
        }

    }

    public void sendEmailVerificationWithContinueUrl() {
        // [START send_email_verification_with_continue_url]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String url = "http://www.example.com/verify?uid=" + user.getUid();
        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl(url)
                .setIOSBundleId("com.example.ios")
                // The default for this is populated with the current android package name.
                .setAndroidPackageName("com.example.android", false, null)
                .build();

        user.sendEmailVerification(actionCodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }








}