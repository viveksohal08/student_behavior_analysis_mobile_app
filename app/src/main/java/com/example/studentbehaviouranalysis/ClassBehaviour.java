package com.example.studentbehaviouranalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassBehaviour extends AppCompatActivity {

    //Declare inside class
    TextView tvClassBehaviour;
    PieChart pieChart;
    String loginURL = "http://192.168.43.213/sba/class_behaviour.php";
    ProgressDialog progressDialog;
    double joyful, bored, attentive, sleepy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_behaviour);

        //Bind inside onCreate()
        tvClassBehaviour = (TextView) findViewById(R.id.tvClassBehaviour);
        pieChart = (PieChart) findViewById(R.id.pieChart);

        //HomeButton
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

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

        //Create StringRequest for PHP script class_behaviour.php
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("class_behaviour");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        joyful = jsonObject2.getDouble("joyful");
                        bored = jsonObject2.getDouble("bored");
                        attentive = jsonObject2.getDouble("attentive");
                        sleepy = jsonObject2.getDouble("sleepy");
                    }
                    tvClassBehaviour.setText(String.format("Joyful: %.4f", joyful) + "%\n" + String.format("Bored: %.4f", bored) + "%\n"
                            + String.format("Attentive: %.4f", attentive) + "%\n" + String.format("Sleepy: %.4f", sleepy) + "%\n"
                    );

                    pieChart.setUsePercentValues(true);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setExtraOffsets(5, 0, 5, 5);
                    //The value of setDragDecelerationFrictionCoef() method decides the speed with which the pieChart rotates
                    pieChart.setDragDecelerationFrictionCoef(0.99f);
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setHoleColor(Color.WHITE);
                    //The value of setTransparentCircleRadius() method gives 3D effect to pieChart
                    pieChart.setTransparentCircleRadius(61f);

                    //Description for pieChart
                    Description description = new Description();
                    description.setTextSize(15f);
                    description.setText("Pie Chart for analysis of Class Behaviour");
                    pieChart.setDescription(description);

                    //Animation of pieChart
                    pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                    ArrayList<PieEntry> values = new ArrayList<>();
                    if (joyful > 0.0)
                        values.add(new PieEntry((float) joyful, "Joyful"));
                    if (bored > 0.0)
                        values.add(new PieEntry((float) bored, "Bored"));
                    if (attentive > 0.0)
                        values.add(new PieEntry((float) attentive, "Attentive"));
                    if (sleepy > 0.0)
                        values.add(new PieEntry((float) sleepy, "Sleepy"));

                    PieDataSet dataSet = new PieDataSet(values, "Emotion");
                    //The value of setSliceSpace() method decides the space between sections of pieChart
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(5f);
                    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                    PieData data = new PieData(dataSet);
                    data.setValueTextSize(20f);
                    data.setValueTextColor(Color.BLACK);

                    pieChart.setData(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ClassBehaviour.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Send classname, lecturename, date, time and framecount to class_behaviour.php
                Map<String, String> map = new HashMap<String, String>();
                map.put("classname", classname);
                map.put("lecturename", subjectname);
                map.put("date", date);
                map.put("time", time);
                map.put("framecount", frameCount);
                return map;
            }
        };

        MySingleton.getInstance(ClassBehaviour.this).addToRequestQueue(stringRequest);
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
