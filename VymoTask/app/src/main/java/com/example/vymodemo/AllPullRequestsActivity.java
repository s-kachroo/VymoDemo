package com.example.vymodemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vymodemo.Adapter.PullRequestAdapter;
import com.example.vymodemo.Model.PullRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllPullRequestsActivity extends AppCompatActivity {
    private String repoName, ownerName;
    String URL_OPEN, URL_CLOSED;
    OkHttpClient okHttpClient;
    ArrayList<PullRequest> openPullRequestList;
    ArrayList<PullRequest> closedPullRequestList;
    PullRequestAdapter pullRequestAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pull_requests);

        Button mOpenButton = findViewById(R.id.btn_open);
        Button mCloseButton = findViewById(R.id.btn_close);

        recyclerView = findViewById(R.id.rv_pull_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        openPullRequestList = new ArrayList<>();
        closedPullRequestList = new ArrayList<>();

        okHttpClient = new OkHttpClient();

        /* Get Names of Repo and Owner */
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            repoName = extras.getString("repo_name");
            ownerName = extras.getString("owner_name");
        }
//        Toast.makeText(this, repoName + " " + ownerName, Toast.LENGTH_SHORT).show();

        /* Set URL for open and closed pull requests */
        URL_OPEN = "https://api.github.com/repos/" + ownerName + "/" + repoName + "/issues?state=open";
        URL_CLOSED = "https://api.github.com/repos/" + ownerName + "/" + repoName + "/issues?state=closed";

        mOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fetchDataFromJSON(URL_OPEN, "open");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AllPullRequestsActivity.this, "Error in Data Fetch", Toast.LENGTH_SHORT).show();
                }

                pullRequestAdapter = new PullRequestAdapter(AllPullRequestsActivity.this, openPullRequestList);
                recyclerView.setAdapter(pullRequestAdapter);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fetchDataFromJSON(URL_CLOSED, "closed");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AllPullRequestsActivity.this, "Error in Data Fetch", Toast.LENGTH_SHORT).show();
                }

                pullRequestAdapter = new PullRequestAdapter(AllPullRequestsActivity.this, closedPullRequestList);
                recyclerView.setAdapter(pullRequestAdapter);
            }
        });
    }

    /**
     * Display data fetched from the API
     */
    void fetchDataFromJSON(String URL, final String whichList) {
        final Request request = new Request.Builder()
                .url(URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
                Log.e("TAG", "onFailure: " + call.request().url());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                Log.e("TAG", "onResponse: " + result);

                final GsonBuilder gsonBuilder = new GsonBuilder();
                final Gson gson = gsonBuilder.create();
                final List<PullRequest> pullRequest = gson.fromJson(result, new TypeToken<List<PullRequest>>(){}.getType());

                /*
                    Notify the adapter for data changes on the UI thread
                */
                AllPullRequestsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(whichList.equals("open"))
                            openPullRequestList.addAll(pullRequest);
                        else
                            closedPullRequestList.addAll(pullRequest);

                        pullRequestAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}