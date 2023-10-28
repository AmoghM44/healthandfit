package com.example.healthandfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editloginemail, editloginpwd;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    TextView forgotPassword;

    FirebaseUser firebaseUser;


    // private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Objects.requireNonNull(getSupportActionBar()).setTitle("Login");

        editloginemail = findViewById(R.id.editloginemail);
        editloginpwd = findViewById(R.id.editloginpwd);
        progressBar = findViewById(R.id.progressBar);
        forgotPassword = findViewById(R.id.forgotPassword);
        auth = FirebaseAuth.getInstance();

        //login button
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editloginemail.getText().toString();
                String textPwd = editloginpwd.getText().toString();

                if (!textEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    if (!textPwd.isEmpty()) {
                        auth.signInWithEmailAndPassword(textEmail, textPwd)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        if(isEmailVerified()){
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Verify email", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        editloginpwd.setError("Empty fields are not allowed");
                    }
                } else if (textEmail.isEmpty()) {
                    editloginemail.setError("Empty fields are not allowed");
                } else {
                    editloginemail.setError("Please enter correct email");
                }
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        /*signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this, regactivity.class));
            }
        });*/


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText editforgetemail = dialogView.findViewById(R.id.editforgetemail);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.resetbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = editforgetemail.getText().toString();

                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.cancelbutton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //if (dialog.getWindow() != null){
                // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                // }
                dialog.show();
            }
        });


    }
    private boolean isEmailVerified() {
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        if (firebaseUser.isEmailVerified()) {
            Toast.makeText(this, "User is verified...", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "User isn't verified...", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}