package com.example.projecttracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.projecttracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.ProgressDialog;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private EditText userMail;
    private EditText usrPassword;
    private TextView Info;
    private Button btnLogin;
    private ProgressBar loginProgress;
    private int counter = 3;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private Intent HomePage;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        usrPassword = (EditText) findViewById(R.id.login_password);
        Info = (TextView) findViewById(R.id.tvInfo);
        loginProgress = findViewById(R.id.login_progress);
        btnLogin = (Button) findViewById(R.id.loginBtn);

        Info.setText("No of attempts remaining: 3");

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            finish();
            startActivity(new Intent(LoginActivity.this, HomePage.class));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userMail.getText().toString(), usrPassword.getText().toString());

            }
        });
    }

    private void validate(String userMail, String userPassword) {

        mAuth.signInWithEmailAndPassword(userMail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    loginProgress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                    }
                }
            }
        });

    }



    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(LoginActivity.this, HomePage.class));

        if(emailflag){
            finish();
            startActivity(new Intent(LoginActivity.this, HomePage.class));
        }else{
            Toast.makeText(LoginActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            //user is already connected  so we need to redirect him to home page
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(HomePage);
        finish();

    }

}