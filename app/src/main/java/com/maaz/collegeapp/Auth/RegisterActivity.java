package com.maaz.collegeapp.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maaz.collegeapp.MainActivity;
import com.maaz.collegeapp.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button regBtn;
    private TextView openLog;

    private String Name, Email, Pass;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference, DbRef;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.regName);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPass);

        openLog = findViewById(R.id.openLog);

        regBtn = findViewById(R.id.regBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        // we can do like this as well
        // reference = FirebaseDatabase.getInstance().getReference();
        reference = database.getReference();


        regBtn.setOnClickListener(view -> {
            validateData();
        });

        openLog.setOnClickListener(v -> {
            OpenLoginActivity();
        });
    }

    private void OpenLoginActivity() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // if already login
        if (auth.getCurrentUser() != null){
            openMainActivity();
        }
    }

    private void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void validateData() {
        Name = name.getText().toString();
        Email = email.getText().toString();
        Pass = password.getText().toString();

        if (Name.isEmpty()){
            name.setError("Required");
            name.requestFocus();
        } else if (Email.isEmpty()){
            email.setError("Required");
            email.requestFocus();
        } else if (Pass.isEmpty()){
            password.setError("Required");
            password.requestFocus();
        } else {
            createUser();
        }
    }

    private void createUser() {
        progressDialog.show();
        auth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UploadData(); // store data to firebase.
                } else {
                    Toast.makeText(RegisterActivity.this, "Error : " + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failed : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UploadData() {
        DbRef = reference.child("Users");
        String uniqueKey = DbRef.push().getKey();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("key", uniqueKey);
        hashMap.put("Name : ", Name);
        hashMap.put("Email : ", Email);
        hashMap.put("Pass : ", Pass);


        DbRef.child(uniqueKey).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    openMainActivity();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "error " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}