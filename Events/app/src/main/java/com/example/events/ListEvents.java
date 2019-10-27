package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListEvents extends AppCompatActivity {
    String urladdress = "http://192.168.7.122/list_event.php";
    //String imageUri = "http://192.168.7.122/image/hackathon%20india.jpg";
    String[] eventname,location,description,event_date,event_time,event_id;
    String[] photo;
    String[] imagepath;
    BufferedInputStream is;
    String line, result;
    ImageView imageView;
    CardView card;
    ListView listView;
    ArrayList<String> al_event_id = new ArrayList<String>();
    ArrayList<String> al_eventname = new ArrayList<String>();
    ArrayList<String> al_photo = new ArrayList<String>();
    ArrayList<String> al_description = new ArrayList<String>();
    ArrayList<String> al_event_date = new ArrayList<String>();
    ArrayList<String> al_event_time = new ArrayList<String>();
    ArrayList<String> al_location = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_events);
        imageView = findViewById(R.id.imageView);
        card = findViewById(R.id.card_view);
        listView = findViewById(R.id.listview);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        try {
            new get(ListEvents.this).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }


       /* Picasso.with(this)
                .load(imageUri)
                .into(imageView);*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewEachEvents.class);
                i.putExtra("event_id",al_event_id.get(position));

                i.putExtra("eventname",al_eventname.get(position));
                i.putExtra("photo",al_photo.get(position));
                i.putExtra("description",al_description.get(position));
                i.putExtra("location",al_location.get(position));
                i.putExtra("event_date",al_event_date.get(position));
                i.putExtra("event_time",al_event_time.get(position));


                startActivity(i);
            }
        });

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
                //nameValuePairs.add(new BasicNameValuePair("eventname", eventname));
                nameValuePairs.add(new BasicNameValuePair("description", ""));

                 url = "http://192.168.7.122/list_event.php";
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                //httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

            } catch (NullPointerException e) {
            } catch (Exception e) {
                return e.toString();
            }
            return g;

        }

        //listView.setAdapter(customListView);
        protected void onPostExecute(String result) {
        Log.e("SMK",result);

            try {
                JSONArray js = new JSONArray(result);
                JSONObject jobj = null;
                event_id = new String[js.length()];

                eventname = new String[js.length()];
                photo = new String[js.length()];
                location = new String[js.length()];
                description = new String[js.length()];
                event_date = new String[js.length()];
                event_time = new String[js.length()];

                for (int i = 0; i < js.length(); i++) {
                    jobj = js.getJSONObject(i);
                    event_id[i] = jobj.getString("event_id");
                    eventname[i] = jobj.getString("eventname");
                    photo[i] = jobj.getString("photo");
                    location[i] = jobj.getString("location");
                    description[i] = jobj.getString("description");
                    event_time[i] = jobj.getString("event_time");
                    event_date[i] = jobj.getString("event_date");

                    al_event_id.add(jobj.getString("event_id"));
                    al_eventname.add(jobj.getString("eventname"));
                    al_photo.add(jobj.getString("photo"));
                    al_description.add(jobj.getString("description"));
                    al_location.add(jobj.getString("location"));
                    al_event_date.add(jobj.getString("event_date"));
                    al_event_time.add(jobj.getString("event_time"));

                }




                CustomAdapter adapter = new CustomAdapter();
                //setting the country_array to spinner

                listView.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }


    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return al_eventname.size();
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.cardview_inflator, null);

            ImageView thumbnail = (ImageView) convertView.findViewById(R.id.imageView);
            //ImageView loc=(ImageView)convertView.findViewById(R.id.loc);
            TextView eventname = (TextView) convertView.findViewById(R.id.info_text);
            eventname.setText(al_eventname.get(position));

            TextView description = (TextView) convertView.findViewById(R.id.description_text);
            description.setText(al_description.get(position));


          /*  TextView location = (TextView) convertView.findViewById(R.id.event_date_text);
            location.setText(al_event_date.get(position));

            TextView event_date = (TextView) convertView.findViewById(R.id.event_time_text);
            event_date.setText(al_event_time.get(position));

            TextView event_time = (TextView) convertView.findViewById(R.id.location_text);
            event_time.setText(al_location.get(position));*/



            //thumbnail.setImageResource(R.drawable.pp);
            // loc.setBackgroundResource(0);
            // loc.setImageResource(R.drawable.location);

            Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+al_photo.get(position)).into(thumbnail);
            return convertView;
        }



    }
}
