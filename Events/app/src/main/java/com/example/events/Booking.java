package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Booking extends AppCompatActivity {

    TextView num;
    Button add_,minus_,book;
    int counter = 0;
    String event_id ,userid="99";
    int event_id_to_int, user_id_to_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        event_id = intent.getStringExtra("event_id");
        event_id_to_int = Integer.parseInt(event_id);
        user_id_to_int = Integer.parseInt(userid);
        Toast.makeText(getApplicationContext(),event_id, Toast.LENGTH_LONG).show();
        num = findViewById(R.id.number);
        add_ = findViewById(R.id.add);
        minus_ = findViewById(R.id.minus);
        book = findViewById(R.id.book);

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

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Booking.get(getApplicationContext()).execute();
            }
        });
    }

    class get extends AsyncTask<String, String, String> {

        Context ccc;
        String url="";
        get(Context c) {

            ccc = c;
        }

        String g="error";

        @Override
        protected String doInBackground(String... arg0) {
            try {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("event_id",event_id));
                nameValuePairs.add(new BasicNameValuePair("user_id",userid));

                url="http://192.168.7.122/booking.php";


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

                // HttpEntity entity = response.getEntity();


            } catch (NullPointerException e) {

            } catch (Exception e) {

                return e.toString();
            }
            return g;

        }

        @Override


        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();


        }
    }
}
