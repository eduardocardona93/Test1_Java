package com.example.test1_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // variables definition
    EditText loginNameFld, loginUserFld, logInPasswordField;
    Button loginBtn;
    // student name to be passed to the CourseSelectionActivity class
    public static String studentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // elements to variables assignations
        loginNameFld = findViewById(R.id.loginNameFld);
        loginUserFld = findViewById(R.id.loginUserFld);
        logInPasswordField = findViewById(R.id.logInPasswordField);
        loginBtn = findViewById(R.id.loginBtn);

        // log in button click event
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginNameFld.getText().toString().isEmpty()){ // validates if the login name is set
                    // show a toast indicating the error
                    Toast.makeText(getBaseContext(), "Student name field is empty", Toast.LENGTH_SHORT).show();
                }else if(loginUserFld.getText().toString().isEmpty()){ // validates if the login user is set
                    // show a toast indicating the error
                    Toast.makeText(getBaseContext(), "User name field is empty", Toast.LENGTH_SHORT).show();
                }else if(logInPasswordField.getText().toString().isEmpty()){
                    // show a toast indicating the error
                    Toast.makeText(getBaseContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
                }else{
                    // in case all the fields are not empty, validates the user and password set
                    if (loginUserFld.getText().toString().equalsIgnoreCase("student1") && logInPasswordField.getText().toString().equals("123456")){
                        // sets the student name
                        studentName = loginNameFld.getText().toString();
                        // create the intent object with the destination class
                        Intent intent = new Intent(getBaseContext(),CourseSelectionActivity.class);
                        // go to the course selection view
                        startActivity(intent);
                    }else{
                        // show a toast indicating the error
                        Toast.makeText(getBaseContext(), "Invalid username or password, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}