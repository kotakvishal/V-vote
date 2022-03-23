package com.example.v_vote.dashboards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.v_vote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CommissionerDashboard extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commissioner_dashboard);
        floatingActionButton=findViewById(R.id.floating_acb_commissioner_dashboard);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToElectionDeclaringActivity=new Intent(CommissionerDashboard.this
                ,ElectionDeclaringActivity.class);
                startActivity(intentToElectionDeclaringActivity);
            }
        });
    }
}