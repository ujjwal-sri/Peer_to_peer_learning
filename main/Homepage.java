package com.example.hp.qask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final String json_url="http://172.16.16.22:7777/ask/";
        ExpandableListAdapter listAdapter;
        ExpandableListView expListView;
        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;

        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager LayoutManager;
        ArrayList<Contact> arrayList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        ProgressDialog progress;
        final String personal_email;
        personal_email = getIntent().getStringExtra("Username");
        Intent ii= new Intent(Homepage.this,Comment.class);
        ii.putExtra("Username",personal_email);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Homepage.this,Ask.class);

//                Bundle extras = getIntent().getExtras();
//                if(extras !=null) {
//                    String personal_mail = extras.getString("Username");
//                    i.putExtra("Username",personal_mail);
//                }
                i.putExtra("Username",personal_email);
                startActivity(i);
            }
        });




//        View.OnClickListener mOnClickListener;
        recyclerView = findViewById(R.id.RVQuestionAnswer);
        LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setHasFixedSize(true);
//        progress = ProgressDialog.show(this, "Wait!",
//                "Loading...", true);
        final BackgroundTask backgroundTask = new BackgroundTask(Homepage.this);
        arrayList = backgroundTask.getArrayList(personal_email,Homepage.this);

        adapter = new RecyclerAdapter(arrayList,getApplicationContext());
//        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
//        progress.dismiss();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(Homepage.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url.concat(personal_email), (String) null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        try {
                                            JSONObject jsonObject = response.getJSONObject(position);
                                            String s = jsonObject.getString("Question");
                                            Log.wtf("DATAAAAA","Coming");
                                            Intent i = new Intent(Homepage.this, Comment.class);
                                            i.putExtra("json_string", String.valueOf(response));
                                            i.putExtra("Question", s);
                                            i.putExtra("Position_for_RV",position);
                                            i.putExtra("Username",personal_email);
                                            startActivity(i);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Homepage.this, "Connection Error", Toast.LENGTH_SHORT).show();
//                Log.wtf("Work","Done");
                                error.printStackTrace();

                            }
                        }
                        );
                        MySingleton.getInstance(Homepage.this).addTorequestque(jsonArrayRequest);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(Homepage.this,"Ouch! leave me",Toast.LENGTH_LONG).show();
                    }
                })
        );
    }

}
/*mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int itemPosition = recyclerView.getChildLayoutPosition(v);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, json_url.concat(personal_email), (String) null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(itemPosition);
                                    String s = jsonObject.getString("Question");
                                    Intent i = new Intent(Homepage.this, Comment.class);
                                    i.putExtra("Question", s);
                                    startActivity(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Homepage.this, "Connection Error", Toast.LENGTH_SHORT).show();
//                Log.wtf("Work","Done");
                        error.printStackTrace();

                    }
                }
                );
                MySingleton.getInstance(Homepage.this).addTorequestque(jsonArrayRequest);
            }
        };*/
