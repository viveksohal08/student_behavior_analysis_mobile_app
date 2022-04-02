package com.example.studentbehaviouranalysis;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AdminLogin extends AppCompatActivity {

    //Declare inside class
    EditText etUsername, etPassword;
    Button btnLogin;
    String loginURL = "http://192.168.43.213/sba/admin_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        //Bind inside onCreate()
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //HomeButton
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                if (username.length() == 0) {
                    etUsername.setError("Please enter username");
                    etUsername.requestFocus();
                    return;
                }
                if (password.length() == 0) {
                    etPassword.setError("Please enter password");
                    etPassword.requestFocus();
                    return;
                }

                //Create StringRequest for PHP script admin_login.php
                StringRequest stringRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Login unsuccessful.")) {
                            Toast.makeText(AdminLogin.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdminLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdminLogin.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Send username and password to admin_login.php
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("username", username);
                        map.put("password", password);
                        return map;
                    }
                };

                MySingleton.getInstance(AdminLogin.this).addToRequestQueue(stringRequest);
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