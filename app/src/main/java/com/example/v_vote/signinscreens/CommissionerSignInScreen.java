package com.example.v_vote.signinscreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.v_vote.R;
import com.example.v_vote.signupscreens.CommissionerSignUpScreen;

public class CommissionerSignInScreen extends AppCompatActivity {

    TextView signupTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioner_sign_in);
            signupTv=findViewById(R.id.signUpTextview_commissioner_sign_in_screen);
            signupTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentToSignUpScreen=new Intent(CommissionerSignInScreen.this
                    , CommissionerSignUpScreen.class);
                    startActivity(intentToSignUpScreen);
                }
            });
            }
        }