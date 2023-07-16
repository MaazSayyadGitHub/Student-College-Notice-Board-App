package com.maaz.collegeapp.Auth;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private TextView openForgetPass;
    private String Email, Pass;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        TextView openReg = findViewById(R.id.openReg);
        openForgetPass = findViewById(R.id.openForgetPass);
        email = findViewById(R.id.logEmail);
        pass = findViewById(R.id.logPass);
        Button logBtn = findViewById(R.id.logBtn);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");

        logBtn.setOnClickListener(v -> validateData());

        // we can do like this also (lambda Expression)
        // logBtn.setOnClickListener(v -> validateData());
        
        openReg.setOnClickListener(v -> OpenRegisterActivity());

        openForgetPass.setOnClickListener(v -> OpenForgetPasswordActivity());

    }

    private void OpenForgetPasswordActivity() {
        startActivity(new Intent(this, ForgotPassActivity.class));
        // finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // go to Main if user Logged In.
        if (auth.getCurrentUser() != null){
            OpenMainActivity();
        }
    }

    private void validateData() {
        Email = email.getText().toString();
        Pass = pass.getText().toString();

        if (Email.isEmpty()){
            email.setError("Required");
            email.requestFocus();
        } else if (Pass.isEmpty()){
            pass.setError("Required");
            pass.requestFocus();
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        progressDialog.show();
        auth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();

                OpenMainActivity();

            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Error : "+task.getException(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Error : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void OpenMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void OpenRegisterActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }
}