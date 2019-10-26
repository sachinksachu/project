package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Booking extends AppCompatActivity {

    TextView num;
    Button add_,minus_;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        num = findViewById(R.id.number);
        add_ = findViewById(R.id.add);
        minus_ = findViewById(R.id.minus);

        add_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                counter = counter + 1;
                num.setText(String.valueOf(counter));

            }
        });

        minus_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter!= 0) {
                    counter = counter - 1;
                    num.setText(String.valueOf(counter));
                }
            }
        });
    }
}
