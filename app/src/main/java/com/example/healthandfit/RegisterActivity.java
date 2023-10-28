package com.example.healthandfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText editfullname,editemail,editage,editpwd,editconfrmmpwd;
    private ProgressBar progressbar;
    private RadioGroup radiogroupgender;
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    private RadioButton radioButtonRegisterGenderSelected;
    //private static final String TAG = "RegisterActivity";

    //new
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    UserInfo userinfo;

    //private EditText NameEdt,AgeEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();


//        //new
//        // initializing our edittext and button
////        NameEdt = findViewById(R.id.editfullname);
////        AgeEdt = findViewById(R.id.editage);
//
//
//        // below line is used to get the
//        // instance of our FIrebase database.
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.getReference("HnF app").child(textFullName).setValue(userinfo);
//
//        // initializing our object
//        // class variable.
//        userinfo = new UserInfo();



        //Objects.requireNonNull(getSupportActionBar()).setTitle("Register");
        Toast.makeText(RegisterActivity.this,"You can register now",Toast.LENGTH_LONG).show();
        editfullname = findViewById(R.id.editfullname);
        editemail = findViewById(R.id.editemail);
        editage = findViewById(R.id.editage);
        editpwd = findViewById(R.id.editpwd);
        editconfrmmpwd = findViewById(R.id.editconfrmpwd);
        progressbar =findViewById(R.id.progressbar);

        radiogroupgender = findViewById(R.id.radiogroupgender);
        radiogroupgender.clearCheck();

        Button Register = findViewById(R.id.register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = radiogroupgender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);
                String textFullName = editfullname.getText().toString();
                String textEmail = editemail.getText().toString();
                String textage = editage.getText().toString();
                String textPwd = editpwd.getText().toString();
                String textConfirmPwd = editconfrmmpwd.getText().toString();
                String textGender;
                //new
                // initializing our edittext and button
//        NameEdt = findViewById(R.id.editfullname);
//        AgeEdt = findViewById(R.id.editage);


                // below line is used to get the
                // instance of our FIrebase database.
                // initializing our object
                // class variable.
                userinfo = new UserInfo();

                String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                // below line is used to get reference for our database.
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("UserData").child(currentDate);
//                //firebaseDatabase.getReference("HnF app").child(textFullName).setValue(userinfo);
                //FirebaseDatabase.getInstance().getReference("UserInfo").child(currentDate);

                // initializing our object
                // class variable.
                //userinfo = new UserInfo();


                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your Full name", Toast.LENGTH_LONG).show();
                    editfullname.setError("Full name is required");
                    editfullname.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editemail.setError("Email is required");
                    editemail.requestFocus();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your Email", Toast.LENGTH_LONG).show();
                    editemail.setError("Valid Email is required");
                    editemail.requestFocus();

                } else if (TextUtils.isEmpty(textage)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your age", Toast.LENGTH_LONG).show();
                    editage.setError("Age is required");
                    editage.requestFocus();
                } else if (radiogroupgender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Please select your Gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is Required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                    editpwd.setError("Password is required");
                    editpwd.requestFocus();

                } else if (textPwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should be atleast 6 digits", Toast.LENGTH_LONG).show();
                    editpwd.setError("password too weak");
                    editpwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editconfrmmpwd.setError("Password confirmation is required");
                    editconfrmmpwd.requestFocus();
                } else if (!textPwd.equals(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please use same password", Toast.LENGTH_LONG).show();
                    editconfrmmpwd.setError("Password confirmation is required");
                    editconfrmmpwd.requestFocus();
                    editconfrmmpwd.clearComposingText();
                    editpwd.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressbar.setVisibility(View.VISIBLE);
                    //registerUser(textFullName,textEmail,textage,textGender,textPwd);

                    //new
                    // getting text from our edittext fields.
//                    String username = editfullname.getText().toString();
//                    String userage = editage.getText().toString();


                    auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
                                /*int h=0;
                                while( h!=1){
                                    h = isEmailVerified();

                                    if (h == 0)
                                        Toast.makeText(RegisterActivity.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                                }
                                //addDatatoFirebase(name, age, address);

                                if(h==1)*/
                                //new
                                addDatatoFirebase(textFullName, textage);
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));






                            } else {
                                Toast.makeText(RegisterActivity.this, "SignUp Failed" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }




        });





    }

    //new
    private void addDatatoFirebase (String name, String age){
        // below 3 lines of code is used to set
        // data in our object class.
        userinfo.setName(name);
        userinfo.setAge(age);


        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(userinfo);

                // after adding this data we are showing toast message.
                Toast.makeText(RegisterActivity.this, "Data uploaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(RegisterActivity.this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void sendEmailVerification() {


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        assert firebaseUser != null;
        firebaseUser.sendEmailVerification()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Instructions Sent...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Failed to send due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private int isEmailVerified() {
        if (firebaseUser.isEmailVerified()) {
            Toast.makeText(this, "User is verified...", Toast.LENGTH_SHORT).show();
            return 1;
        } else {
            Toast.makeText(this, "User isn't verified...", Toast.LENGTH_SHORT).show();
            return 0;
        }


    }


}