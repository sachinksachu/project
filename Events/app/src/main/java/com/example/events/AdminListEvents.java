package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class AdminListEvents extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    TextView city_name;
    String address,city;

   // String[] eventname,location,description,event_date,event_time,event_id;
    String[] photo;
    String[] imagepath;
    BufferedInputStream is;
    String line, result,mobile_signin;



    ListView listView;
    List<String> mDataset=new ArrayList<String>();
    List<String> name=new ArrayList<String>();
    List<String> photo_view=new ArrayList<String>();
    List<String> location=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_events);

        //SharedPreferences prefs = getSharedPreferences("coord_login", MODE_PRIVATE);
        //mobile_signin = prefs.getString("coordinator_mobile", null);
        //Toast.makeText(this,"MOBILE "+mobile_signin,Toast.LENGTH_LONG).show();


        listView = findViewById(R.id.listview);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminListEvents.this, LinearLayoutManager.HORIZONTAL, true));
        layoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        try {
            Log.e("SMK3","fg");
            new AdminListEvents.get(AdminListEvents.this).execute();
        } catch (JSONException e) {
            Log.e("SMK3",e.toString());
        }

/*        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewEachEvents.class);
                i.putExtra("event_id",mDataset.get(position));
               // i.putExtra("eventname",al_eventname.get(position));
                //i.putExtra("photo",al_photo.get(position));
                //i.putExtra("description",al_description.get(position));
                //i.putExtra("location",al_location.get(position));
               // i.putExtra("event_date",al_event_date.get(position));
                //i.putExtra("event_time",al_event_time.get(position));


                startActivity(i);
            }
        });*/
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
                //nameValuePairs.add(new BasicNameValuePair("coord_id", mobile_signin));
                //nameValuePairs.add(new BasicNameValuePair("description", ""));

                url = "http://192.168.7.122/test_event.php";

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
            Log.e("SMK2",result);
            //Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

            try {
                JSONArray js = new JSONArray(result);
                JSONObject jobj = null;


             /*   for (int i = 0; i < js.length(); i++) {
                    jobj = js.getJSONObject(i);
                    mDataset.add(
                            jobj.getString("eventname"),(1,jobj.getString("photo"),
                    2,jobj.getString("location")),
                            3,jobj.getString("description"),
                            4,jobj.getString("event_time"),
                    5,jobj.getString("event_date"));

                    eventname[i] = jobj.getString("eventname");
                    photo[i] = jobj.getString("photo");
                    location[i] = jobj.getString("location");
                    description[i] = jobj.getString("description");
                    event_time[i] = jobj.getString("event_time");
                    event_date[i] = jobj.getString("event_date");

                  //  al_event_id.add(jobj.getString("event_id"));
                  //  al_eventname.add(jobj.getString("eventname"));
                  //  al_photo.add(jobj.getString("photo"));
                  //  al_description.add(jobj.getString("description"));
                  //  al_location.add(jobj.getString("location"));
                  //  al_event_date.add(jobj.getString("event_date"));
                  //  al_event_time.add(jobj.getString("event_time"));

                }*/
                for (int i = 0; i < js.length(); i++)
                {
                    JSONObject jsonobject = js.getJSONObject(i);

                    mDataset.add(jsonobject.getString("eventname"));
                    name.add(jsonobject.getString("eventname"));
                    location.add(jsonobject.getString("location"));

                    photo_view.add(jsonobject.getString("photo"));
                }




                //AdminListEvents.CustomAdapter adapter = new AdminListEvents.CustomAdapter();
                //setting the country_array to spinner
                mAdapter = new MyAdapter(mDataset);

                recyclerView.setAdapter(mAdapter);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public  class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            ImageView imageView;
            CardView card;
            TextView eventname,location_view;
           
            public MyViewHolder(@NonNull View itemView ) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);

                //ImageView loc=(ImageView)convertView.findViewById(R.id.loc);
                eventname = itemView.findViewById(R.id.info_text);
                location_view = itemView.findViewById(R.id.location_text);


                TextView description = itemView.findViewById(R.id.description_text);
                card= itemView.findViewById(R.id.card_view);


            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<String> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            View view;
            LayoutInflater mLayoutInflater=LayoutInflater.from(parent.getContext());
            view=mLayoutInflater.inflate(R.layout.cardview_inflator,parent,false);
            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.textView.setText(mDataset[position]);

            holder.eventname.setText(mDataset.get(position));
            holder.location_view.setText(location.get(position));


            Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+photo_view.get(position)).into(holder.imageView);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
                   //
        }
    }


   /* class CustomAdapter extends BaseAdapter {
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

          /*  Picasso.with(getApplicationContext()).load("http://192.168.7.122/image/"+al_photo.get(position)).into(thumbnail);
            return convertView;
        } */



  //  }
}
