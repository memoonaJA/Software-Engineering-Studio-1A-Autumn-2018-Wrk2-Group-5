package com.example.group5.fitnessapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalorieCalculatorActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView dailyIntake;

    private double heightVar;
    private double ageVar;
    private double weightVar;
    private String genderVar;
    private double result;//

    EditText foodEditText, caloriesEditText;
    Button addFoodButton;
    TextView currentIntake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        dailyIntake = (TextView) findViewById(R.id.textDailyIntake);
        foodEditText = (EditText) findViewById(R.id.foodEditText);
        caloriesEditText = (EditText) findViewById(R.id.caloriesEditText);
        addFoodButton = (Button) findViewById(R.id.addFoodButton);
        currentIntake = (TextView) findViewById(R.id.currentIntake);
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        //If the user is not logged in, redirect them to the login page
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent (this, LoginActivity.class));
        }
        //Implement value listener which executes a method every time something changes in the database


            mDatabase.child(mAuth.getCurrentUser().getUid()).child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseUser current = mAuth.getCurrentUser();
                    //Since we added a valueEventListner for the specific user child,
                    //We can simply user the datasnapshot to get the value of variable stored under their id
                    String weight = (String) dataSnapshot.child("weight").getValue();
                    if (weight == null) {
                        Toast.makeText(CalorieCalculatorActivity.this , "Please update your profile to see your recommended caloric intake", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        weightVar = Double.parseDouble(weight);
                        String height = (String) dataSnapshot.child("height").getValue();
                        heightVar = Double.parseDouble(height);
                        String age = (String) dataSnapshot.child("age").getValue();
                        ageVar = Double.parseDouble(age);
                        String gender = (String) dataSnapshot.child("gender").getValue();
                        //genderVar = gender;

                        String male = new String("male");
                        String male2 = new String("Male");
                        String female = new String("female");
                        String female2 = new String("Female");

                        if (gender != null || gender.equals(male) || gender.equals(male2)) { //male
                            result = (10 * (weightVar) + 6.25 * (heightVar) - 5 * ageVar + 5);
                            dailyIntake.setText("Recommended Daily Calorie Intake: " + result + "kcal");

                        } else if (gender != null || gender.equals(female2) || gender.equals(female)) { //female
                            result = (10 * (weightVar) + 6.25 * (heightVar) - 5 * ageVar - 161);
                            dailyIntake.setText("Recommended Daily Calorie Intake: " + result + "kcal");
                        }
                        //String name = (String) dataSnapshot.child("name").getValue();
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            DatabaseReference foodRef = mDatabase.child(mAuth.getCurrentUser().getUid()).child("Food intake");
            foodRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    double intake = 0.0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String food = ds.getKey();
                        intake += Double.parseDouble(ds.getValue().toString());
                    }
                    currentIntake.setText("Current Intake: " + intake + "kcal");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void openWebDB(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https:ndb.nal.usda.gov/ndb/search/list"));
        startActivity(browserIntent);
    }

    public void addFood(View view) {
        mDatabase.child(mAuth.getCurrentUser().getUid()).child("Food intake").child(foodEditText.getText().toString()).setValue(caloriesEditText.getText().toString());
    }

    public void clearFoods(View view) {
        DatabaseReference foodRef = mDatabase.child(mAuth.getCurrentUser().getUid()).child("Food intake");
        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String food = ds.getKey();
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, "Cleared food entries", Toast.LENGTH_SHORT).show();
    }

}
