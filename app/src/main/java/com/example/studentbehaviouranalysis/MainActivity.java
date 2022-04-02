package com.example.studentbehaviouranalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Declare inside class
    Button btnTeacherLogin, btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind inside onCreate()
        btnTeacherLogin = (Button) findViewById(R.id.btnTeacherLogin);
        btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);

        btnTeacherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TeacherLogin.class);
                startActivity(i);
            }
        });

        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(i);
            }
        });
    }
}
