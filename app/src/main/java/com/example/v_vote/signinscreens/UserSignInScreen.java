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
import com.example.v_vote.dashboards.UserDashboard;
import com.example.v_vote.signupscreens.UserSignUpScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignInScreen extends AppCompatActivity {
TextView registerForNew,forgotPassword;
TextInputEditText emailEdittext,passwordEdittext;
AppCompatButton signInButton;
String emailString,passwordString;
FirebaseAuth mAuthForUserSignIn;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in_screen);
        mAuthForUserSignIn=FirebaseAuth.getInstance();
        forgotPassword=findViewById(R.id.forgotPasswordTextview_user_sign_in_screen);
        emailEdittext=findViewById(R.id.emailEdittextSignIn_user_sign_in_screen);
        passwordEdittext=findViewById(R.id.passwordEdittextSignIn_user_sign_in_screen);
        signInButton=findViewById(R.id.signIn_button_user_sign_in_screen);
        registerForNew=findViewById(R.id.signUpTextview_user_sign_in_screen);
        progressBar=findViewById(R.id.progress_circular_user_sign_in_screen);
        registerForNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToUSerSignUpScreen=new Intent(UserSignInScreen.this, UserSignUpScreen.class);
                startActivity(intentToUSerSignUpScreen);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(UserSignInScreen.this);
                emailString=emailEdittext.getText().toString().trim();
                passwordString=passwordEdittext.getText().toString().trim();
                emailPasswordValidation(emailString,passwordString);

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
        mAuthForUserSignIn.signInWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(String.valueOf(UserSignInScreen.this),"Signed in successfully!!!");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intentToUserDashboard=new Intent(UserSignInScreen.this, UserDashboard.class);
                        startActivity(intentToUserDashboard);
                        finishAffinity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserSignInScreen.this,"Sign in Failed!!!",Toast.LENGTH_SHORT).show();
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
}