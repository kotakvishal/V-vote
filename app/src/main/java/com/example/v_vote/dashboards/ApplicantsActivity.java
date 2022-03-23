package com.example.v_vote.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v_vote.R;
import com.example.v_vote.adapters.ApplicantsListAdapter;
import com.example.v_vote.helpingclasses.ApplicantsListClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ApplicantsActivity extends AppCompatActivity {
    ApplicantsListAdapter applicantsListAdapter;
    RecyclerView recyclerView;
    ArrayList<ApplicantsListClass>applicantsListClasses;
    LinearLayoutManager layoutManager;
    FirebaseAuth firebaseAuthForApplicants;
    FirebaseFirestore firebaseFirestoreForApplicants;
    TextView refreshTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);
        refreshTextview=findViewById(R.id.refresh_applicants_acb_textview);
        refreshTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadApplicantsList();
            }
        });
        loadApplicantsList();
    }
    public void loadApplicantsList(){
        firebaseFirestoreForApplicants=FirebaseFirestore.getInstance();
        applicantsListClasses=new ArrayList<>();
        firebaseAuthForApplicants=FirebaseAuth.getInstance();
        firebaseFirestoreForApplicants.collection("Applicants")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot>documentSnapshotArrayList=queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot documentSnapshot:documentSnapshotArrayList){
                    ApplicantsListClass myApplicants=documentSnapshot.toObject(ApplicantsListClass.class);
                    applicantsListClasses.add(myApplicants);
                    Log.e(String.valueOf(ApplicantsActivity.this),"Successfully Executed");
                }
                applicantsListAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(String.valueOf(ApplicantsActivity.this),e.toString());
            }
        });
        applicantsListAdapter=new ApplicantsListAdapter(applicantsListClasses,ApplicantsActivity.this);
        recyclerView=findViewById(R.id.applicants_list_recyclerview);
        layoutManager=new LinearLayoutManager(ApplicantsActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(applicantsListAdapter);
    }
}