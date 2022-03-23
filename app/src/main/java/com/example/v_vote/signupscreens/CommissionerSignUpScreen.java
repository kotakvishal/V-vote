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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.v_vote.R;
import com.example.v_vote.signinscreens.CommissionerSignInScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class CommissionerSignUpScreen extends AppCompatActivity {
TextInputEditText commissionName,commissionerName,email,password,phoneNumber,aadharNumber;
String commissionNameString,passwordString,commissionersNameString,
        emailString,phoneNumberString,aadharNumberString,currentUserId;
AppCompatButton submitButton;
FirebaseAuth mAuthForNewCommission;
FirebaseFirestore mFirestoreForApplication;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioner_sign_up_screen);
            commissionName=findViewById(R.id.commissionsNameEdittextSignIn_commissioner_sign_in_screen);
            commissionerName=findViewById(R.id.CommissionersNameEdittextSignIn_commissioner_sign_in_screen);
            email=findViewById(R.id.emailEdittextSignIn_commissioner_sign_up_screen);
            password=findViewById(R.id.passwordEdittextSignIn_commissioner_sign_up_screen);
            phoneNumber=findViewById(R.id.phoneNoEdittextSignIn_commissioner_sign_up_screen);
            aadharNumber=findViewById(R.id.aadharNoEdittextSignIn_commissioner_sign_up_screen);
            submitButton=findViewById(R.id.submitButton_commissioner_sign_up_screen);
            progressBar=findViewById(R.id.progress_bar_commissioner_sign_up_screen);
            mAuthForNewCommission=FirebaseAuth.getInstance();
            mFirestoreForApplication=FirebaseFirestore.getInstance();
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    hideSoftKeyboard(CommissionerSignUpScreen.this);
                    commissionersNameString=commissionerName.getText().toString().trim();
                    commissionNameString=commissionName.getText().toString().trim();
                    emailString=email.getText().toString().trim();
                    passwordString=password.getText().toString();
                    aadharNumberString=aadharNumber.getText().toString().trim();
                    phoneNumberString=phoneNumber.getText().toString().trim();
                    checkValidationsAndApply(commissionersNameString,commissionNameString,
                            phoneNumberString,aadharNumberString,emailString,passwordString);
                }
            });
    }
    private void checkValidationsAndApply(String commissionersNameString,String commissionNameString,
                                          String phoneNumberString,String aadharNumberString,
                                          String emailString, String passwordString) {
        if(emailString.length()>8
                && passwordString.length()>8
                && commissionersNameString.length()>3
                && commissionNameString.length()>3
                && phoneNumberString.length()==10
                && aadharNumberString.length()==12
                && emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
        {
            mAuthForNewCommission.createUserWithEmailAndPassword(emailString,passwordString)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressBar.setVisibility(View.INVISIBLE);
                                        submitApplication(commissionersNameString,commissionNameString,phoneNumberString,
                    aadharNumberString,emailString,passwordString);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CommissionerSignUpScreen.this,
                            "Failed To Sign In. Try Again",Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            if(emailString.length()<8){
                email.setError("Please Enter a Valid Email");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(!emailString.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")){
                email.setError("Please Enter a Valid Email");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(commissionersNameString.length()<3){
                commissionerName.setError("Commissioner Name Should Be Valid");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if(commissionNameString.length()<3){
                commissionName.setError("Commission Name Should Be Valid");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(phoneNumberString.length()!=10){
                phoneNumber.setError("Please Enter a Valid Phone Number");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(aadharNumberString.length()!=12){
                aadharNumber.setError("Aadhar Number Should Be Valid");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
            if (passwordString.length()<8){
                password.setError(" Enter a valid Password");
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        }
    }
    private void submitApplication(String commissionersNameString,String commissionNameString,String phoneNumberString,
                                   String aadharNumberString,String emailString,String passwordString) {
        currentUserId=mAuthForNewCommission.getCurrentUser().getUid();
        DocumentReference documentReferenceForApplicants=mFirestoreForApplication.collection("Applicants")
                .document(currentUserId);
        Log.e(String.valueOf(CommissionerSignUpScreen.this),currentUserId.toString());
        Map<String,Object> applicantsList=new HashMap<>();
        applicantsList.put("commissionsName",commissionNameString);
        applicantsList.put("commissionersName",commissionersNameString);
        applicantsList.put("phoneNumber",phoneNumberString);
        applicantsList.put("email",emailString);
        applicantsList.put("aadharNumber",aadharNumberString);
        applicantsList.put("UserId",currentUserId);
        documentReferenceForApplicants.set(applicantsList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CommissionerSignUpScreen.this,
                                "Applied successfully,LogIn With Your Id.",Toast.LENGTH_LONG).show();
                        Intent intentToSignInScreen=new Intent(CommissionerSignUpScreen.this, CommissionerSignInScreen.class);
                        finishAffinity();
                        startActivity(intentToSignInScreen);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CommissionerSignUpScreen.this,
                                "Failed To Apply,Login And apply again!!!",Toast.LENGTH_SHORT).show();
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