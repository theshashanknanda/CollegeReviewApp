package com.project.ams.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.project.ams.R;

public class LoginScreen extends AppCompatActivity {

    public FirebaseAuth auth;
    public FirebaseUser user;
    public FirebaseDatabase database;

    public EditText emailEdit;
    public EditText passwordEdit;
    public Button loginButton;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().hide();

        emailEdit = findViewById(R.id.emailEditText_login);
        passwordEdit = findViewById(R.id.passwordEditText_login);
        loginButton = findViewById(R.id.loginButton_login);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        loginButton.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                dialog = new ProgressDialog(LoginScreen.this);
                dialog.setMessage("Logging in");
                dialog.show();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    startActivity(new Intent(LoginScreen.this, ListActivity.class));
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginScreen.this, "Login failed", Toast.LENGTH_LONG).show();
                                Log.d("Mytag", e.getMessage());
                                dialog.dismiss();
                            }
                        });
            }
            else{
                Toast.makeText(LoginScreen.this, "Blanks empty", Toast.LENGTH_LONG).show();
            }
        });
    }
}
