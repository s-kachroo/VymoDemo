package com.example.vymodemo.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vymodemo.Model.PullRequest;
import com.example.vymodemo.R;

import java.util.ArrayList;


public class PullRequestAdapter extends RecyclerView.Adapter<PullRequestAdapter.MyHolder> {
    private Context context;
    private ArrayList<PullRequest> pullRequestModelArrayList;

    public PullRequestAdapter(Context context, ArrayList<PullRequest> pullRequestModelArrayList) {
        this.context = context;
        this.pullRequestModelArrayList = pullRequestModelArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.single_pull_request_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        PullRequest prm = pullRequestModelArrayList.get(position);

        holder.pullTitle.setText("Pull Title : " + prm.getTitle());
        holder.pullNumber.setText("Pull Number : " + prm.getNumber());
        holder.pullStatus.setText("Pull Status : " + prm.getState());
        holder.pullCreated.setText("Created at : " + prm.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return pullRequestModelArrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView pullTitle, pullNumber, pullStatus, pullCreated;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            pullTitle = itemView.findViewById(R.id.tv_pull_title);
            pullNumber = itemView.findViewById(R.id.tv_pull_number);
            pullStatus = itemView.findViewById(R.id.tv_pull_status);
            pullCreated = itemView.findViewById(R.id.tv_pull_created);
        }
    }
}
