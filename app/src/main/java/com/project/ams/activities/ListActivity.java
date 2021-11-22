package com.project.ams.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ams.Adapters.CollegeListAdapter;
import com.project.ams.Models.CollegeModel;
import com.project.ams.Models.User;
import com.project.ams.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    public List<CollegeModel> collegeModelList;

    public TextView nameTextView;
    public TextView messageTextView;
    public ProgressBar progressBar;

    public RecyclerView recyclerView;
    public CollegeListAdapter adapter;

    public FirebaseDatabase database;
    public FirebaseAuth auth;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        collegeModelList = new ArrayList<>();

        recyclerView = findViewById(R.id.collegListRecylerView_id);
        adapter = new CollegeListAdapter(collegeModelList, this);

        nameTextView = findViewById(R.id.nameTextView);
        messageTextView = findViewById(R.id.messageTextView_id);
        progressBar = findViewById(R.id.progressBar_list);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Setting the username
        database.getReference().child("Students").child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            User user = new User();
                            user = snapshot.getValue(User.class);

                            nameTextView.setText("Hello " + user.getUsername());
                            messageTextView.setText("Colleges available for you!");
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // Getting college data in an arraylist
        database.getReference().child("Colleges").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                collegeModelList.clear();

                if(snapshot.hasChildren())
                {
                    for(DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        CollegeModel model = new CollegeModel();
                        model = snapshot1.getValue(CollegeModel.class);

                        collegeModelList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Setting up the recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_id:
                auth.signOut();
                startActivity(new Intent(ListActivity.this, CreateAccountActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
