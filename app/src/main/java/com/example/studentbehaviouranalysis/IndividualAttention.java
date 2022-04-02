package com.example.studentbehaviouranalysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndividualAttention extends AppCompatActivity {

    //Declare inside class
    private List<ListItem2> listItems;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    String loginURL = "http://192.168.43.213/sba/individual_attention.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_attention);

        //Bind inside onCreate().
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        listItems = new ArrayList<>();

        //Create StringRequest for PHP script individual_attention.php
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("individual_attention");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        int expressionId = jsonObject2.getInt("expression_id");
                        String encodedString = jsonObject2.getString("face_image");
                        String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
                        byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        if (expressionId == 0) {
                            ListItem2 item2 = new ListItem2(jsonObject2.getString("face_id"), "Bored: " + String.format("%.4f", jsonObject2.getDouble("expression")) + "%", decodedBitmap);
                            listItems.add(item2);
                        }
                        if (expressionId == 1) {
                            ListItem2 item2 = new ListItem2(jsonObject2.getString("face_id"), "Sleepy: " + String.format("%.4f", jsonObject2.getDouble("expression")) + "%", decodedBitmap);
                            listItems.add(item2);
                        }
                    }
                    adapter = new MyAdapter2(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndividualAttention.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Send classname, lecturename, date, time and framecount to individual_attention.php
                Map<String, String> map = new HashMap<String, String>();
                map.put("classname", classname);
                map.put("lecturename", subjectname);
                map.put("date", date);
                map.put("time", time);
                map.put("framecount", frameCount);
                return map;
            }
        };

        MySingleton.getInstance(IndividualAttention.this).addToRequestQueue(stringRequest);
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
