package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

public class AdminLogin extends AppCompatActivity {

    EditText admin_id,password;
    Button login,register,b2;
    String adminId,passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_id= findViewById(R.id.id);
        password= findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminId=admin_id.getText().toString();
                passw=password.getText().toString();
                new AdminLogin.get(getApplicationContext()).execute();


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
                nameValuePairs.add(new BasicNameValuePair("admin_id",adminId));
                nameValuePairs.add(new BasicNameValuePair("password",passw));


                url="http://192.168.7.122/Adminlogin.php";


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

        @Override


        protected void onPostExecute(String result) {

            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


            if(result.trim().equals("success"))
            {

                Intent i = new Intent(getApplicationContext(), AdminHome.class);
                //i.putExtra("mobile_no",mob);
                startActivity(i);
                finish();

            } else
            {

                Toast.makeText(getApplicationContext(), "Wrong email or password !", Toast.LENGTH_LONG).show();

            }


        }
    }
}
