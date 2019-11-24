package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserOptions extends AppCompatActivity {

    Button search,myBookings,preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        preference = findViewById(R.id.bCategory);
        search = findViewById(R.id.bSearch);
        myBookings = findViewById(R.id.bMyevents);

        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(new Intent(getApplicationContext(),CategoryPref.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }
        });

        myBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(new Intent(getApplicationContext(),MyBookings.class));
            }
        });
    }
}
