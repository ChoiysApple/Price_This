package com.example.price_this.price_this;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {


    private static final int RC_SIGN_IN = 9001;          // Google Log in result const
    private GoogleSignInClient googleSignInClient;      // GoogleApiClient
    private FirebaseAuth mAuth;                  // Firebase auth object
    private SignInButton btn_googleSignin;               // Sign in button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();         //declare Firebase auth object

        // Integrate google sign in to app
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //Google sign in button
        btn_googleSignin = (SignInButton) findViewById(R.id.btn_googleSignin);
        btn_googleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });//   [END onClickListener]


    }//     [END onCreate]


    public void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // respond to sign in button
        if (requestCode == RC_SIGN_IN) {// Sign in success
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d("SIGNIN", "exception"+e);
                Toast.makeText(getApplicationContext(), "Google Sign in Failed", Toast.LENGTH_LONG).show();
            }
        }
    }//     [END onActivityResult]

    // Get id token from GoogleSignInAccount, auth to firebase
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in successful
                            Toast.makeText(MainActivity.this, "success_login", Toast.LENGTH_SHORT).show();
                        } else {
                            //  sign in failed
                            Toast.makeText(MainActivity.this, "failed_login", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }//    [END firebaseAuthWithGoogle]

}
