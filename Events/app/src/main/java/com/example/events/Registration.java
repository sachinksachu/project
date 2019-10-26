package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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

public class Registration extends AppCompatActivity {

    private static final int REQUEST_CODE_EMAIL = 1;
    EditText email_, mobile_, password_;
    String email, password, mobile,token;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("SSSSMMMKKK", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("SSSSMMMKKK", token);
                        Toast.makeText(Registration.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        mobile_ = findViewById(R.id.reg_mobile);
        email_ = findViewById(R.id.reg_email);
        password_ = findViewById(R.id.reg_password);
        reg = findViewById(R.id.register);

        try {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
            startActivityForResult(intent, REQUEST_CODE_EMAIL);
        } catch (ActivityNotFoundException e) {
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobile = mobile_.getText().toString();
                email = email_.getText().toString();
                password = password_.getText().toString();

                new Registration.get(getApplicationContext()).execute();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            email_.setText(accountName);
        }
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
                nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                nameValuePairs.add(new BasicNameValuePair("token", token));

                url = "http://192.168.7.122/add_users.php";

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

                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }

            Toast.makeText(getApplicationContext(),result.toString(), Toast.LENGTH_LONG).show();


        }
    }
}


