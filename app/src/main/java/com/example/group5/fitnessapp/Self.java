package com.example.group5.fitnessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
*/
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Self extends AppCompatActivity {

   // private DatabaseReference mDatabase;
    //private DatabaseReference mUsers;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    TextView emailText;
    EditText editWeight, editHeight, editAge, editIntake, editGoal;
    Button button_editInfo;

    ArrayList<EditText> fields = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        mAuth = FirebaseAuth.getInstance();

        //Check if the user is logged in, may not be necessary for this activity; leaving it for now
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent( this, LoginActivity.class));
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Use this object to get email if you want to show the user their email.
        FirebaseUser user = mAuth.getCurrentUser();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
       // mUsers = mDatabase.child("users");

        button_editInfo = (Button)findViewById(R.id.button_editInfo);
        editWeight = (EditText)findViewById(R.id.editWeight);
        editHeight = (EditText)findViewById(R.id.editHeight);
        editAge = (EditText)findViewById(R.id.editAge);
        editIntake = (EditText)findViewById(R.id.editIntake);
        editGoal = (EditText)findViewById(R.id.editGoal);
        emailText = (TextView)findViewById(R.id.emailText);
        emailText.setText("" + user.getEmail());

        fields.add(editWeight);
        fields.add(editHeight);
        fields.add(editAge);
        fields.add(editIntake);
        fields.add(editGoal);

        for (EditText editText : fields) {
            editText.setClickable(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //If the user is not logged in, redirect them to the login page
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent (this, LoginActivity.class));
        }
        //Implement value listener which executes a method everytime something changes in the database
        mDatabase.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser current = mAuth.getCurrentUser();
                //this for loop is iterating through each variable in the users node
                //e.g first weight, next intake etc.
                String weight = (String) dataSnapshot.child("weight").getValue();
                String height = (String) dataSnapshot.child("height").getValue();
                String age = (String) dataSnapshot.child("age").getValue();
                String intake = (String) dataSnapshot.child("intake").getValue();
                String goal = (String) dataSnapshot.child("goal").getValue();

                editWeight.setText(weight);
                editHeight.setText(height);
                editAge.setText(age);
                editIntake.setText(intake);
                editGoal.setText(goal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onUpdate() {
        String weight = editWeight.getText().toString().trim();
        String height = editHeight.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String intake = editIntake.getText().toString().trim();
        String goal = editGoal.getText().toString().trim();

        //Store information in a new object that stores all this info
        User userInformation = new User(weight, height, age, intake, goal);
        //Get firebase user object to store this data in for the current user
        FirebaseUser user = mAuth.getCurrentUser();
        //Add this information to the databsae under that current users id
        //Will use user id to create a node and then store that information in that node
        mDatabase.child(user.getUid()).setValue(userInformation);
    }

    void onEdit(View view) {
        //If edit information is clicked then allow editing of textfield
        if (button_editInfo.getText().equals("EDIT INFORMATION")) {
            button_editInfo.setText("CONFIRM");
            for (EditText editText : fields) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
            }
        } else {
            //If button does not have "edit information" onclick then set it back to that
            //this means the user has finish editing the info and press confirmed
            button_editInfo.setText("EDIT INFORMATION");
            for (EditText editText : fields) {
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
                onUpdate(); //call the method that updates the data in the database ,. 
            }
        }
    }
}
