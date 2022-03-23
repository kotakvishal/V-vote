package com.example.v_vote.signupscreens;

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
import android.widget.Toast;

import com.example.v_vote.R;
import com.example.v_vote.dashboards.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserSignUpScreen extends AppCompatActivity {
    FirebaseAuth mAuthForUserSignIn;
    TextInputEditText firstName,lastName,phoneNumber,email,password;
    String firstNameString,lastNameString,phoneNumberString,emailString,passwordString;
    AppCompatButton signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthForUserSignIn=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user_sign_up_screen);
        firstName=findViewById(R.id.firstNameEdittextSignIn_user_sign_up_screen);
        lastName=findViewById(R.id.lastNameEdittextSignIn_user_sign_up_screen);
        phoneNumber=findViewById(R.id.phoneNoEdittextSignIn_user_sign_up_screen);
        email=findViewById(R.id.emailEdittextSignIn_user_sign_up_screen);
        password=findViewById(R.id.passwordEdittextSignIn_user_sign_up_screen);
        signUpButton=findViewById(R.id.signUpButton_user_sign_up_screen);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(UserSignUpScreen.this);
                firstNameString=firstName.getText().toString().trim();
                lastNameString=lastName.getText().toString().trim();
                phoneNumberString=phoneNumber.getText().toString().trim();
                emailString=email.getText().toString().trim();
                passwordString=password.getText().toString().trim();
                checkValidations(firstNameString,lastNameString,
                        phoneNumberString,emailString,passwordString);
            }
        });
    }

    private void checkValidations(String firstNameString, String lastNameString,
                                  String phoneNumberString, String emailString, String passwordString) {
        if(firstNameString.length()>3
                && lastNameString.length()>3
                && phoneNumberString.length()==10
                && emailString.length()>8
                && emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")
                && passwordString.length()>8){
            createUserWithDetails(emailString,passwordString);
        }
        else{
            if(firstNameString.length()<3){
                firstName.setError("First Name Should Be Valid");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(lastNameString.length()<3){
                lastName.setError("Last Name Should Be Valid");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(phoneNumberString.length()<10){
                phoneNumber.setError("Please Enter A Valid Phone Number");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(emailString.length()<8 ||!emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") ){
                email.setError("Please Enter A Valid Email");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(passwordString.length()<8){
                password.setError("Please Enter A Valid Password");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }
    private void createUserWithDetails(String emailString, String passwordString) {
        mAuthForUserSignIn.createUserWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(String.valueOf(UserSignUpScreen.this),"Signed up successfully!!!");
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intentToUserDashboard=new Intent(UserSignUpScreen.this, UserDashboard.class);
                        startActivity(intentToUserDashboard);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserSignUpScreen.this,"Sign up Failed!!!",Toast.LENGTH_SHORT).show();
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