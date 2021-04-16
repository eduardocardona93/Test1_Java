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
    RadioButton undergradeRb,graduateRb ;
    Spinner courseSp;
    TextView courseFeesLbl, courseHoursLbl, totalFeesLbl, totalHoursLbl;
    CheckBox accomodationChk, insuranceChk;
    Button addButton, logoutBtn;

    int hoursLimit = 19, totalHours = 0, selectedCourseHours = 0;
    double selectedCourseFees = 0.0, totalCoursesFees= 0.0, totalFees = 0.0, optionFees = 0.0;
    String studentName = "";
    public ArrayList<Course> courses = new ArrayList<>();
    public ArrayList<String> coursesName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);
        studentName = MainActivity.studentName;
        Toast.makeText(this, "Welcome " + studentName, Toast.LENGTH_LONG).show();
        // variables and elements linking
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
        // set the arrays
        fillCourses();
        // set initial values for variables and elements
        clearAll(true);

        // radio buttons
        undergradeRb.setOnClickListener(new gradeRadioButtonEvent());
        graduateRb.setOnClickListener(new gradeRadioButtonEvent());
        // checkboxes
        accomodationChk.setOnCheckedChangeListener(new optionsCheckboxEvent());
        insuranceChk.setOnCheckedChangeListener(new optionsCheckboxEvent());
        // buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCourseHours + totalHours > hoursLimit){
                    Toast.makeText(getBaseContext(), "You can't add this course, Total Hours Limit (" + hoursLimit + ") will be exceeded ", Toast.LENGTH_LONG  ).show();
                }else{
                    totalHours += selectedCourseHours;
                    totalHoursLbl.setText(String.valueOf(totalHours));
                    totalCoursesFees += selectedCourseFees;
                    calculate();
                }
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        // spinner
        ArrayAdapter aa1=new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, coursesName);
        courseSp.setAdapter(aa1);
        courseSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeCourseHandler(courses.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                changeCourseHandler(courses.get(0));

            }
        });


    }

    public void fillCourses(){
        courses.add(new Course("Java", 1300.0 , 6));
        courses.add(new Course("Swift", 1500.0 , 5));
        courses.add(new Course("iOS", 1350.0 , 5));
        courses.add(new Course("Android", 1400.0 , 7));
        courses.add(new Course("Database", 1000.0 , 4));

        for (Course cou : courses) {
            coursesName.add(cou.getName());
        }
    }

    public void changeCourseHandler(Course selCourse){
        selectedCourseFees = selCourse.getFees();
        selectedCourseHours = selCourse.getHours();
        courseFeesLbl.setText("$ " + String.format("%.2f" , selectedCourseFees));
        courseHoursLbl.setText(String.valueOf(selectedCourseHours));
    }

    public void calculate(){
        totalFees = totalCoursesFees + optionFees;
        totalFeesLbl.setText("$ " + String.format("%.2f" , totalFees));
    }
    public void clearAll(boolean initial){
        if (initial){
            hoursLimit = 19;
            undergradeRb.setChecked(true);
            courseSp.setSelection(0);
            selectedCourseFees = 0.0;
            courseFeesLbl.setText( "$ 0.00");
            selectedCourseHours = 0;
            courseHoursLbl.setText("0");
        }

        totalHours = 0;

        totalCoursesFees = 0.0;
        totalFees = 0.0;
        optionFees = 0.0;

        totalFeesLbl.setText( "$ 0.00");
        totalHoursLbl.setText("0");
        accomodationChk.setChecked(false);
        insuranceChk.setChecked(false);
    }

    private class gradeRadioButtonEvent implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.undergradeRb:
                    hoursLimit = 19;
                    break;
                case R.id.graduateRb:
                    hoursLimit = 21;
                    break;
                default:
                    break;
            }
            Log.d("gradeRadio", "onClick: " + v.getId());
            clearAll(false);
        }
    }

    private class optionsCheckboxEvent implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            optionFees = 0.0;
            if(accomodationChk.isChecked())
                optionFees += 1000.0;
            if(insuranceChk.isChecked())
                optionFees += 700.0;
            calculate();
        }
    }

}