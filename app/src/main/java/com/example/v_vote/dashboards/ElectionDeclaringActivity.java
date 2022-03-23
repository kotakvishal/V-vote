package com.example.v_vote.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.v_vote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ElectionDeclaringActivity extends AppCompatActivity {

    String electionTitleString ,candidate1String,
            candidate2String,electionDateString,resultsDateString,currentUserId;
    TextInputEditText electionTitle,candidate1
            ,candidate2,electionDate,resultsDate;
    FirebaseAuth mFirebaseAuthForElectionDeclaration;
    AppCompatButton submitButton;
    FirebaseFirestore mFirestoreForElectionDeclaration;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_declaring);
        electionTitle=findViewById(R.id.title_et_cdb);
        electionDate=findViewById(R.id.elecctionDate_et_cdb);
        candidate1=findViewById(R.id.candidate_1_et_cdb);
        candidate2=findViewById(R.id.candidate_2_et_cdb);
        resultsDate=findViewById(R.id.results_et_cdb);
        submitButton=findViewById(R.id.submit_button_db);
        progressBar=findViewById(R.id.progress_bar_db);
        mFirebaseAuthForElectionDeclaration=FirebaseAuth.getInstance();
        mFirestoreForElectionDeclaration=FirebaseFirestore.getInstance();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(ElectionDeclaringActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                electionTitleString=electionTitle.getText().toString().trim();
                electionDateString=electionDate.getText().toString().trim();
                candidate1String=candidate1.getText().toString().trim();
                candidate2String=candidate2.getText().toString().trim();
                resultsDateString=resultsDate.getText().toString().trim();
                checkValidations(electionTitleString,candidate1String,candidate2String,electionDateString,resultsDateString);
            }
        });
    }

    private void checkValidations(String electionTitleString, String candidate1String,
                                  String candidate2String, String electionDateString,String resultsDateString) {
        if (electionTitleString.length()>4
                || candidate1String.length()>4
                || candidate2String.length()>4
                || electionDateString.length()>4
                ||resultsDateString.length()>4) {
            uploadToCollections();
        }
        else{
            if(electionTitleString.length()<4){
                electionTitle.setError("Please Enter a valid title");
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(electionDateString.length()<4){
                electionDate.setError("Please Enter a valid date ");
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(candidate1String.length()<4){
                candidate1.setError("Please Enter a valid candidate name");
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(candidate2String.length()<4){
                candidate2.setError("Please Enter a valid candidate name");
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
            if(resultsDateString.length()<4){
                resultsDate.setError("Please Enter a valid date");
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        }
    }

    private void uploadToCollections() {
        currentUserId=mFirebaseAuthForElectionDeclaration.getCurrentUser().getUid();
        DocumentReference documentReference=mFirestoreForElectionDeclaration.collection("ElectionsList")
                .document(currentUserId);
        Map<String,Object> electionsList=new HashMap<>();
        electionsList.put("electionTitle",electionTitleString);
        electionsList.put("electionDate",electionDateString);
        electionsList.put("candidate1",candidate1String);
        electionsList.put("candidate2",candidate2String);
        electionsList.put("resultsDate",resultsDateString);
        documentReference.set(electionsList)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ElectionDeclaringActivity.this,
                                "Election Delcared Successfully!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ElectionDeclaringActivity.this,
                                "Election Declaration Failed!",Toast.LENGTH_SHORT).show();
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