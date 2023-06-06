package com.example.swarai;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationPage extends AppCompatActivity {

    TextView signInBtn;
    AppCompatButton signUpBtn;

    EditText etEmail,etPassword,etConfirmPass;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPass);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationPage.this,MainPage.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationPage.this,MainPage.class));
//                PerformAuth();
            }
        });

    }
//    private void PerformAuth() {
//
//        String email = etEmail.getText().toString().trim();
//        String Password = etPassword.getText().toString().trim();
//        String ConfirmPassword = etConfirmPass.getText().toString().trim();
//
//        if(!email.matches(emailPattern)){
//            etEmail.setError("Enter correct email");
//
//        } else if (Password.isEmpty() || Password.length() < 6) {
//            etPassword.setError("Enter Correct Password");
//
//        }else if (!Password.equals(ConfirmPassword)){
//            etConfirmPass.setError("Password Not Match both field");
//        }
//        else {
//
//    8655020215
//            progressDialog.setMessage("Please wait while Registration...");
//            progressDialog.setTitle("Registration");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            mAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    if(task.isSuccessful()){
//
//                        progressDialog.dismiss();
//                        Log.d(TAG,"errrr");
//                        NextActivity();
//                        Toast.makeText(RegistrationPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                    }else {
//
//                        progressDialog.dismiss();
////                        Log.d(TAG,"errrr");
//                        Toast.makeText(RegistrationPage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }

//    private void NextActivity() {
//
//        Intent intent = new Intent(RegistrationPage.this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
}