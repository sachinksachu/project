package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginScreen extends AppCompatActivity {

    EditText mobile,password;
    Button login,register,b2;
    String mob,passw,user_id;
    TextView newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mobile= findViewById(R.id.mobile);
        password= findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        //newuser = findViewById(R.id.user_register);
        b2 = findViewById(R.id.b2);

        SharedPreferences prefs = getSharedPreferences("login", MODE_PRIVATE);
        user_id= prefs.getString("user_id", "0");


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Category.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mob=mobile.getText().toString();
                passw=password.getText().toString();
                new get(getApplicationContext()).execute();


            }
        });


    }
    public void new_user(View v)
    {
        Intent intent = null;
        switch(v.getId()){
            case R.id.user_register:
                intent = new Intent(this,Registration.class);
                break;
            case R.id.coordinator_register:
                intent = new Intent(this,CoordinatorLogin.class);
                break;

        }
        if (null!=intent) startActivity(intent);
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
                nameValuePairs.add(new BasicNameValuePair("mobile",mob));
                nameValuePairs.add(new BasicNameValuePair("password",passw));

                /*$title=$_REQUEST["title"];
                $notice=$_REQUEST["notice"];
                $date=$_REQUEST["date"];
                $create_date=$_REQUEST["c_date"];
                $teacherID=$_REQUEST["teacherID"];
                $classesID=$_REQUEST["classID"];*/
                //hedder_no=Integer.parseInt(arg0[1]);

                url="http://192.168.7.122/login.php";


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
            //  hlist.removeAllViewsInLayout();

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                user_id = result;
                Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();

                if (user_id.equals("0"))
                {
                    Toast.makeText(getApplicationContext(), "Wrong email or password !", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("user_id", user_id);
                    editor.apply();
                } else
                {

                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("user_id", user_id);
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(), ListEvents.class);
                    i.putExtra("mobile_no",mob);
                    startActivity(i);
                    finish();
                }








         /*   if(result.equals("LoginSuccess"))
            {
                //sharedpref
                //Toast.makeText(getApplicationContext(),mob, Toast.LENGTH_LONG).show();


                //startActivity(new Intent(getApplicationContext(),UserHome.class));
                Intent i = new Intent(getApplicationContext(), ListEvents.class);
                i.putExtra("mobile_no",mob);
                startActivity(i);
                finish();
            }
            else {

            }

            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
        */

        }
    }


}

