package com.example.v_vote.signinscreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v_vote.R;
import com.example.v_vote.dashboards.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminSignInScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    AppCompatButton signInButton;
    TextInputEditText emailEdittext,passwordEdittext;
    String emailString,passwordString;
    ProgressBar progressBar;
    TextView forgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in_screen);
        mAuth= FirebaseAuth.getInstance();
        forgotPassword=findViewById(R.id.forgotPasswordTextview_admin_sign_in_screen);
        signInButton=findViewById(R.id.signIn_button_admin_sign_in_screen);
        emailEdittext=findViewById(R.id.emailEdittextSignIn_admin_sign_in_screen);
        passwordEdittext=findViewById(R.id.passwordEdittextSignIn_admin_sign_in_screen);
        progressBar=findViewById(R.id.progress_circular_admin_sign_in_screen);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidationAndSendResetLink(emailString);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(AdminSignInScreen.this);
                emailString=emailEdittext.getText().toString().trim();
                passwordString=passwordEdittext.getText().toString().trim();
                emailPasswordValidation(emailString,passwordString);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void emailPasswordValidation(String emailString, String passwordString) {
        if (emailString.length() > 8
                && emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
                && passwordString.length() > 8)
        {
            userSignInWithEmail(emailString,passwordString);
                progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            if(emailString.length()<=8 || !emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                emailEdittext.setError("Enter a valid email");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            if(passwordString.length()<=8){
                passwordEdittext.setError("Enter a valid Password");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
    }
    private void userSignInWithEmail(String emailString, String passwordString) {
        mAuth.signInWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(String.valueOf(AdminSignInScreen.this),"Task:Completed");
                    }
                }).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intentToAdminDashboard=new Intent(AdminSignInScreen.this,AdminDashboard.class);
                        startActivity(intentToAdminDashboard);
                        progressBar.setVisibility(View.INVISIBLE);
                        finishAffinity();
                    }
                }).
                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminSignInScreen.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

    }
    private void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view=activity.getCurrentFocus();
        if(view==null){
            view=new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    private void checkValidationAndSendResetLink(String emailString){
        emailString=emailEdittext.getText().toString().trim();
        if(emailString.length()<8 ||
        !emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
            emailEdittext.setError("Please Enter Valid Email");
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressBar.setVisibility(View.INVISIBLE);

        }
        else{
            mAuth.sendPasswordResetEmail(emailString)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AdminSignInScreen.this,"Password Reset Link Sent Successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminSignInScreen.this,"Failed To Sent The Password Reset Link!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}