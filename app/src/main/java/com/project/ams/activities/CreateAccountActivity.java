package com.project.ams.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.project.ams.Models.User;
import com.project.ams.R;
import com.project.ams.Splashscreen;

public class CreateAccountActivity extends AppCompatActivity {
    public Button loginButton, createAccountButton;
    public EditText usernameEdit, emailEdit, passwordEdit;

    public FirebaseAuth auth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase database;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        usernameEdit = findViewById(R.id.usernameTextView_createAccount);
        emailEdit = findViewById(R.id.emailEditText_createAccount);
        passwordEdit = findViewById(R.id.paswordEditText_createAccount);

        createAccountButton = findViewById(R.id.createAccountButton);
        loginButton = findViewById(R.id.loginButton);

        if(user != null){
            startActivity(new Intent(CreateAccountActivity.this, ListActivity.class));
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginScreen.class));
                finish();
            }
        });

        createAccountButton.setOnClickListener(v -> {

            String username = usernameEdit.getText().toString().trim();
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
            {
                ProgressDialog dialog = new ProgressDialog(CreateAccountActivity.this);
                dialog.setMessage("Creating Account");
                dialog.show();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                database.getReference().child("Students").child(auth.getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // Success
                                                Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(CreateAccountActivity.this, ListActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CreateAccountActivity.this, "Unable to save your data", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateAccountActivity.this, "Unable to create Account", Toast.LENGTH_LONG).show();
                            }
                        });
            }
            else{
                Toast.makeText(CreateAccountActivity.this, "Blanks empty", Toast.LENGTH_LONG).show();
            }
        });
    }
}
