package com.example.hp.qask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BackgroundTask {
    ProgressDialog progress ;
    Context context;
    ArrayList<Contact> arrayList = new ArrayList<>();
    String json_url="http://172.16.16.22:7777/ask/";
    public BackgroundTask(Context context){
        this.context=context;
    }
    public ArrayList<Contact> getArrayList(String email,Context c) {
        progress = new ProgressDialog(c);
        progress.setTitle("Wait!");
        progress.setMessage("Loading...");
        progress.show();
        String req_url = json_url.concat(email);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, req_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress.dismiss();
//                      Button[] b = new Button[response.length()];
                        int count = 0;
                        try {
                            while (count < response.length()) {
                                Log.wtf("hm","working..");
                                JSONObject jsonObject = response.getJSONObject(count);
                                Contact contact = new Contact(jsonObject.getString("Question"),jsonObject.getString("Answer"));
                                arrayList.add(contact);
                                count++;
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "ERROR of no response from the server", Toast.LENGTH_SHORT).show();
//                Log.wtf("Work","Done");
                error.printStackTrace();

            }
        }
        );MySingleton.getInstance(context).addTorequestque(jsonArrayRequest);
        return arrayList;
    }

}
