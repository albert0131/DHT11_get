package com.example.user.dht11_get;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.dht11_get.R;
import com.example.user.dht11_get.Thinkspeak;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tv, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);

// ----------- 抓取ThinkSpeak --------------------
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // feed需利用Object解析
        StringRequest request = new StringRequest("https://api.thingspeak.com/channels/176126/feed/last.json?results=2&api_key=TC1HMH4R7SSVE93A",

        // results=? 設定抓取筆數 (field需用array解析)
        //StringRequest request = new StringRequest("https://api.thingspeak.com/channels/176126/field/1.json?results=1&api_key=TC1HMH4R7SSVE93A",

               new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // -------- GSON 寫法 ---------
//                        Gson gson = new Gson();
//                        Thinkspeak loc[] = gson.fromJson(response, Thinkspeak[].class);
//                        for (Thinkspeak a : loc)
//                        {
//                            Log.d("NET", a.field1);
//                        }
//                         -----------------------

                        // ------- JSON 寫法 -------
                        try {
                                JSONObject objLast = new JSONObject(response);
                                String humiLast = objLast.getString("field1");
                                String tempLast = objLast.getString("field2");

                                Log.d("NET", humiLast);
                                Log.d("NET", tempLast);
                                tv.setText("目前濕度: " + humiLast);
                                tv2.setText("目前溫度: " + tempLast);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // --------------------------
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// --------------------------------------------------
        queue.add(request);
        queue.start();

    }
}