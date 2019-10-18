package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Events extends AppCompatActivity {
    EditText event_name;
    EditText description;
    int mYear,mMonth,mDay;
    String _date,eventname, describe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__events);

         event_name = findViewById(R.id.event_name);
         description = findViewById(R.id.description);
        Button pickdate = findViewById(R.id.pickdate);
        Button add = findViewById(R.id.add);

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventname = event_name.getText().toString();
                describe =description.getText().toString();
                new Add_Events.get(getApplicationContext()).execute();


            }
        });
    }
    private void datePicker(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
         mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        _date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        //*************Call Time Picker Here ********************
                        Toast.makeText(getApplicationContext(),_date, Toast.LENGTH_LONG).show();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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
                nameValuePairs.add(new BasicNameValuePair("event_name",eventname));
                nameValuePairs.add(new BasicNameValuePair("description",describe));
                nameValuePairs.add(new BasicNameValuePair("date",_date));

                url="http://192.168.7.122/add_event.php";


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

                // HttpEntity entity = response.getEntity();


            } catch (NullPointerException e) {
                //Toast.makeText(ccc, e.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
//		Toast.makeText(ccc,e.toString(), Toast.LENGTH_LONG).show();
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
