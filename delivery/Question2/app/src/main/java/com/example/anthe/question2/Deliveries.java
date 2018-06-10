package com.example.anthe.question2;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;


public class Deliveries extends Fragment {
    DatabaseReference fbReference;
    List<Customers> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    ProgressDialog progressDialog;

    View dogs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dogs = inflater.inflate(R.layout.activity_recycler_view, container, false);
        recyclerView = (RecyclerView) dogs.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(dogs.getContext()));
        progressDialog = new ProgressDialog(dogs.getContext());
        fbReference = FirebaseDatabase.getInstance().getReference("Category13");//establishes connection to database path
        progressDialog.setMessage("Loading your matches . . . ");
        progressDialog.show();

        fbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Customers customers = snapshot.getValue(Customers.class);//database data inform of object is sent through
                    list.add(customers);
                }
                myAdapter = new RecyclerViewAdapter(dogs.getContext(), list);
                recyclerView.setAdapter(myAdapter);//sets the recyclerview with the data
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        return dogs;
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        Context context;
        List<Customers> MainImageUploadInfoList;
        public RecyclerViewAdapter(Context context, List<Customers> TempList) {
            this.MainImageUploadInfoList = TempList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_delivery, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Customers customers = MainImageUploadInfoList.get(position);
            holder.cName.setText(customers.getCustNme());
            holder.address.setText(customers.getCustAddress());
            holder.cDel.setText(customers.getDelType());
            holder.cID.setText(customers.getCustID());
            holder.cKm.setText(customers.getKmTravel());
        }

        @Override
        public int getItemCount() {
            return MainImageUploadInfoList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView cName;
            public TextView cID;
            public TextView cDel;
            public TextView cKm;
            public TextView address;

            public ViewHolder(View itemView) {
                super(itemView);
                cName = (TextView)itemView.findViewById(R.id.txtvwCustNme);
                cID = (TextView)itemView.findViewById(R.id.txtvwCustID);
                cDel = (TextView)itemView.findViewById(R.id.txtvwDelType);
                cKm = (TextView)itemView.findViewById(R.id.txtvwEstKm);
                address = (TextView)itemView.findViewById(R.id.txtvwAddress);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent custSheet = new Intent(dogs.getContext(),customersSheet.class);
                        custSheet.putExtra("name",cName.getText().toString());
                        custSheet.putExtra("address",address.getText().toString());
                        startActivity(custSheet);
                    }
                });
            }
        }
    }

    static class Customers {
        public String custNme;
        public String custID;
        public String delType;
        public String kmTravel;
        public String custAddress;
        public Customers(){

        }

        public Customers(String name,String id,String del,String km,String address){
            custNme = name;
            custID = id;
            delType = del;
            kmTravel = km;
            custAddress = address;
        }

        public String getCustNme() {
            return custNme;
        }

        public String getCustAddress() {
            return custAddress;
        }

        public String getCustID() {
            return custID;
        }

        public String getDelType() {
            return delType;
        }

        public String getKmTravel() {
            return kmTravel;
        }

        public void setCustNme(String custNme) {
            this.custNme = custNme;
        }

        public void setCustAddress(String custAddress) {
            this.custAddress = custAddress;
        }

        public void setCustID(String custID) {
            this.custID = custID;
        }

        public void setDelType(String delType) {
            this.delType = delType;
        }

        public void setKmTravel(String kmTravel) {
            this.kmTravel = kmTravel;
        }
    }

}
