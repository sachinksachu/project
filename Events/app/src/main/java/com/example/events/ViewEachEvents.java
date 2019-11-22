package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

public class ViewEachEvents extends AppCompatActivity {

    String[] eventname,location,description,event_date,event_time,event_id;

    String[] imagepath;
    BufferedInputStream is;
    String line, result,mobile_signin;
    ImageView imageView;
    CardView card;
    ListView listView;
    String event_id_intent,url;
    ArrayList<String> al_event_id = new ArrayList<String>();
    ArrayList<String> al_eventname = new ArrayList<String>();
    ArrayList<String> al_photo = new ArrayList<String>();
    ArrayList<String> al_description = new ArrayList<String>();
    ArrayList<String> al_event_date = new ArrayList<String>();
    ArrayList<String> al_event_time = new ArrayList<String>();
    ArrayList<String> al_location = new ArrayList<String>();

    TextView eventid ,photo ,event_name ,describe, venue ,time,date,seat;
    Button book;
    String pic;


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
        event_id_intent = intent.getStringExtra("event_id");
        //eventname = intent.getStringExtra("eventname");
         //desc = intent.getStringExtra("description");
        //loc = intent.getStringExtra("location");
        //da_te = intent.getStringExtra("event_date");
        //ti_me = intent.getStringExtra("event_time");
        //Toast.makeText(getApplicationContext(),desc, Toast.LENGTH_LONG).show();

       eventid=findViewById(R.id.event_id);
       // eventid.setText(event_id);

        //photo = intent.getStringExtra("photo");
        book = findViewById(R.id.Book_Ticket);

        event_name = findViewById(R.id.eventname2);
        //event_name.setText(eventname);

        describe = findViewById(R.id.description);
        //describe.setText(desc);

        venue = findViewById(R.id.location);
        //venue.setText(loc);

        date = findViewById(R.id.event_date);
       // date.setText(da_te);

        time = findViewById(R.id.event_time);
        //time.setText(ti_me);

        seat = findViewById(R.id.seat_id);

       // url = "http://192.168.7.122/image/"+photo;
       // Toast.makeText(getApplicationContext(),url, Toast.LENGTH_LONG).show();
        imageView= findViewById(R.id.displayimage);


        Picasso.with(this)
                .load(url)
                .into(imageView);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Booking.class);
                i.putExtra("event_id",event_id_intent);
               // Toast.makeText(getApplicationContext(),photo, Toast.LENGTH_SHORT).show();
                startActivity(i);

            }
        });

        try {
            Log.e("SMK3","fg");
            new ViewEachEvents.get(ViewEachEvents.this).execute();
        } catch (JSONException e) {
            Log.e("SMK3",e.toString());
        }

    }

    class get extends AsyncTask<String, String, String> {

        Context ccc;
        String url = "";

        get(Context c) throws JSONException {

            ccc = c;
        }

        String g = "error";

        @Override
        protected String doInBackground(String... arg0) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("event_id", event_id_intent));
                //nameValuePairs.add(new BasicNameValuePair("description", ""));

                url = "http://192.168.7.122/ViewEachEvent.php";

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

            } catch (NullPointerException e) {
            } catch (Exception e) {
                return e.toString();
            }
            return g;

        }

        protected void onPostExecute(String result) {
            Log.e("SMK2",result);
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

            try {
                JSONArray js = new JSONArray(result);



                    JSONObject jobj = js.getJSONObject(0);

                event_name.setText(jobj.getString("eventname"));
                describe.setText(jobj.getString("description"));
                pic = jobj.getString("photo");
                Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+pic).into(imageView);
                venue.setText(jobj.getString("location"));
                date.setText(jobj.getString("event_date"));
                time.setText(jobj.getString("event_time"));
                seat.setText(jobj.getString("seat"));
                   // event_id[i] = jobj.getString("event_id");

                    //photo[i] = jobj.getString("photo");
                    //location[i] = jobj.getString("location");
                    //description[i] = jobj.getString("description");
                    //event_time[i] = jobj.getString("event_time");
                    //event_date[i] = jobj.getString("event_date");




            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }
}
