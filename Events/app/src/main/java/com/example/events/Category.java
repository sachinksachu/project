package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Category extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("user/chat");

    EditText editText,editText2;
    TextView txt;
    Button submit,edit,addmember;
    String str,membername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        submit= findViewById(R.id.btnSubmit);
        edit= findViewById(R.id.btnEdit);
        addmember= findViewById(R.id.addmembutton);
        editText= findViewById(R.id.inputid);
        editText2 = findViewById(R.id.member);
        //str=editText.getText().toString();
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        txt = findViewById(R.id.txtid);
        //basicReadWrite();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                membername=editText2.getText().toString();
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                myRef.child(membername).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        txt.setText(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=editText.getText().toString();
                membername=editText2.getText().toString();
               Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                myRef.child(membername).setValue(str);
            }
        });

        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                membername=editText2.getText().toString();
                Toast.makeText(getApplicationContext(),membername,Toast.LENGTH_SHORT).show();
                myRef.child(membername).setValue(str);
            }
        });


    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database

        myRef.setValue(str);
        // [END write_message]

        // [START read_message]
        // Read from the database


        }
        // [END read_message]


    }
