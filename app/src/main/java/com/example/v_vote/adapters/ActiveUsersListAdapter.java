package com.example.v_vote.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v_vote.R;
import com.example.v_vote.helpingclasses.ActiveUsersListClass;

import java.util.List;

public class ActiveUsersListAdapter extends RecyclerView.Adapter<ActiveUsersListAdapter.ActiveUsersListViewHolder> {
    Context context;
    List<ActiveUsersListClass>activeUsersListClassList;

    public ActiveUsersListAdapter(Context context, List<ActiveUsersListClass> activeUsersListClassList) {
        this.context = context;
        this.activeUsersListClassList = activeUsersListClassList;
    }

    @NonNull
    @Override
    public ActiveUsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveUsersListViewHolder(LayoutInflater.from(context).inflate(R.layout.active_users_list_signle_item,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveUsersListViewHolder holder, int position) {
        holder.userNameTextview.setText(activeUsersListClassList.get(position).getCommissionersName());
    }

    @Override
    public int getItemCount() {
        return activeUsersListClassList.size();
    }

    public class ActiveUsersListViewHolder extends RecyclerView.ViewHolder {
        TextView userNameTextview;
        public ActiveUsersListViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextview=itemView.findViewById(R.id.active_users_list_user_name);
        }
    }
}
