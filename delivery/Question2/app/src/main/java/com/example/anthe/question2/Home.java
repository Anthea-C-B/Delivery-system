package com.example.anthe.question2;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    View hmeView;
    TextView txtNme,txtID,txtTruck,txtTruckNum;
    ImageView profile;
    ProgressDialog loadProfile;
    DatabaseReference dbRef;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        hmeView = inflater.inflate(R.layout.activity_home,container,false);
        txtNme = (TextView)hmeView.findViewById(R.id.txtvwDrvName);
        txtID = (TextView)hmeView.findViewById(R.id.txtvwDriverID);
        txtTruck = (TextView)hmeView.findViewById(R.id.txtvwDrivVehType);
        txtTruckNum = (TextView)hmeView.findViewById(R.id.txtvwDrvTruckNum);
        profile = (ImageView)hmeView.findViewById(R.id.imgvwProfilePic);
        loadProfile = new ProgressDialog(hmeView.getContext());
        loadProfile.setMessage("Building your profile");
        loadProfile.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadProfile.show();
        final ArrayList<String> myData = new ArrayList<>();

        dbRef = FirebaseDatabase.getInstance().getReference();

       dbRef.child("123456").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   myData.add(snapshot.getValue(String.class));
                }
                txtNme.setText(myData.get(0));
                txtID.setText(myData.get(1));
                txtTruck.setText(myData.get(2));
                txtTruckNum.setText(myData.get(3));
                Picasso.with(hmeView.getContext()).load(myData.get(4)).into(profile);
                loadProfile.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loadProfile.dismiss();
            }
        });
        return hmeView;
    }
}
