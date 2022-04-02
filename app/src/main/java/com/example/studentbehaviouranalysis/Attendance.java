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

public class Attendance extends AppCompatActivity {

    //Declare inside class
    TableLayout tlAttendance;
    TextView tvAttendance;
    String loginURL = "http://192.168.43.213/sba/attendance.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        //Bind inside onCreate()
        tlAttendance = (TableLayout) findViewById(R.id.tlAttendance);
        tvAttendance = (TextView)findViewById(R.id.tvAttendance);

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
        tvHeadColumn2.setText("Attendance");
        tvHeadColumn2.setPadding(20, 20, 20, 20);
        tvHeadColumn2.setTextColor(Color.WHITE);
        tvHeadColumn2.setGravity(Gravity.CENTER);
        tblHeadRow.addView(tvHeadColumn2);
        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2, Color.BLACK);
        tblHeadRow.setBackgroundDrawable(gd);
        tvHeadColumn.setBackgroundDrawable(gd);
        tvHeadColumn2.setBackgroundDrawable(gd);
        tlAttendance.setBackgroundDrawable(gd);
        tlAttendance.addView(tblHeadRow);

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
                progressDialog.dismiss();
                String rollNumber = "";
                String[] rollNumbers;
                int attendance[] = new int[20];
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
                    int count = 0;
                    for (int i = 0; i < 20; i++) {
                        TableRow tblRow = new TableRow(Attendance.this);
                        TableRow.LayoutParams params4 = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 10f);
                        tblRow.setLayoutParams(params4);
                        TextView tvColumn = new TextView(Attendance.this);
                        TableRow.LayoutParams params5 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
                        tvColumn.setLayoutParams(params5);
                        tvColumn.setText("" + (i + 1));
                        tvColumn.setPadding(20, 20, 20, 20);
                        tvColumn.setTextColor(Color.WHITE);
                        tvColumn.setGravity(Gravity.CENTER);
                        tblRow.addView(tvColumn);

                        TextView tvColumn2 = new TextView(Attendance.this);
                        TableRow.LayoutParams params6 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 5f);
                        tvColumn2.setLayoutParams(params6);
                        if (attendance[i] == 1) {
                            tvColumn2.setText("Present");
                            count++;
                        } else
                            tvColumn2.setText("Absent");
                        tvColumn2.setPadding(20, 20, 20, 20);
                        tvColumn2.setTextColor(Color.WHITE);
                        tvColumn2.setGravity(Gravity.CENTER);
                        tblRow.addView(tvColumn2);
                        GradientDrawable gd = new GradientDrawable();
                        gd.setStroke(2, Color.BLACK);
                        tblRow.setBackgroundDrawable(gd);
                        tvColumn.setBackgroundDrawable(gd);
                        tvColumn2.setBackgroundDrawable(gd);
                        tlAttendance.addView(tblRow);
                    }
                    tlAttendance.setBackgroundResource(R.color.colorPrimary);
                    tvAttendance.setText("Number of students present: " + count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Attendance.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Send classname, lecturename, date, time and framecountto attendance.php
                Map<String, String> map = new HashMap<String, String>();
                map.put("classname", classname);
                map.put("lecturename", subjectname);
                map.put("date", date);
                map.put("time", time);
                map.put("framecount", frameCount);
                return map;
            }
        };

        MySingleton.getInstance(Attendance.this).addToRequestQueue(stringRequest);
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
