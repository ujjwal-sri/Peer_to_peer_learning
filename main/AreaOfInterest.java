package com.example.hp.qask;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaOfInterest extends AppCompatActivity {
    final ArrayList<String> al = new ArrayList<>();
    Button bdone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_of_interest);
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager LayoutManager;


//        View.OnClickListener mOnClickListener;
//        Button baoicancel;
//        recyclerView = findViewById(R.id.RVQuestionAnswer);

//        LayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(LayoutManager);
//        recyclerView.setHasFixedSize(true);
//        al.add(null);
//        baoicancel = (Button) findViewById(R.id.BAOICancel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        adapter = new RecyclerAdapterAOI(AreaOfInterest.this,al);
        JSONObject jsonObject;


        final EditText et;
        final String[] aoi = new String[1];
        et = (EditText) findViewById(R.id.ETAOI);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                JSONObject jsonObject;
//                ContactAOI contactaoi = new ContactAOI(et.getText().toString());
                String s = et.getText().toString();
                al.add(s);
                et.setText("");

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        bdone = (Button)findViewById(R.id.BDone);
        bdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String personal_email;
                personal_email = getIntent().getStringExtra("Emailid");
                String server_url = "http://172.16.16.22:7777/api/area-interest";
                StringRequest request = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AreaOfInterest.this,"Information posted",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AreaOfInterest.this,Homepage.class);
                        i.putExtra("Username",personal_email);
                        startActivity(i);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AreaOfInterest.this,"Network Error",Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email_id",personal_email);
                        String a = String.valueOf(al);
                        params.put("area_of_interest", a.substring(1,a.length()-1));
                        return params;
                    }

                };
                    MySingleton.getInstance(AreaOfInterest.this).addTorequestque(request);
            }
        });
//        adapter.notifyDataSetChanged();
//        recyclerView.setAdapter(adapter);

//        baoicancel.setOnClickListener(
//                new RecyclerItemClickListener(AreaOfInterest.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, final int position) {
//                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url.concat(personal_email), (String) null,
//                                new Response.Listener<JSONArray>() {
//                                    @Override
//                                    public void onResponse(JSONArray response) {
//                                        try {
//                                            JSONObject jsonObject = response.getJSONObject(position);
//                                            String s = jsonObject.getString("Question");
//                                            Intent i = new Intent(Homepage.this, Comment.class);
//                                            i.putExtra("json_string", String.valueOf(response));
//                                            i.putExtra("Question", s);
//                                            i.putExtra("Position_for_RV",position);
//                                            i.putExtra("Username",personal_email);
//                                            startActivity(i);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(Homepage.this, "Connection Error", Toast.LENGTH_SHORT).show();
////                Log.wtf("Work","Done");
//                                error.printStackTrace();
//
//                            }
//                        }
//                        );
//                        MySingleton.getInstance(Homepage.this).addTorequestque(jsonArrayRequest);
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        Toast.makeText(AreaOfInterest.this,"Ouch! leave me",Toast.LENGTH_LONG).show();
//                    }
//                })
//        );

    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        final String personal_email;
        personal_email = getIntent().getStringExtra("Emailid");
        int id = item.getItemId();
        String server_url = "http://172.16.16.22:7777/api/area-interest";
//        if(id==R.id.skipping){StringRequest request = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(AreaOfInterest.this,"Information posted",Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(AreaOfInterest.this,Homepage.class);
//                i.putExtra("Username",personal_email);
//                startActivity(i);
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AreaOfInterest.this,"Network Error",Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email_id",personal_email);
//                String a = String.valueOf(al);
//                params.put("area_of_interest", a.substring(1,a.length()-1));
//                return params;
//            }
//
//        };
//            MySingleton.getInstance(AreaOfInterest.this).addTorequestque(request);
//
//        }

        return true;
    }
}
