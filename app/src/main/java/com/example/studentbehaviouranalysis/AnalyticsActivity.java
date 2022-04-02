package com.example.studentbehaviouranalysis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class AnalyticsActivity extends AppCompatActivity {

    //Declare inside class
    CardView cvClassBehaviour, cvIndividualStudentBehaviour, cvIndividualAttention, cvAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        //Bind inside onCreate()
        cvClassBehaviour = (CardView) findViewById(R.id.cvClassBehaviour);
        cvIndividualStudentBehaviour = (CardView) findViewById(R.id.cvIndividualStudentBehaviour);
        cvIndividualAttention = (CardView) findViewById(R.id.cvIndividualAttention);
        cvAttendance = (CardView) findViewById(R.id.cvAttendance);

        //HomeButton
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final String classname = i.getStringExtra("classname");
        String subjectname = i.getStringExtra("subjectname");
        final String date = i.getStringExtra("date");
        final String time = i.getStringExtra("time");
        final String frameCount = i.getStringExtra("frameCount");
        if (subjectname.equals("10:25-11:25"))
            subjectname = "10:25:00";
        else if (subjectname.equals("11:25-12:25"))
            subjectname = "11:25:00";
        else if (subjectname.equals("12:45-1:45"))
            subjectname = "12:45:00";
        else if (subjectname.equals("1:45-2:45"))
            subjectname = "01:45:00";
        else if (subjectname.equals("3:00-4:00"))
            subjectname = "3:00";
        else if (subjectname.equals("4:00-5:00"))
            subjectname = "4:00";

        final String finalSubjectname = subjectname;
        cvClassBehaviour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyticsActivity.this, ClassBehaviour.class);
                i.putExtra("classname", classname);
                i.putExtra("subjectname", finalSubjectname);
                i.putExtra("date", date);
                i.putExtra("time", time);
                i.putExtra("frameCount",frameCount);
                startActivity(i);
            }
        });

        cvIndividualStudentBehaviour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyticsActivity.this, StudentActivity.class);
                i.putExtra("classname", classname);
                i.putExtra("subjectname", finalSubjectname);
                i.putExtra("date", date);
                i.putExtra("time", time);
                i.putExtra("frameCount",frameCount);
                startActivity(i);
            }
        });

        cvIndividualAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyticsActivity.this, IndividualAttention.class);
                i.putExtra("classname", classname);
                i.putExtra("subjectname", finalSubjectname);
                i.putExtra("date", date);
                i.putExtra("time", time);
                i.putExtra("frameCount",frameCount);
                startActivity(i);
            }
        });

        cvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyticsActivity.this, Attendance.class);
                i.putExtra("classname", classname);
                i.putExtra("subjectname", finalSubjectname);
                i.putExtra("date", date);
                i.putExtra("time", time);
                i.putExtra("frameCount",frameCount);
                startActivity(i);
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
