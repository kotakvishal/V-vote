package com.example.v_vote.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v_vote.R;
import com.example.v_vote.signinscreens.CommissionerSignInScreen;
import com.example.v_vote.signupscreens.CommissionerSignUpScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ApplicantsDetailActivity extends AppCompatActivity {
    FirebaseAuth mFirebaseAuthForActiveUsers;
    FirebaseFirestore mFirebaseFirestoreforActiveUsers;
    TextView commissionnameTv,commissionerNameTv,emailTv,phoneTv,aadharNumberTv;
    AppCompatButton approveButton;
    String commissionerName,commissionsName,aadharNumber,phoneNumber,email,userId;
    String messageToMail,subjectToMail,uniqueIdForLogIn,currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants_detail);
        commissionerNameTv=findViewById(R.id.commissionerNameTextview);
        commissionnameTv=findViewById(R.id.commissionsNameTextview);
        emailTv=findViewById(R.id.email_textview);
        phoneTv=findViewById(R.id.phoneNumberTextview);
        aadharNumberTv=findViewById(R.id.aadhar_textview);
        approveButton=findViewById(R.id.approve_button_applicants_details_screen);
        mFirebaseAuthForActiveUsers=FirebaseAuth.getInstance();
        mFirebaseFirestoreforActiveUsers=FirebaseFirestore.getInstance();
        commissionsName=getIntent().getStringExtra("commissionsName");
        commissionerName=getIntent().getStringExtra("commissionersName");
        aadharNumber=getIntent().getStringExtra("aadharNumber");
        phoneNumber=getIntent().getStringExtra("phoneNumber");
        email=getIntent().getStringExtra("email");
        userId=getIntent().getStringExtra("userId");
        commissionnameTv.setText(commissionsName);
        commissionerNameTv.setText(commissionerName);
        emailTv.setText(email);
        phoneTv.setText(phoneNumber);
        aadharNumberTv.setText(aadharNumber);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailForIdPassword();
            }
        });
    }
    private void sendEmailForIdPassword() {

        uniqueIdForLogIn=phoneNumber+"@vvoteCommission";
        subjectToMail="Approval to access commission panel of your V-vote account.";
        messageToMail="Hello,"+commissionerName+"\n"
                +"We are glad to inform you that your application to access the commission panel " +
                "of your V-vote account for your organisation "+commissionsName+
                " has been approved.\n Kindly find your unique id below. \n"+
                "Unique Id: "+uniqueIdForLogIn;
        SendMail sendMail=new SendMail(ApplicantsDetailActivity.this,
                email,subjectToMail,messageToMail);
        sendMail.execute();
        addUserToActiveUsersList();
    }
    private void addUserToActiveUsersList(){

        currentUserId=mFirebaseAuthForActiveUsers.getCurrentUser().getUid();
        DocumentReference documentReferenceForActiveUsers=mFirebaseFirestoreforActiveUsers
                .collection("ActiveUsers").document(userId);
        Map<String,Object> activeUsers=new HashMap<>();
        activeUsers.put("commissionsName",commissionsName);
        activeUsers.put("commissionersName",commissionerName);
        activeUsers.put("phoneNumber",phoneNumber);
        activeUsers.put("email",email);
        activeUsers.put("aadharNumber",aadharNumber);
        documentReferenceForActiveUsers.set(activeUsers)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e(String.valueOf(ApplicantsDetailActivity.this)
                        ,"Commissioner Added Successfully!!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(String.valueOf(ApplicantsDetailActivity.this
                ), "Failed to add the commissioner");
            }
        })
        ;
    }

}