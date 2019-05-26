package com.example.price_this.price_this;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    Button btn_register;
    EditText editTxt_productName, editTxt_description, editTxt_tag;
    EditText editTxt_spec, editTxt_price;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.app_banner));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        btn_register = findViewById(R.id.btn_register);
        editTxt_description = findViewById(R.id.editTxt_description);
        editTxt_productName = findViewById(R.id.editTxt_productName);
        editTxt_tag = findViewById(R.id.editText_tag);
        editTxt_spec = findViewById(R.id.editTxt_spec);
        editTxt_price = findViewById(R.id.editText_priceToRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
