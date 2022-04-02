package com.example.studentbehaviouranalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    //Declare inside class
    TableLayout tlStudent;
    String loginURL = "http://192.168.43.213/sba/attendance.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Bind inside onCreate()
        tlStudent = (TableLayout) findViewById(R.id.tlStudent);

        //HomeButton
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        final TableRow tblHeadRow = new TableRow(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 10f);
        tblHeadRow.setLayoutParams(params);

        TextView tvHeadColumn = new TextView(this);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
        tvHeadColumn.setLayoutParams(params2);
        tvHeadColumn.setText("Roll Number");
        tvHeadColumn.setPadding(20, 20, 20, 20);
        tvHeadColumn.setTextColor(Color.WHITE);
        tvHeadColumn.setGravity(Gravity.CENTER);
        tblHeadRow.addView(tvHeadColumn);

        TextView tvHeadColumn2 = new TextView(this);
        TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
        tvHeadColumn2.setLayoutParams(params3);
        tvHeadColumn2.setText("Name");
        tvHeadColumn2.setPadding(20, 20, 20, 20);
        tvHeadColumn2.setTextColor(Color.WHITE);
        tvHeadColumn2.setGravity(Gravity.CENTER);
        tblHeadRow.addView(tvHeadColumn2);
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2, Color.BLACK);
        tblHeadRow.setBackgroundDrawable(gd);
        tvHeadColumn.setBackgroundDrawable(gd);
        tvHeadColumn2.setBackgroundDrawable(gd);
        tlStudent.setBackgroundDrawable(gd);
        tlStudent.addView(tblHeadRow);

        Intent i = getIntent();
        final String classname = i.getStringExtra("classname");
        final String subjectname = i.getStringExtra("subjectname");
        final String date = i.getStringExtra("date");
        final String time = i.getStringExtra("time");
        final String frameCount = i.getStringExtra("frameCount");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        //Create StringRequest for PHP script attendance.php
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String rollNumber = "";
                String[] rollNumbers;
                int attendance[] = new int[20];
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("attendance");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        rollNumber = rollNumber + jsonObject2.getString("face_id") + ",";
                    }
                    rollNumbers = rollNumber.split(",");
                    Arrays.sort(rollNumbers);
                    for (int i = 0; i < 20; i++) {
                        if (Arrays.binarySearch(rollNumbers, String.valueOf(i + 1)) >= 0)
                            attendance[i] = 1;
                        else
                            attendance[i] = 0;
                    }
                    for (int i = 0; i < 20; i++) {
                        TableRow tblRow = new TableRow(StudentActivity.this);
                        if (attendance[i] == 1) {
                            TableRow.LayoutParams params4 = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 10f);
                            tblRow.setLayoutParams(params4);
                            TextView tvColumn = new TextView(StudentActivity.this);
                            TableRow.LayoutParams params5 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
                            tvColumn.setLayoutParams(params5);
                            tvColumn.setText("" + (i + 1));
                            tvColumn.setPadding(20, 20, 20, 20);
                            tvColumn.setTextColor(Color.WHITE);
                            tvColumn.setGravity(Gravity.CENTER);
                            tblRow.addView(tvColumn);

                            TextView tvColumn2 = new TextView(StudentActivity.this);
                            TableRow.LayoutParams params6 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
                            tvColumn2.setLayoutParams(params6);
                            if((i+1) == 1)
                                tvColumn2.setText("Roshan Talreja");
                            if((i+1) == 2)
                                tvColumn2.setText("Vivek Sohal");
                            if((i+1) == 3)
                                tvColumn2.setText("Mukta Chandani");
                            if((i+1) == 4)
                                tvColumn2.setText("Madhuresh Pandey");
                            if((i+1) == 6)
                                tvColumn2.setText("Gaurav Ail");
                            if((i+1) == 7)
                                tvColumn2.setText("Hitesh Vaswani");
                            if((i+1) == 9)
                                tvColumn2.setText("Rahul Jaisinghani");
                            if((i+1) == 10)
                                tvColumn2.setText("Murlidhar Gangwani");
                            if((i+1) == 11)
                                tvColumn2.setText("Kamal Teckchandani");
                            if((i+1) == 13)
                                tvColumn2.setText("Vinay Bhatia");
                            if((i+1) == 15)
                                tvColumn2.setText("Ajinkya Pawale");
                            if((i+1) == 17)
                                tvColumn2.setText("Varsha Chhabria");
                            if((i+1) == 20)
                                tvColumn2.setText("Pooja Vazirani");
                            tvColumn2.setPadding(20, 20, 20, 20);
                            tvColumn2.setTextColor(Color.WHITE);
                            tvColumn2.setGravity(Gravity.CENTER);
                            tblRow.addView(tvColumn2);
                            GradientDrawable gd = new GradientDrawable();
                            gd.setStroke(2, Color.BLACK);
                            tblRow.setBackgroundDrawable(gd);
                            tvColumn.setBackgroundDrawable(gd);
                            tvColumn2.setBackgroundDrawable(gd);
                            tlStudent.addView(tblRow);
                        }
                        tblRow.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                //v.setBackgroundColor(Color.WHITE);
                                TableRow tablerow = (TableRow) v;
                                TextView rollNumber = (TextView) tablerow.getChildAt(0);
                                Intent i = new Intent(StudentActivity.this, IndividualStudentBehaviour.class);
                                i.putExtra("classname", classname);
                                i.putExtra("subjectname", subjectname);
                                i.putExtra("date", date);
                                i.putExtra("time", time);
                                i.putExtra("faceID", rollNumber.getText().toString());
                                i.putExtra("frameCount",frameCount);
                                startActivity(i);
                            }
                        });
                    }
                    tlStudent.setBackgroundResource(R.color.colorPrimary);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StudentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Send classname, lecturename, date, time and framecount to attendance.php
                Map<String, String> map = new HashMap<String, String>();
                map.put("classname", classname);
                map.put("lecturename", subjectname);
                map.put("date", date);
                map.put("time", time);
                map.put("framecount",frameCount);
                return map;
            }
        };
        MySingleton.getInstance(StudentActivity.this).addToRequestQueue(stringRequest);
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
