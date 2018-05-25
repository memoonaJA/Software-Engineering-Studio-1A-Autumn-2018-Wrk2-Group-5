package com.example.group5.fitnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

/*import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<User> userList = new ArrayList<>();
    //private DatabaseReference mDatabase;
    //private DatabaseReference mUsers;
    private AutoCompleteTextView editEmail;
    private EditText editPassword;

    private ProgressDialog progressDialog;

    //Auth object to check for users
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //Check if a user is already logged in, then take them directly to main page
        if(mAuth.getCurrentUser() != null) {
            //Close the current activity here & start the main page
            finish();
            startActivity(new Intent(getApplicationContext(), MainPage.class));
        }

        editEmail = (AutoCompleteTextView)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        //generateUserList();
    }

    public void onLogin(View view) {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        //Check if text fields are empty, then provide error message via toast
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
        //If input is okay thus far, show proceed with login
        //Show progress dialog first
        progressDialog.setMessage("Logging in ...");
        progressDialog.show();

        //Check input with database and see if user exists then log in if true
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Start the main page
                            //Since we're inside a listener we can't user "this" to reference login class
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        } else if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void triggerSignUp(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

//test
/*
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the userlist
        userList.clear();
        generateUserList();
    }

//change
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
    */

}

