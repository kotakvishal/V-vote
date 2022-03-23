package com.example.v_vote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.v_vote.signinscreens.AdminSignInScreen;
import com.example.v_vote.signinscreens.CommissionerSignInScreen;
import com.example.v_vote.signinscreens.UserSignInScreen;

public class AuthenticationGatewayScreen extends AppCompatActivity {
AppCompatButton adminButton,commissionButton,userButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication_gateway_screen);
        adminButton=findViewById(R.id.admin_button_auth_gateway_screen);
        commissionButton=findViewById(R.id.commission_button_auth_gateway_screen);
        userButton=findViewById(R.id.user_button_auth_gateway_screen);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAdminSignIn=new Intent(AuthenticationGatewayScreen.this,
                        AdminSignInScreen.class);
                startActivity(intentToAdminSignIn);
            }
        });
        commissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCommisionerScreen=new Intent(AuthenticationGatewayScreen.this,
                        CommissionerSignInScreen.class);
                startActivity(intentToCommisionerScreen);
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToUserSignInScreen=new Intent(AuthenticationGatewayScreen.this,
                        UserSignInScreen.class);
                startActivity(intentToUserSignInScreen);
            }
        });
    }
}