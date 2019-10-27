package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewEachEvents extends AppCompatActivity {

    TextView event_name,venue,describe,event_id_vu;
    ImageView pic;
    String url,event_id,eventname,photo,desc;
    Button book;

    double latitude, longitude;
    TextView city_name;
    String address,city;



    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_each_events);

        Intent intent = getIntent();
        event_id = intent.getStringExtra("event_id");
        eventname = intent.getStringExtra("eventname");
         desc = intent.getStringExtra("description");
        photo = intent.getStringExtra("photo");
        book = findViewById(R.id.Book_Ticket);
        event_name = findViewById(R.id.eventname);
        event_name.setText(eventname);
        describe = findViewById(R.id.description);
        describe.setText(desc);

        url = "http://192.168.7.122/image/"+photo;
       // Toast.makeText(getApplicationContext(),url, Toast.LENGTH_LONG).show();
        pic= findViewById(R.id.displayimage);


        Picasso.with(this)
                .load(url)
                .into(pic);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Booking.class);
                i.putExtra("event_id",event_id);
                Toast.makeText(getApplicationContext(),photo, Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });

    }
}
