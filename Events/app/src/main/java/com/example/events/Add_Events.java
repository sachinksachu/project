package com.example.events;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.SSLContext;

public class Add_Events extends AppCompatActivity {
    TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private static final int PICK_IMAGE_REQUEST =1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    EditText event_name;
    EditText describe;
    SearchView searchView;
    Button addimage,pickdate,add,picktime;
    int mYear,mMonth,mDay;
    String _date,eventname, description,location,_time;
    ImageView imageView;
    Uri selectedImage;
    Uri filePath;
    private Bitmap bitmap;
    TextView mTimePicker;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    private SimpleCursorAdapter myAdapter;
    private String[] strArrData = {"No Suggestions"};
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__events);

        requestStoragePermission();

        event_name = findViewById(R.id.nameevent);
        describe = findViewById(R.id.event_desc);
        searchView = findViewById(R.id.place);

        pickdate = findViewById(R.id.Choosedate);
        picktime = findViewById(R.id.Choosetime);
        add = findViewById(R.id.buttonUpload);
        addimage = findViewById(R.id.UploadImage);
        imageView = findViewById(R.id.imageView);
        mTimePicker =findViewById(R.id.Timetext);

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });
        picktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventname = event_name.getText().toString();
                description = describe.getText().toString();

                _time = mTimePicker.getText().toString();


                new Add_Events.get(getApplicationContext()).execute();


            }
        });

















        final String[] from = new String[] {"fishName"};
        final int[] to = new int[] {android.R.id.text1};



        myAdapter = new SimpleCursorAdapter(Add_Events.this, android.R.layout.simple_spinner_dropdown_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        new AsyncFetch(Add_Events.this).execute();

        SearchManager searchManager = (SearchManager) Add_Events.this.getSystemService(Context.SEARCH_SERVICE);



        searchView.setSearchableInfo(searchManager.getSearchableInfo(Add_Events.this.getComponentName()));
        searchView.setIconified(false);
        searchView.setSuggestionsAdapter(myAdapter);

        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {

                // Add clicked text to search box
                CursorAdapter ca = searchView.getSuggestionsAdapter();
                Cursor cursor = ca.getCursor();
                cursor.moveToPosition(position);
                searchView.setQuery(cursor.getString(cursor.getColumnIndex("fishName")),false);
                //text1.setQuery(cursor.getString(cursor.getColumnIndex("fishName")),false);
                location=(cursor.getString(cursor.getColumnIndex("fishName")));
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {


                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                // Filter data
                final MatrixCursor mc = new MatrixCursor(new String[]{ BaseColumns._ID, "fishName" });
                for (int i=0; i<strArrData.length; i++) {
                    if (strArrData[i].toLowerCase().startsWith(s.toLowerCase()))
                        mc.addRow(new Object[] {i, strArrData[i]});
                }
                myAdapter.changeCursor(mc);
                return false;
            }
        });






















        /*Places.initialize(getApplicationContext(), "AIzaSyBakXTYRgbnWoea9JF-7rDTcOcdwj82HmE");
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //placename.setText(place.getName()+","+place.getId());
                placename.setText(place.getName());
               // Log.i( "Place: " + place.getName() + ", " + place.getId());
                Toast.makeText(getApplicationContext(),placename.getText(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Status status) {
                placename.setText(status.toString());
            }
        });*/



    }



   /* public void uploadMultipart()
    {
        name = "hello";
        String path = getPath(filePath);
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

   private void timePicker()
   {
       calendar = Calendar.getInstance();
       currentHour = calendar.get(Calendar.HOUR_OF_DAY);
       currentMinute = calendar.get(Calendar.MINUTE);

       timePickerDialog = new TimePickerDialog(Add_Events.this, new TimePickerDialog.OnTimeSetListener() {
           @Override
           public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
               if (hourOfDay >= 12) {
                   amPm = "PM";
               } else {
                   amPm = "AM";
               }
               mTimePicker.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
           }
       }, currentHour, currentMinute, false);

       timePickerDialog.show();
   }


    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        assert cursor != null;
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
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

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("eventname",eventname));
                nameValuePairs.add(new BasicNameValuePair("description",description));
                nameValuePairs.add(new BasicNameValuePair("location",location));
                nameValuePairs.add(new BasicNameValuePair("event_date",_date));
                nameValuePairs.add(new BasicNameValuePair("event_time",_time));
                nameValuePairs.add(new BasicNameValuePair("photo",encodedImage));
                url="http://192.168.7.122/add.php";


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                g = httpClient.execute(httpPost, responseHandler);

                // HttpEntity entity = response.getEntity();


            } catch (NullPointerException e) {

            } catch (Exception e) {

                return e.toString();
            }
            return g;

        }

        @Override


        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();


        }
    }







    private class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Add_Events.this);
        HttpURLConnection conn;
        URL url = null;

        public AsyncFetch(Add_Events add_events) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides or your JSON file address
                url = new URL("http://192.168.7.122/image/fetch-all-fish.JSON");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we receive data
                conn.setDoOutput(true);
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    return("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            ArrayList<String> dataList = new ArrayList<String>();
            pdLoading.dismiss();
           // Toast.makeText(Add_Events.this, result, Toast.LENGTH_LONG).show();

            if(result.equals("no rows")) {

                // Do some action if no data from database

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    // Extract data from json and store into ArrayList
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        dataList.add(json_data.getString("fish_name"));
                    }

                    strArrData = dataList.toArray(new String[dataList.size()]);
                    //Toast.makeText(MainActivity.this, strArrData.toString(), Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    //Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(Add_Events.this, result, Toast.LENGTH_LONG).show();
                }

            }

        }

    }





}
