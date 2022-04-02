package com.example.studentbehaviouranalysis;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class SubjectDetails extends AppCompatActivity {

    //Declare inside class
    Spinner spnClassname, spnSubjectname, spnTime;
    TextView tvDate;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText etSubjectID, etNumberOfFrame;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);

        //Bind inside onCreate()
        tvDate = (TextView) findViewById(R.id.tvDate);
        etSubjectID = (EditText) findViewById(R.id.etSubjectID);
        etNumberOfFrame = (EditText) findViewById(R.id.etNumberOfFrame);
        spnClassname = (Spinner) findViewById(R.id.spnClassname);
        spnSubjectname = (Spinner) findViewById(R.id.spnSubjectname);
        spnTime = (Spinner) findViewById(R.id.spnTime);
        btnNext = (Button) findViewById(R.id.btnNext);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //HomeButton
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final String subjectID = i.getStringExtra("subjectID");

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SubjectDetails.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                tvDate.setText(date);
            }
        };

        //Create ArrayList for class
        final ArrayList<String> classname = new ArrayList<String>();
        classname.add("D17A");
        classname.add("D17B");
        classname.add("D17C");

        //Create ArrayAdapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classname);

        //Set arrayAdapter with the Spinner
        spnClassname.setAdapter(arrayAdapter);

        //Create ArrayList for subject
        final ArrayList<String> subjectname = new ArrayList<String>();
        subjectname.add("AI");
        subjectname.add("SC");
        subjectname.add("CSS");
        subjectname.add("NTAL");
        subjectname.add("DSP");

        //Create ArrayAdapter
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectname);

        //Set arrayAdapter with the Spinner
        spnSubjectname.setAdapter(arrayAdapter2);

        //Create Array for time
        final String[] time = {"10:25-11:25", "11:25-12:25", "12:45-1:45", "1:45-2:45", "3:00-4:00", "4:00-5:00"};

        //Create ArrayAdapter
        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, time);

        //Set arrayAdapter with the Spinner
        spnTime.setAdapter(arrayAdapter3);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredSubjectID = etSubjectID.getText().toString();
                String date = tvDate.getText().toString();
                String numberOfFrame = etNumberOfFrame.getText().toString();
                if (date.length() == 0) {
                    tvDate.setError("Please select date");
                    tvDate.requestFocus();
                    return;
                }
                if (enteredSubjectID.length() == 0) {
                    tvDate.setError(null);
                    etSubjectID.setError("Please enter subject ID");
                    etSubjectID.requestFocus();
                    return;
                }
                if (numberOfFrame.length() == 0) {
                    tvDate.setError(null);
                    etNumberOfFrame.setError("Please enter number of minutes for analysis");
                    etNumberOfFrame.requestFocus();
                    return;
                }
                if (Integer.parseInt(numberOfFrame) <= 0 || Integer.parseInt(numberOfFrame) > 61) {
                    tvDate.setError(null);
                    etNumberOfFrame.setError("Please enter valid number of minutes for analysis");
                    etNumberOfFrame.requestFocus();
                    return;
                }
                if (enteredSubjectID.equals(subjectID)) {
                    Intent i = new Intent(SubjectDetails.this, AnalyticsActivity.class);
                    i.putExtra("classname", classname.get(spnClassname.getSelectedItemPosition()));
                    i.putExtra("subjectname", subjectname.get(spnSubjectname.getSelectedItemPosition()));
                    i.putExtra("date", tvDate.getText().toString());
                    i.putExtra("time", time[spnTime.getSelectedItemPosition()]);
                    i.putExtra("frameCount",numberOfFrame);
                    startActivity(i);
                } else
                    Toast.makeText(SubjectDetails.this, "Incorrect subject ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
