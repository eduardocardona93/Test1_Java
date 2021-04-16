package com.example.test1_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CourseSelectionActivity extends AppCompatActivity {
    // variables definition
    RadioButton undergradeRb,graduateRb ;
    Spinner courseSp;
    TextView courseFeesLbl, courseHoursLbl, totalFeesLbl, totalHoursLbl;
    CheckBox accomodationChk, insuranceChk;
    Button addButton, logoutBtn;

    // global variables
    int hoursLimit = 19, totalHours = 0, selectedCourseHours = 0;
    double selectedCourseFees = 0.0, totalCoursesFees= 0.0, totalFees = 0.0, optionFees = 0.0;
    String studentName = "";
    public ArrayList<Course> courses = new ArrayList<>(); // course array list
    public ArrayList<String> coursesName = new ArrayList<>(); // courses names array list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);
        // sets the student name set in the login (main activity) view
        studentName = MainActivity.studentName;
        // shows a toast welcoming the student
        Toast.makeText(this, "Welcome " + studentName, Toast.LENGTH_LONG).show();
        // elements to variables assignations
        undergradeRb = findViewById(R.id.undergradeRb);
        graduateRb = findViewById(R.id.graduateRb);
        courseSp = findViewById(R.id.courseSp);
        courseFeesLbl = findViewById(R.id.courseFeesLbl);
        courseHoursLbl = findViewById(R.id.courseHoursLbl);
        totalFeesLbl = findViewById(R.id.totalFeesLbl);
        totalHoursLbl = findViewById(R.id.totalHoursLbl);
        accomodationChk = findViewById(R.id.accomodationChk);
        insuranceChk = findViewById(R.id.insuranceChk);
        addButton = findViewById(R.id.addButton);
        logoutBtn = findViewById(R.id.logoutBtn);
        // set the array lists values
        fillCourses();
        // set initial values for variables and elements
        clearAll(true);

        // radio buttons events
        undergradeRb.setOnClickListener(new gradeRadioButtonEvent());
        graduateRb.setOnClickListener(new gradeRadioButtonEvent());
        // checkboxes events
        accomodationChk.setOnCheckedChangeListener(new optionsCheckboxEvent());
        insuranceChk.setOnCheckedChangeListener(new optionsCheckboxEvent());
        // buttons events
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validates if the selected hours does not exceed the limit
                if(selectedCourseHours + totalHours > hoursLimit){
                    // show a toast indicating the error
                    Toast.makeText(getBaseContext(), "You can't add this course, Total Hours Limit (" + hoursLimit + ") will be exceeded ", Toast.LENGTH_LONG  ).show();
                }else{
                    // adds the selected hours to the total
                    totalHours += selectedCourseHours;
                    // shows the total hours to the user
                    totalHoursLbl.setText(String.valueOf(totalHours));
                    // add the total courses fees
                    totalCoursesFees += selectedCourseFees;
                    // calculates and shows the grand total
                    calculate();
                }
            }
        });
        // logout to return to the login/main activity
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create the intent object with the destination class
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                // go to the main view
                startActivity(intent);
            }
        });

        // spinner
        ArrayAdapter aa1=new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, coursesName);
        courseSp.setAdapter(aa1);
        courseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // when the course changes, sets the variables and the labels depending on the selected course
                changeCourseHandler(courses.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // when the course is the first, sets the variables and the labels depending on the first course
                changeCourseHandler(courses.get(0));

            }
        });
    }
    // set the array lists values
    public void fillCourses(){
        courses.add(new Course("Java", 1300.0 , 6)); // java
        courses.add(new Course("Swift", 1500.0 , 5)); // swift
        courses.add(new Course("iOS", 1350.0 , 5)); // iOS
        courses.add(new Course("Android", 1400.0 , 7)); // android
        courses.add(new Course("Database", 1000.0 , 4)); // database
        // fills the courses names array list
        for (Course cou : courses) {
            coursesName.add(cou.getName());
        }
    }
    // when the course changes, sets the variables and the labels depending on the selected course
    public void changeCourseHandler(Course selCourse){
        selectedCourseFees = selCourse.getFees();
        selectedCourseHours = selCourse.getHours();
        courseFeesLbl.setText("$ " + String.format("%.2f" , selectedCourseFees));
        courseHoursLbl.setText(String.valueOf(selectedCourseHours));
    }
    // calculates and shows the grand total
    public void calculate(){
        totalFees = totalCoursesFees + optionFees;
        totalFeesLbl.setText("$ " + String.format("%.2f" , totalFees));
    }
    public void clearAll(boolean initial){
        if (initial){ // if there is an initial clear
            hoursLimit = 19;
            undergradeRb.setChecked(true);
            courseSp.setSelection(0);
            selectedCourseFees = 0.0;
            courseFeesLbl.setText( "$ 0.00");
            selectedCourseHours = 0;
            courseHoursLbl.setText("0");
        }
        // variables clear
        totalHours = 0;
        totalCoursesFees = 0.0;
        totalFees = 0.0;
        optionFees = 0.0;
        // elements clear
        totalFeesLbl.setText( "$ 0.00");
        totalHoursLbl.setText("0");
        accomodationChk.setChecked(false);
        insuranceChk.setChecked(false);
    }

    private class gradeRadioButtonEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // verifies the selected radio button
            switch (v.getId()){
                case R.id.undergradeRb:
                    // undergraduate limit 19 hours
                    hoursLimit = 19;
                    break;
                case R.id.graduateRb:
                    // graduate limit 21 hours
                    hoursLimit = 21;
                    break;
                default:
                    break;
            }
            // clears the total fees and total hours, as well as reset the selected options
            clearAll(false);
        }
    }

    private class optionsCheckboxEvent implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            optionFees = 0.0; // reset the option fees
            if(accomodationChk.isChecked()) // if accommodation option is checked
                // adds 1000 to the options Fees
                optionFees += 1000.0;
            if(insuranceChk.isChecked()) // if insuranceChk option is checked
                // adds 700 to the options Fees
                optionFees += 700.0;
            // calculates and shows the grand total
            calculate();
        }
    }

}