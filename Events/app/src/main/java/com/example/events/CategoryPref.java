package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class CategoryPref extends AppCompatActivity {

    private CheckBox workshop, seminar, fest,party;
    Button done;
    String wrkshp,smnr,fst,prty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_pref);

        //addListenerOnChkIos();
        addListenerOnButton();
    }
    public void addListenerOnButton() {

        workshop = findViewById(R.id.workshop_id);
        seminar = findViewById(R.id.seminar_id);
        fest = findViewById(R.id.fest_id);
        party = findViewById(R.id.parties_id);
        done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                //StringBuffer result = new StringBuffer();
               // result.append("IPhone check : ").append(chkIos.isChecked());
                //result.append("\nAndroid check : ").append(chkAndroid.isChecked());
                //result.append("\nWindows Mobile check :").append(chkWindows.isChecked());



                if(workshop.isChecked())
                {
                    wrkshp=workshop.getText().toString();
                    Toast.makeText(CategoryPref.this,wrkshp,Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(wrkshp).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"workshop Success",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(seminar.isChecked())
                {
                    smnr=seminar.getText().toString();
                    Toast.makeText(CategoryPref.this,smnr,Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(smnr).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"seminar Success",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(fest.isChecked())
                {
                    fst=fest.getText().toString();
                    Toast.makeText(CategoryPref.this,fst,Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(fst).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"fest Success",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(party.isChecked())
                {
                    prty=party.getText().toString();
                    Toast.makeText(CategoryPref.this,prty,Toast.LENGTH_LONG).show();
                    FirebaseMessaging.getInstance().subscribeToTopic(prty).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Party Success",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                /*if(seminar.isChecked())
                {
                    smnr=seminar.getText().toString();
                    Toast.makeText(CategoryPref.this,smnr,Toast.LENGTH_LONG).show();
                }

                FirebaseMessaging.getInstance().subscribeToTopic(smnr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext()," Success",Toast.LENGTH_LONG).show();
                    }
                });*/

            }
        });

    }
}
