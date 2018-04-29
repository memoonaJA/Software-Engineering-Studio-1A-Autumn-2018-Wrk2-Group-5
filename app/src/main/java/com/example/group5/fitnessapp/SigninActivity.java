package com.example.group5.fitnessapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;
    EditText newPassword;
    AutoCompleteTextView newEmail;
    TextInputEditText newPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        newEmail = (AutoCompleteTextView) findViewById(R.id.newEmail);
        newPassword = (EditText)findViewById(R.id.newPassword);
        newPasswordAgain = (TextInputEditText) findViewById(R.id.newPasswordAgain);
    }

    public void triggerSignUp(View view) {
        // TODO:Firebase database management
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User newUser = createUser();
        if (newUser != null) {
            // TODO:Verify if users exist in database already, and deny signup
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
}
