package com.example.anthe.question2;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.R.layout.simple_dropdown_item_1line;


public class completedDel extends Fragment {
    View compView;
    ListView comDeliveries;
    ArrayList<String> dataList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseReference dbRef;
    ProgressDialog loadCompleted;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        compView = inflater.inflate(R.layout.activity_completed_deliveries, container, false);
        dbRef = FirebaseDatabase.getInstance().getReference("completed");
        adapter = new ArrayAdapter<String>(compView.getContext(), simple_dropdown_item_1line,dataList);
        comDeliveries = (ListView)compView.findViewById(R.id.lstVwCompDel);
        comDeliveries.setAdapter(adapter);
        loadCompleted = new ProgressDialog(comDeliveries.getContext());
        loadCompleted.setMessage("Loading all your completed deliveries");
        loadCompleted.show();

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataList.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
                loadCompleted.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataList.remove(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();
                loadCompleted.dismiss();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return compView;
    }
}
