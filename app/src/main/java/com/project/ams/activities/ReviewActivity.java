package com.project.ams.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class ReviewActivity extends AppCompatActivity {

    public EditText editText;
    public Button button;
    public RatingBar ratingBar;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;

    User user1;

    public List<ReviewModel> reviewModelList;

    public RecyclerView recyclerView;
    public ReviewAdapter adapter;

    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        getSupportActionBar().hide();

        editText = findViewById(R.id.reviewEditText_id);
        button = findViewById(R.id.reviewButton_id);
        ratingBar = findViewById(R.id.ratingbar_id);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reviewModelList = new ArrayList<>();

        recyclerView = findViewById(R.id.reviewRecylerView_id);
        adapter = new ReviewAdapter(reviewModelList, this);

        name = getIntent().getStringExtra("name");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user1 = new User();
                database.getReference().child("Students").child(user.getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChildren())
                                {
                                    user1 = snapshot.getValue(User.class);

                                    String review = editText.getText().toString().trim();
                                    String name1 = user1.getUsername();

                                    String rating = String.valueOf(ratingBar.getRating());

                                    if(!review.isEmpty()){
                                        ReviewModel model = new ReviewModel();
                                        model.setReview(review);
                                        model.setUserName(name1);
                                        model.setRating(rating);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                                        builder.setTitle("Alert");
                                        builder.setMessage("Are you sure you want to add this dialog?");
                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                database.getReference().child("Reviews")
                                                        .child(name)
                                                        .child(auth.getUid())
                                                        .setValue(model);

                                                editText.setText("");
                                                ratingBar.setRating(0);

                                                Toast.makeText(ReviewActivity.this, "Review Added",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                        builder.create().show();

                                    }
                                    else{
                                        Toast.makeText(ReviewActivity.this, "Please type something", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });

        // Getting reviews from firebase
        database.getReference().child("Reviews").child(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reviewModelList.clear();
                        if(snapshot.hasChildren())
                        {
                            for(DataSnapshot snapshot1: snapshot.getChildren())
                            {
                                ReviewModel model = new ReviewModel();
                                model = snapshot1.getValue(ReviewModel.class);

                                reviewModelList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}