package com.example.vymodemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText mGithubOwnerNameEditText = findViewById(R.id.et_owner_name);
        final EditText mGithubRepoNameEditText = findViewById(R.id.et_repo_name);

//        mGithubOwnerNameEditText.setText("prestodb");
//        mGithubRepoNameEditText.setText("presto");

        TextView mSubmitButton = findViewById(R.id.tv_submit);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ownerName = mGithubOwnerNameEditText.getText().toString();
                String repoName = mGithubRepoNameEditText.getText().toString();

                Intent i = new Intent(MainActivity.this, AllPullRequestsActivity.class);
                i.putExtra("owner_name", ownerName);
                i.putExtra("repo_name", repoName);
                startActivity(i);
            }
        });
    }
}