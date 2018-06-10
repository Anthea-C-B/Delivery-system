package com.example.anthe.question2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class notifyCustomer extends AppCompatActivity {
    Button notify;
    EditText smsMessage,custCellNum;
    TextView customersNme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_customer);
        notify = (Button)findViewById(R.id.btnNotify);
        smsMessage = (EditText)findViewById(R.id.edtTxtMessage);
        customersNme = (TextView)findViewById(R.id.txtvwCustNameNotify);
        custCellNum = (EditText)findViewById(R.id.edtTxtNotifyCell);
        final String cell = custCellNum.getText().toString();
        Intent getCustName = getIntent();
        customersNme.setText(getCustName.getStringExtra("customers Name"));
        smsMessage.setText("Good day " + getCustName.getStringExtra("customers Name") + " \nplease be aware that " +
                "\nyour goods have gone out and will be delivered to you during \nthe course of today. Please ensure that you have \nsomeone " +
                "at your household to receive \nthese goods. Thank you!");
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                if(cell.isEmpty() || cell.length() > 10 || cell.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Invalid cell number", Toast.LENGTH_LONG).show();
                }else{
                    sms.sendTextMessage(custCellNum.getText().toString(),null,smsMessage.getText().toString(),null,null);//send the text message
                    Toast.makeText(getApplicationContext(),"SMS sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
