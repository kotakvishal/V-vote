package com.example.v_vote;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentUser= FirebaseAuth.getInstance().getCurrentUser();
                if(currentUser!=null){
                    Log.e(String.valueOf(Splash.this), currentUser.getEmail().toString());
                }
                Intent intentToSignUpScreen=new Intent(Splash.this, AuthenticationGatewayScreen.class);
                finish();
                startActivity(intentToSignUpScreen);

            }
        },3000);

    }
}