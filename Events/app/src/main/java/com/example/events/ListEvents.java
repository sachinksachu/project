package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ListEvents extends AppCompatActivity {
    String imageUri = "https://i.imgur.com/tGbaZCY.jpg";

    ImageView imageView;
    CardView card;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        imageView = findViewById(R.id.imageView);
        card = findViewById(R.id.card_view);

        Picasso.with(this)
                .load(imageUri)
                .into(imageView);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ViewEachEvents.class);
                startActivity(i);
            }
        });
    }
}
