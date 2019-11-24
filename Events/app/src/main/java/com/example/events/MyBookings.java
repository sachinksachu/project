package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

public class MyBookings extends AppCompatActivity {

    String[] eventname,location,description,event_date,event_time,event_id;
    String[] photo;
    String[] imagepath;
    BufferedInputStream is;
    String line, result,user_id;
    ImageView imageView;
    CardView card_myBookings;
    ListView listView_myBookings;
    ArrayList<String> al_event_id = new ArrayList<String>();
    ArrayList<String> al_eventname = new ArrayList<String>();
    ArrayList<String> al_photo = new ArrayList<String>();
    ArrayList<String> al_description = new ArrayList<String>();
    ArrayList<String> al_event_date = new ArrayList<String>();
    ArrayList<String> al_event_time = new ArrayList<String>();
    ArrayList<String> al_location = new ArrayList<String>();
    String user_id_shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        card_myBookings = findViewById(R.id.card_view_mybooking);
        listView_myBookings = findViewById(R.id.listview_eventList);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        user_id_shared= prefs.getString("user_id", "0");
        Toast.makeText(this,user_id_shared,Toast.LENGTH_LONG).show();

        try {
            new MyBookings.get(MyBookings.this).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class get extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        Context ccc;
        String url = "";

        get(Context c) throws JSONException {

            ccc = c;
            dialog = new ProgressDialog(MyBookings.this);
        }

        String g = "error";

        // @Override
        //protected void onPreExecute() {
        //    dialog.setMessage("Loading please wait.");
        //    dialog.show();
        //}

        @Override
        protected String doInBackground(String... arg0) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("user_id", user_id_shared));
                //nameValuePairs.add(new BasicNameValuePair("description", ""));

                url = "http://192.168.7.122/MyBookings.php";

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




                MyBookings.CustomAdapter adapter = new MyBookings.CustomAdapter();
                //setting the country_array to spinner

                listView_myBookings.setAdapter(adapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
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

            convertView = getLayoutInflater().inflate(R.layout.card_mybooking, null);

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

            */



            //thumbnail.setImageResource(R.drawable.pp);
            // loc.setBackgroundResource(0);
            // loc.setImageResource(R.drawable.location);

            Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+al_photo.get(position)).into(thumbnail);
            return convertView;
        }



    }


}
