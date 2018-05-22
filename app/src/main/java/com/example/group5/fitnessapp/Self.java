package com.example.group5.fitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Self extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mUsers;

    EditText editWeight;
    EditText editHeight;
    EditText editAge;
    EditText editIntake;
    EditText editGoal;
    Button button_editInfo;

    ArrayList<EditText> fields = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUsers = mDatabase.child("users");

        button_editInfo = (Button)findViewById(R.id.button_editInfo);
        editWeight = (EditText)findViewById(R.id.editWeight);
        editHeight = (EditText)findViewById(R.id.editHeight);
        editAge = (EditText)findViewById(R.id.editAge);
        editIntake = (EditText)findViewById(R.id.editIntake);
        editGoal = (EditText)findViewById(R.id.editGoal);

        fields.add(editWeight);
        fields.add(editHeight);
        fields.add(editAge);
        fields.add(editIntake);
        fields.add(editGoal);

        for (EditText editText : fields) {
            editText.setClickable(false);
        }
    }

    void onEdit(View view) {
        if (button_editInfo.getText().equals("EDIT INFORMATION")) {
            button_editInfo.setText("CONFIRM");
            for (EditText editText : fields) {
                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
            }
        } else {
            button_editInfo.setText("EDIT INFORMATION");
            for (EditText editText : fields) {
                editText.setFocusable(false);
                editText.setFocusableInTouchMode(false);
            }
        }
    }
}
