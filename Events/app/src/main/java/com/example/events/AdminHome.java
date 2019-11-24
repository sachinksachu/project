package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {

    Button eventList,Coordinators,approval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        eventList= findViewById(R.id.eventlist);
        Coordinators= findViewById(R.id.coordinators);
        approval = findViewById(R.id.approve);
        //register = findViewById(R.id.register);
        //register = findViewById(R.id.register);
        //register = findViewById(R.id.register);
        //register = findViewById(R.id.register);

        eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminListEvents.class);
                startActivity(i);
            }
        });

        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ApproveEvents.class);
                startActivity(i);
            }
        });

    }
}
