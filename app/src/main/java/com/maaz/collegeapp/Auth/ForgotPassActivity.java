package com.maaz.collegeapp.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.maaz.collegeapp.R;

public class ForgotPassActivity extends AppCompatActivity {

    private Button forgetBtn;
    private EditText email;

    private String Email;
    private FirebaseAuth auth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");

        forgetBtn = findViewById(R.id.forgetBtn);
        email = findViewById(R.id.forEmail);

        auth = FirebaseAuth.getInstance();

        forgetBtn.setOnClickListener(v -> validateData());


    }

    private void validateData() {
        Email = email.getText().toString();

        if (Email.isEmpty()){
            email.setError("Required");
            email.requestFocus();
        } else {
            ForgetPassword();
        }
    }

    private void ForgetPassword() {
        progressDialog.show();
        auth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this, "Check Your Email", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}