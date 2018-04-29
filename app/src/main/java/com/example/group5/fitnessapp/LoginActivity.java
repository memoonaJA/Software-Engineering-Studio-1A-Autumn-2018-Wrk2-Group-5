package com.example.group5.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<User> userList = new ArrayList<>();
    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;
    AutoCompleteTextView editEmail;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = (AutoCompleteTextView)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        generateUserList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the userlist
        userList.clear();
        generateUserList();
    }

    public void triggerSignUp(View view) {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    public void triggerLogin(View view) {
        Intent intent = new Intent(this, MainPage.class);
        if (dataMatches()) {
            startActivity(intent);
            this.finish();
        }
    }

    private void generateUserList() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = mDatabase.child("users");

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    userList.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    private boolean dataMatches() {
        for (User user : userList) {
            if (user.email.equals(editEmail.getText().toString()) && user.password.equals(editPassword.getText().toString())) {
                return true;
            }
        }
        return false;
    }

}

