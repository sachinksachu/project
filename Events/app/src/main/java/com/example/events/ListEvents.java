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
    String[] eventname;
    String[] photo;
    String[] imagepath;
    BufferedInputStream is;
    String line, result;
    ImageView imageView;
    CardView card;
    ListView listView;
    ArrayList<String> al_eventname = new ArrayList<String>();
    ArrayList<String> al_photo = new ArrayList<String>();

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
                i.putExtra("eventname",al_eventname.get(position));
                i.putExtra("photo",al_photo.get(position));
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
                eventname = new String[js.length()];
                photo = new String[js.length()];

                for (int i = 0; i < js.length(); i++) {
                    jobj = js.getJSONObject(i);
                    eventname[i] = jobj.getString("eventname");
                    photo[i] = jobj.getString("photo");
                    al_eventname.add(jobj.getString("eventname"));
                    al_photo.add(jobj.getString("photo"));

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
            TextView ownername = (TextView) convertView.findViewById(R.id.info_text);
            ownername.setText(al_eventname.get(position));
            //thumbnail.setImageResource(R.drawable.pp);
            // loc.setBackgroundResource(0);
            // loc.setImageResource(R.drawable.location);

            Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+al_photo.get(position)).into(thumbnail);
            return convertView;
        }



    }
}
