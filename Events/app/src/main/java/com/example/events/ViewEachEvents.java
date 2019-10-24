package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ViewEachEvents extends AppCompatActivity {

    TextView event_name,venue,describe;
    ImageView pic;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_each_events);

        Intent intent = getIntent();
        String eventname = intent.getStringExtra("eventname");
        String photo = intent.getStringExtra("photo");
        url = "http://192.168.7.122/image/"+photo;
        Toast.makeText(getApplicationContext(),url, Toast.LENGTH_LONG).show();
        pic= findViewById(R.id.displayimage);

        Picasso.with(this)
                .load(url)
                .into(pic);

    }
}
