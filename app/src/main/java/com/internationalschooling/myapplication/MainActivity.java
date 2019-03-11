package com.internationalschooling.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView tv;


    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner)findViewById(R.id.spinner);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "http://35.229.120.166/test.json";

       // JSONObject jsonBody1 = new JSONObject();
      //  jsonBody.put("authentication", "firstvalue");
          //  jsonBody.put("requestData", "secondobject");
String jsonBody ="{\n" +
        "\t\"authentication\":{\n" +
        "\t\"hash\":\"sdfsdfsfdsdff\"\n" +
        "\t},\n" +
        "\t\"requestData\":{\n" +
        "\t\t\"requestKey\":\"COUNTRIES-LIST\",\n" +
        "\t\t\"requestValue\":\"0\"\n" +
        "\t}\n" +
        "}";


        final String mRequestBody = jsonBody;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject obj = null;

                try {
                    ArrayList<String> cs = new ArrayList<String>();
                    obj = new JSONObject(response);

                    String mastersData = obj.getString("mastersData");
                    JSONObject obj1 = new JSONObject(mastersData);
                    String country = obj1.getString("countries");
                  JSONArray result;
                    result = obj1.getJSONArray("countries");
                    for(int i=0;i<result.length();i++){
                        try {
                            //Getting json object
                            JSONObject json = result.getJSONObject(i);

                            //Adding the name of the student to array list
                            cs.add(json.getString("value"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
 spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, cs));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }


        };

        requestQueue.add(stringRequest);
    }
}
