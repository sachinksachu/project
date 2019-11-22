package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

public class Coordinator_ViewEachEvents extends AppCompatActivity {
    String url,event_id_intent;
    String[] eventname,location,description,event_date,event_time,event_id;
    String[] user_id,username,user_mobile,user_email;
    String[] photo;
    String[] imagepath;
    ImageView imageView;
    TextView name,mobile,email;
    CardView card;
    ListView listView;
    ArrayList<String> al_user_id = new ArrayList<String>();
    ArrayList<String> al_name = new ArrayList<String>();
    ArrayList<String> al_mobile = new ArrayList<String>();
    ArrayList<String> al_email = new ArrayList<String>();
    ArrayList<String> al_event_date = new ArrayList<String>();
    ArrayList<String> al_event_time = new ArrayList<String>();
    ArrayList<String> al_location = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator__view_each_events);

        //name=findViewById(R.id.name);


       // mobile = findViewById(R.id.mobile);


       // email = findViewById(R.id.email);

        card = findViewById(R.id.card2);
        listView = findViewById(R.id.listview_Coor_view_each_event);



        Intent intent = getIntent();
        event_id_intent = intent.getStringExtra("event_id");
        Toast.makeText(getApplicationContext(),"EventID"+ event_id_intent, Toast.LENGTH_SHORT).show();

        try {
            Log.e("SMK3","fg");
            new Coordinator_ViewEachEvents.get(Coordinator_ViewEachEvents.this).execute();
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

                url = "http://192.168.7.122/CoordinatorViewEachEvent.php";

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
                JSONObject jobj = null;
                //user_id = new String[js.length()];
                username = new String[js.length()];
                user_mobile = new String[js.length()];
                user_email = new String[js.length()];
                //description = new String[js.length()];
                //event_date = new String[js.length()];
                //event_time = new String[js.length()];

                for (int i = 0; i < js.length(); i++) {
                    jobj = js.getJSONObject(i);
                    //user_id[i] = jobj.getString("user_id");
                    username[i] = jobj.getString("name");
                    user_mobile[i] = jobj.getString("mobile");
                    user_email[i] = jobj.getString("email");
                    //description[i] = jobj.getString("description");
                    //event_time[i] = jobj.getString("event_time");
                    //event_date[i] = jobj.getString("event_date");

                    //al_user_id.add(jobj.getString("user_id"));
                    al_name.add(jobj.getString("name"));
                    al_mobile.add(jobj.getString("mobile"));
                    al_email.add(jobj.getString("email"));
                    //al_location.add(jobj.getString("location"));
                    //al_event_date.add(jobj.getString("event_date"));
                    //al_event_time.add(jobj.getString("event_time"));

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
            return al_name.size();
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

            convertView = getLayoutInflater().inflate(R.layout.card2, null);

            //ImageView thumbnail = (ImageView) convertView.findViewById(R.id.imageView);
            //ImageView loc=(ImageView)convertView.findViewById(R.id.loc);
             name = (TextView) convertView.findViewById(R.id.name);
            name.setText(al_name.get(position));
            Toast.makeText(getApplicationContext(),al_name.get(position), Toast.LENGTH_LONG).show();

             mobile = (TextView) convertView.findViewById(R.id.mobile);
            mobile.setText(al_mobile.get(position));

           email = (TextView) convertView.findViewById(R.id.email);
            email.setText(al_email.get(position));

            //TextView event_date = (TextView) convertView.findViewById(R.id.event_time_text);
            //event_date.setText(al_event_time.get(position));





            //thumbnail.setImageResource(R.drawable.pp);
            // loc.setBackgroundResource(0);
            // loc.setImageResource(R.drawable.location);

           // Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+al_photo.get(position)).into(thumbnail);
            return convertView;
        }



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.coord_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.update:
                Intent i = new Intent(getApplicationContext(), UpdateEvents.class);
                i.putExtra("event_id",event_id_intent);
                startActivity(i);
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.update:
                Intent i = new Intent(getApplicationContext(), UpdateEvents.class);
                i.putExtra("event_id",event_id_intent);
                startActivity(i);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
