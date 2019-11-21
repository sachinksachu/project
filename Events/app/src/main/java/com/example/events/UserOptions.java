package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserOptions extends AppCompatActivity {

    Button search,myEvents,preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_options);

        preference = findViewById(R.id.bCategory);
        search = findViewById(R.id.bSearch);
        myEvents = findViewById(R.id.bMyevents);

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

        myEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }
        });
    }
}
