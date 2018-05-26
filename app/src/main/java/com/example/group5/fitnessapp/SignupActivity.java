package com.example.group5.fitnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/

public class SignupActivity extends AppCompatActivity {

    //private DatabaseReference mDatabase;
    //private DatabaseReference mUsers;
    //Test test test test test test 
    //FireBase authorise object
    private FirebaseAuth mAuth;
    EditText newPassword;
    EditText newEmail;
    TextInputEditText newPasswordAgain;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initialise firebase object
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        //If user is already logged in, navigate them to the mainpage (the profile page)
        //Note don't put this in the sign up page
        /*if(mAuth.getCurrentUser() != null) {
            //profile activity here
            finish();
        startActivity(new Intent(getApplicationContext(), MainPage.class));
    } */
        //Initialise the views
        newEmail = (EditText) findViewById(R.id.newEmail);
        newPassword = (EditText)findViewById(R.id.newPassword);
        newPasswordAgain = (TextInputEditText) findViewById(R.id.newPasswordAgain);
    }

    public void triggerSignUp(View view){
        String email = newEmail.getText().toString().trim();
        String password = newPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function execution further.
            return;
        }
        //if validations are ok
        //will first show a progress bar
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        //this method creates a user on the firebase server with the email and password parameters
        //we can also attach a listener which will execute when the registration is complete
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Here we can check is the task is successful or not
                if (task.isSuccessful()) {
                    //user is sucessfully registered and logged in
                    //we will start the main activty here
                    //profile activity here
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainPage.class));

                }else {
                    Toast.makeText(SignupActivity.this, "Could not register.. please Try Again", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    /*public void triggerSignUp(View view) {
        // TODO:Firebase database management
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User newUser = createUser();
        if (newUser != null) {
            newUser.setUid(mDatabase.child("users").push().getKey());
            mDatabase.child("users").child(newUser.getUid()).setValue(newUser);
        }
        Intent intent = new Intent(this, LoginActivity.class);
        this.finish();
    }

    private User createUser() {
        // Get Strings from textfields and check if they are valid
        String email = newEmail.getText().toString();
        // Check if the email is valid
        if (!email.contains("@")) {
            return null;
        } else {
            // check passwords
            String password = newPassword.getText().toString();
            if (!password.equals(newPasswordAgain.getText().toString())) {
                // password fields don't match, fail the user creation by returning null
                return null;
            } else {
                // password fields match, so return a User
                User newUser = new User(email, password);
                return newUser;
            }
        }
    }
    */
}
