package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class CoordinatorRegister extends AppCompatActivity {

    EditText email_, mobile_, password_,name_;
    String email, password, mobile,token,name;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_register);

        mobile_ = findViewById(R.id.coordinator_mobile);
        email_ = findViewById(R.id.coordinator_email);
        password_ = findViewById(R.id.coordinator_password);
        reg = findViewById(R.id.register);
        name_ = findViewById(R.id.coordinator_name);

        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mobile = mobile_.getText().toString();
                email = email_.getText().toString();
                password = password_.getText().toString();
                name = name_.getText().toString();


                new CoordinatorRegister.get(getApplicationContext()).execute();
            }
        });
    }

    class get extends AsyncTask<String, String, String> {
        Context ccc;
        String url = "", g = "error";

        public get(Context applicationContext) {
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));
                nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                //nameValuePairs.add(new BasicNameValuePair("token", token));

                url = "http://192.168.7.122/CoordinatorRegister.php";

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

                // HttpEntity entity = response.getEntity();


            } catch (NullPointerException e) {
                Toast.makeText(ccc, e.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(ccc, e.toString(), Toast.LENGTH_LONG).show();
                return e.toString();
            }
            return g;

        }
        @Override


        protected void onPostExecute(String result) {

            if(result.equals("LoginSuccess"))
            {

                startActivity(new Intent(getApplicationContext(),CoordinatorLogin.class));
            }

            Toast.makeText(getApplicationContext(),result.toString(), Toast.LENGTH_LONG).show();


        }
    }


}
