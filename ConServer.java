package com.server.lookitsmarc.myserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * Created by lookitsmarc on 18/05/2016.
 */
public class ConServer extends Thread implements Runnable {

    Handler mm = new Handler();
    String userAction,
           eventAction,
           dateAction,
           nameAction,
           phoneNb,
           evContent;

    Context myCont;

    // constructor
    public ConServer(Context context, String action, String event, String date, String name, String phone, String ev){
        userAction = action;
        eventAction = event;
        dateAction = date;
        nameAction = name;
        phoneNb = phone;
        myCont = context;
        evContent = ev;

    }


    public void run()
    {


            JSONObject myObj = new JSONObject();

            try{
                myObj.put("action", userAction);
                myObj.put("event",eventAction);
                myObj.put("date",dateAction);
                myObj.put("name",nameAction);
                myObj.put("phone",phoneNb);

                // convert space into %20

                String eventStrConvert = new String(evContent);
                eventStrConvert = eventStrConvert.replaceAll("^ +| +$|( )+","%20");
                myObj.put("eventContent", eventStrConvert);



            } catch(JSONException e){

            }


            RequestQueue queue = Volley.newRequestQueue(myCont);
            final String url = "http://marcintha.fr/android_ws.php?data="+myObj;
            //final String url = "http://marcintha.fr/android_ws.php?name="+nameAction+"&action="+eventAction+"&event="+eventAction+"&date="+dateAction+"phone="+phoneNb;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                               System.out.println(url);
                               System.out.println(response);
                            }
                        }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That didn't work!");
                        }
                });
                // Add the request to the RequestQueue.
            queue.add(stringRequest);





          /*  System.out.println(myObj);
            URL url = new URL("http://marcintha.fr/android_ws.php?object="+myObj+"");
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.addRequestProperty("Accept", "application/json");
            urlCon.addRequestProperty("Content-Type", "application/json");

            try{

                InputStream in = new BufferedInputStream(urlCon.getInputStream());
                final String uu = readStream(in);

                mm.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("received "+uu);
                        Toast.makeText(myCont, uu, Toast.LENGTH_LONG).show();
                    }
                });

            } finally {
                urlCon.disconnect();
            }*/






           /* URL url = new URL("http://marcintha.fr/android_ws.php");
            System.out.println("send");
            URLConnection urlCon = url.openConnection();

            InputStream in = new BufferedInputStream(urlCon.getInputStream());
            final String uu = readStream(in);

            mm.post(new Runnable() {
                @Override
                public void run() {
                    System.out.println("received "+uu);
                    Toast.makeText(myCont, uu, Toast.LENGTH_LONG).show();
                }
            }); */







    }


    private String readStream(InputStream is){
        try{
            ByteArrayOutputStream bo = new ByteArrayOutputStream();

            int i = is.read();

            while(i != -1){
                bo.write(i);
                i = is.read();

            }


            return bo.toString();
        }
        catch(IOException e){
            return "";
        }
    }
}
