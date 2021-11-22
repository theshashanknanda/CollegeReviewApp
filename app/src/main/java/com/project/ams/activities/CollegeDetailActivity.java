package com.project.ams.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ams.Adapters.ReviewAdapter;
import com.project.ams.Models.ReviewModel;
import com.project.ams.Models.User;
import com.project.ams.R;

import java.util.ArrayList;
import java.util.List;

public class CollegeDetailActivity extends AppCompatActivity {

    public TextView collegeName, location, emailTextView;
    public Button reviewButton, minimumMarksButton, averageRatingButton;
    public Button totalReviewButton;

    public FirebaseDatabase database;
    public FirebaseAuth auth;
    public FirebaseUser user;

    public int i;
    public float totalRating = 0f, i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_detail);

        reviewButton = findViewById(R.id.reviewScreenButton_id);
        collegeName = findViewById(R.id.collegName_id);
        location = findViewById(R.id.location_id);
        totalReviewButton = findViewById(R.id.totalReviewsButton_id);
        emailTextView = findViewById(R.id.emailTextView_id);
        minimumMarksButton = findViewById(R.id.minimumMarksButton_id);
        averageRatingButton = findViewById(R.id.averageRatingButton_id);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        getSupportActionBar().hide();

        String name = getIntent().getStringExtra("name");
        String locat = getIntent().getStringExtra("location");
        String minimumMarks = getIntent().getStringExtra("minimumMarks");
        String email = getIntent().getStringExtra("email");

        collegeName.setText(name);
        location.setText(locat);
        emailTextView.setText("Contact: "+email);

        database.getReference().child("Reviews").child(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        i2 = 0;
                        if(snapshot.hasChildren())
                        {
                            for(DataSnapshot snapshot1: snapshot.getChildren())
                            {
                                ReviewModel model = new ReviewModel();
                                model = snapshot1.getValue(ReviewModel.class);

                                totalRating += Float.parseFloat(model.getRating());
                                i2++;
                            }

                            float total = i2*5;
                            float answer = (totalRating*5)/total;

                            averageRatingButton.setText("Average Rating:  " + String.valueOf(answer) + "/5");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reviewButton.setOnClickListener(v -> {
            Intent i = new Intent(CollegeDetailActivity.this, ReviewActivity.class);
            i.putExtra("name", name);
            startActivity(i);
            finish();
        });

        database.getReference().child("Reviews").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    i = 0;
                    for(DataSnapshot snapshot1: snapshot.getChildren())
                    {
                        i++;
                    }
                }

                totalReviewButton.setText("Total Reviews:   " + i);
                minimumMarksButton.setText("Minimum Marks:  " + minimumMarks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
