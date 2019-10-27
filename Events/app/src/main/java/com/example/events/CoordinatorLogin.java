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

public class CoordinatorLogin extends AppCompatActivity {

    EditText mobile,password;
    Button btn;
    String mob ,passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_login);

        mobile= findViewById(R.id.coordinator_mobile);
        password= findViewById(R.id.password);
        btn = findViewById(R.id.login);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mob=mobile.getText().toString();
                passw=password.getText().toString();
                new CoordinatorLogin.get(getApplicationContext()).execute();


            }
        });
    }

    public void new_coordinator(View v)
    {
        Intent intent = null;
        switch(v.getId()){
            case R.id.coordinator_register:
                intent = new Intent(this,CoordinatorRegister.class);
                break;
            /*case R.id.coordinator_register:
                intent = new Intent(this,CoordinatorLogin.class);
                break;*/

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

                url="http://192.168.7.122/CoordinatorLogin.php";


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
            if(result.equals("LoginSuccess"))
            {
                //sharedpref
                //startActivity(new Intent(getApplicationContext(),UserHome.class));
                Intent i = new Intent(getApplicationContext(), Add_Events.class);
                i.putExtra("mobile_no",mob);
                startActivity(i);
            }
            else {

            }

            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();


        }
    }
}
