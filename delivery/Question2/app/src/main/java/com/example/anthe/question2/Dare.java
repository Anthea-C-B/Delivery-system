package com.example.anthe.question2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Dare extends AppCompatActivity {
    EditText email, password;
    String enteredEmail, enteredPasswrd;
    Button signIn;
    FirebaseAuth db;
    final int MY_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        db = FirebaseAuth.getInstance();
        email = (EditText)findViewById(R.id.edtTxtUsername);
        password = (EditText)findViewById(R.id.edtTxtPasswrd);
        if(db.getCurrentUser() != null){
            startActivity(new Intent(Dare.this,dareUser.class));
            finish();
        }

        signIn = (Button)findViewById(R.id.btnSign);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        enteredEmail = email.getText().toString() ;
        enteredPasswrd = password.getText().toString();
        db.signInWithEmailAndPassword(enteredEmail,enteredPasswrd)
         .addOnCompleteListener(Dare.this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
         if(!task.isSuccessful()){
            Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
         }else{
            Intent data = new Intent(getApplicationContext(),dareUser.class);
            startActivity(data);
            finish();
            }
         }
         });
            }
        });
    }
    public void onRequestPermissionsResult(int reqCode,String[]perm,int[]grntRes){
        switch (reqCode){
            case MY_REQUEST:{
                if(grntRes.length>0 && grntRes[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(),"Permission granted",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}
