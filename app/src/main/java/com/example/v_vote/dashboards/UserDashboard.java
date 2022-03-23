package com.example.v_vote.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.v_vote.R;
import com.example.v_vote.adapters.ElectionListAdapter;
import com.example.v_vote.helpingclasses.ElectionsListClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {

    FirebaseAuth mAuthUserDashboard;
    FirebaseFirestore mFirestoreUserDashboard;
    String currentUserId;
    List<ElectionsListClass> electionsListClasses;
    RecyclerView recyclerView;
    ElectionListAdapter electionListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        loadElections();
    }
    public void loadElections(){
        recyclerView=findViewById(R.id.recycler_view_user_dashboard);
        electionsListClasses=new ArrayList<>();
        electionListAdapter=new ElectionListAdapter(electionsListClasses,UserDashboard.this);
        RecyclerView.LayoutManager layoutManager=new
                LinearLayoutManager(UserDashboard.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(electionListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        mAuthUserDashboard=FirebaseAuth.getInstance();
        mFirestoreUserDashboard=FirebaseFirestore.getInstance();
        currentUserId=mAuthUserDashboard.getCurrentUser().toString();
        mFirestoreUserDashboard.collection("Elections")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.e(String.valueOf(UserDashboard.this),"Elections Loading Task:Completed.");
            }
        })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshotList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot documentSnapshotSingleItem:documentSnapshotList){
                    ElectionsListClass electionsListClass=documentSnapshotSingleItem.toObject(ElectionsListClass.class);

                }
                Log.e(String.valueOf(UserDashboard.this),"Election Loading Task:Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(String.valueOf(UserDashboard.this),"Election Loading:Failed");
            }
        });
    }
}