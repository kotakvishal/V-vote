package com.example.v_vote.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v_vote.R;
import com.example.v_vote.dashboards.ApplicantsActivity;
import com.example.v_vote.dashboards.ApplicantsDetailActivity;
import com.example.v_vote.helpingclasses.ApplicantsListClass;

import java.util.List;

public class ApplicantsListAdapter extends RecyclerView.Adapter<ApplicantsListAdapter.ApplicantsViewHolder> {
    public ApplicantsListAdapter(List<ApplicantsListClass> applicantsListClasses, Context context) {
        this.applicantsListClasses = applicantsListClasses;
        this.context = context;
    }


    List<ApplicantsListClass> applicantsListClasses;
    Context context;
    @NonNull
    @Override
    public ApplicantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ApplicantsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.active_users_list_signle_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.applicantsTextview.setText(applicantsListClasses.get(position).getCommissionsName());
        holder.applicantsTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToApplicantsDetailActivity=new Intent(holder
                .applicantsTextview.getContext(), ApplicantsDetailActivity.class);
                intentToApplicantsDetailActivity.putExtra("commissionsName",
                        applicantsListClasses.get(position).getCommissionsName());
                intentToApplicantsDetailActivity.putExtra("commissionersName",
                        applicantsListClasses.get(position).getCommissionersName());
                intentToApplicantsDetailActivity.putExtra("phoneNumber",
                        applicantsListClasses.get(position).getPhoneNumber());
                intentToApplicantsDetailActivity.putExtra("email",
                        applicantsListClasses.get(position).getEmail());
                intentToApplicantsDetailActivity.putExtra("aadharNumber",
                        applicantsListClasses.get(position).getAadharNumber());
                intentToApplicantsDetailActivity.putExtra("userId",
                        applicantsListClasses.get(position).getUserId());
                intentToApplicantsDetailActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.applicantsTextview.getContext().startActivity(intentToApplicantsDetailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicantsListClasses.size();
    }

    public static class ApplicantsViewHolder extends RecyclerView.ViewHolder {
        TextView applicantsTextview;
        public ApplicantsViewHolder(@NonNull View itemView) {
            super(itemView);
            applicantsTextview=itemView.findViewById(R.id.active_users_list_user_name);
        }
    }
}
