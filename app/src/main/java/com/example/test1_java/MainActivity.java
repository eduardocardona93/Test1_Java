package com.example.test1_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText loginNameFld, loginUserFld, logInPasswordField;
    Button loginBtn;

    public static String studentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginNameFld = findViewById(R.id.loginNameFld);
        loginUserFld = findViewById(R.id.loginUserFld);
        logInPasswordField = findViewById(R.id.logInPasswordField);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginNameFld.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Student name field is empty", Toast.LENGTH_SHORT).show();
                }else if(loginUserFld.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "User name field is empty", Toast.LENGTH_SHORT).show();
                }else if(logInPasswordField.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Password field is empty", Toast.LENGTH_SHORT).show();
                }else{
                    if (loginUserFld.getText().toString().equalsIgnoreCase("student1") && logInPasswordField.getText().toString().equals("123456")){
                        studentName = loginNameFld.getText().toString();
                        Intent intent = new Intent(getBaseContext(),CourseSelectionActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Invalid username or password, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}