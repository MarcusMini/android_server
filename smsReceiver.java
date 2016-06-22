package com.server.lookitsmarc.myserver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lookitsmarc on 18/05/2016.
 */
public class smsReceiver extends BroadcastReceiver {


    ///////// MODEL OF THE SMS


    /*

        SMS structure

        REQUEST : <action> | <event> | <date> | <text>
                  inscrire
                  enregistrer
                  supprimer


     */

    public smsReceiver(){

    }

    @Override
    public void onReceive(Context context , Intent intent){
        if(intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
            Toast.makeText(context.getApplicationContext(), "received", Toast.LENGTH_LONG).show();
            getMessagesFromIntent(intent, context);
        }
    }


    public static void getMessagesFromIntent(Intent intent, Context context){

        Bundle myBundle = intent.getExtras();
        String action = "",
               event = "",
               date = "",
               name = "",
               eventContent = "";


        Object[] messages = (Object[]) myBundle.get("pdus");

        for(int i = 0; i < messages.length; i++){
            SmsMessage currentMessage = SmsMessage.createFromPdu((byte []) messages[i], "3gpp");
            String mss = currentMessage.getDisplayMessageBody();
            String phoneNb = currentMessage.getDisplayOriginatingAddress();

            String[] splitMss = mss.split("/");

            if(splitMss.length == 5){
                // parse the data
                action = splitMss[0];
                event = splitMss[1];
                date = splitMss[2];
                name = splitMss[3];
                eventContent = splitMss[4];

            } else{
                // send sms to the broadcaster that the format isn't the right one
            }


            System.out.println("MESSAGE "+mss);
            ConServer myCon = new ConServer(context, action, event, date, name, phoneNb, eventContent);
            myCon.start();
        }

    }
}
