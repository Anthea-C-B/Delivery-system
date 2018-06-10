package com.example.anthe.question2;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class customersSheet extends AppCompatActivity {
    ImageButton gMap;
    TextView setName;
    Button complete;
    EditText idNum;
    RadioButton good,damaged;
    TextView nme;
    DatabaseReference dbRef;
    FloatingActionButton btnSms;
    ArrayList<String> dbNme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_sheet);
        gMap = (ImageButton)findViewById(R.id.imgBtnGoogleMaps);
        setName = (TextView)findViewById(R.id.txtvwFullCustNme);
        complete = (Button)findViewById(R.id.btnDelCompleted);
        idNum = (EditText) findViewById(R.id.edTextIDNum);
        good = (RadioButton)findViewById(R.id.rdoBtnGood);
        damaged = (RadioButton)findViewById(R.id.rdoBtnDamaged);
        good.setChecked(true);
        final Intent myVal = getIntent();
        setName.setText(myVal.getStringExtra("name"));
        btnSms = (FloatingActionButton)findViewById(R.id.fltingActionBtnMessage);
        dbRef = FirebaseDatabase.getInstance().getReference("ques-b6b8c");
        gMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri map = Uri.parse("google.navigation:q="+myVal.getStringExtra("address"));
                Intent google = new Intent(Intent.ACTION_VIEW, map);
                google.setPackage("com.google.android.apps.maps");
                startActivity(google);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idNumber = idNum.getText().toString();
                String delStat = "";
                if(idNumber.length()>13 || idNumber.length() < 13 || idNumber.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ID number is invalid",Toast.LENGTH_SHORT).show();
                }else{
                   if(good.isChecked()){
                       delStat = "Good";
                   }else{
                       delStat = "Damaged";
                       DatabaseReference db = dbRef.child("Name");
                       db.setValue(setName.getText().toString());
                       db = dbRef.child("ID");
                       db.setValue(idNumber);
                       db = dbRef.child("Good Status");
                       db.setValue(delStat);
                       Toast.makeText(getApplicationContext(),"Delivery completed, Good Job!",Toast.LENGTH_SHORT).show();

                   }
                }
            }
        });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendSms = new Intent(getApplicationContext(),notifyCustomer.class);
                sendSms.putExtra("customers Name",setName.getText().toString());
                startActivity(sendSms);
            }
        });
    }
}
