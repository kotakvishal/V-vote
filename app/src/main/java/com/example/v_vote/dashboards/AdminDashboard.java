package com.example.v_vote.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v_vote.R;
import com.example.v_vote.adapters.ActiveUsersListAdapter;
import com.example.v_vote.helpingclasses.ActiveUsersListClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    FirebaseAuth mAuthForActiveUsers;
    FirebaseFirestore mFirestoreForActiveusers;
    List<ActiveUsersListClass> activeUsersListClasses;
    ActiveUsersListAdapter activeUsersListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView notificationTextview;
    TextView refreshButton;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuthForActiveUsers.signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        recyclerView=findViewById(R.id.active_users_list_recyclerview);
        notificationTextview=findViewById(R.id.admin_dashboard_acb_imageview);
        refreshButton=findViewById(R.id.refresh_acb_textview);
        notificationTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToApplicantsActivity=new Intent(AdminDashboard.this,
                        ApplicantsActivity.class);
                startActivity(intentToApplicantsActivity);
            }
        });
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadActiveUsersList();
            }
        });
        loadActiveUsersList();
    }
    public void loadActiveUsersList(){
        mAuthForActiveUsers=FirebaseAuth.getInstance();
        mFirestoreForActiveusers=FirebaseFirestore.getInstance();
        layoutManager= new LinearLayoutManager(AdminDashboard.this,RecyclerView.VERTICAL,false);
        activeUsersListClasses=new ArrayList<>();
        activeUsersListAdapter=new ActiveUsersListAdapter(AdminDashboard.this,activeUsersListClasses);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activeUsersListAdapter);
        mFirestoreForActiveusers.collection("ActiveUsers")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshotList=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot documentSnapshot:documentSnapshotList){
                    ActiveUsersListClass activeUsersListClass=documentSnapshot.toObject(ActiveUsersListClass.class);
                    activeUsersListClasses.add(activeUsersListClass);
                    Log.e(String.valueOf(AdminDashboard.this),"Successfully Executed");
                }
                activeUsersListAdapter.notifyDataSetChanged();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(String.valueOf(AdminDashboard.this),e.toString());
                    }
                });
    }

}