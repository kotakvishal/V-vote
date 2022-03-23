package com.example.v_vote.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v_vote.R;
import com.example.v_vote.helpingclasses.ElectionsListClass;

import java.util.List;

public class ElectionListAdapter extends RecyclerView.Adapter<ElectionListAdapter.ElectionsViewHolder> {
    List<ElectionsListClass> electionsListClassList;
    Context context;
    @NonNull
    @Override
    public ElectionListAdapter.ElectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ElectionsViewHolder(LayoutInflater.from(context).
                inflate(R.layout.elections_single_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ElectionListAdapter.ElectionsViewHolder holder, int position) {
        holder.electionTitleTextview.setText(electionsListClassList.get(position).getElectionTitle());
    }

    public ElectionListAdapter(List<ElectionsListClass> electionsListClassList, Context context) {
        this.electionsListClassList = electionsListClassList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return electionsListClassList.size();
    }

    public class ElectionsViewHolder extends RecyclerView.ViewHolder {
        TextView electionTitleTextview;
        public ElectionsViewHolder(@NonNull View itemView) {
            super(itemView);
            electionTitleTextview=itemView.findViewById(R.id.electionTitle);
        }
    }
}
