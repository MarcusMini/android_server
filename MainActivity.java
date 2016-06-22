package com.server.lookitsmarc.myserver;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // save an instance of my activity
    public static Activity activity;
    IntentFilter smsFiler;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
    }


    public void launchConServer(View v){
        Button StartButton = (Button) v;

        SmsSender();
    }

    public void SmsSender() {

        try {

            int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
            // first check the permission

            // get the context
            Context cont = getApplicationContext();

            int permissionCheck = ContextCompat.checkSelfPermission(cont, Manifest.permission.SEND_SMS);

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);

            SmsManager man = SmsManager.getDefault();
            man.sendTextMessage("_your_number", null, "inscrire/restaurant/01-06-2016/Marc/Devenir pilote", null, null);

            Toast.makeText(getApplicationContext(), "Sms send", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Sms not send", Toast.LENGTH_LONG).show();
        }

    }




}
