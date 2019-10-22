package com.example.hp.qask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AOILatest extends AppCompatActivity {

    private RecyclerView rv_area_of_interest_latest;
    private RecyclerAdapterAOI adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ContactAOI> arrayList = new ArrayList<>();
    private String aoi_url ="http://172.16.16.22:7777/api/area";
    private AlertDialog.Builder builder;
    private String url_for_posting_aoi= "http://172.16.16.22:7777/api/area";
    private  ArrayList<String> arrayListpost = new ArrayList<>();
    private JSONArray response;
    private String personalmail;
    private String post_url = "http://172.16.16.22:7777/api/area-interest";
    private TextView tvaoi_;
    private int row_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aoilatest);
       AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(AOILatest.this);
//        alertDialogBuilder.setTitle("NOTE:");
//
//        alertDialogBuilder.setMessage("An email has been sent to your mobile number!");
//        alertDialogBuilder.setCancelable(true);
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        personalmail = getIntent().getStringExtra("Emailid");
        rv_area_of_interest_latest = (RecyclerView)findViewById(R.id.RVAOILatest);
        layoutManager = new LinearLayoutManager(this);
        tvaoi_ = (TextView)findViewById(R.id.TVAOI_);
        rv_area_of_interest_latest.setLayoutManager(layoutManager);
        rv_area_of_interest_latest.setHasFixedSize(true);
        adapter = new RecyclerAdapterAOI(arrayList);
        rv_area_of_interest_latest.setAdapter(adapter);

//        progress = ProgressDialog.show(this, "Wait!",
//                "Loading...", true);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, aoi_url, (String) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int count=0;
                        while(count<response.length()){
                            try {
                                ContactAOI contactAOI = new ContactAOI(response.getString(count));
                                arrayList.add(contactAOI);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            count++;
                        }
                        adapter.notifyDataSetChanged();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                builder = new AlertDialog.Builder(AOILatest.this);
                builder.setTitle("Error!").setMessage("Enable network connection").show();
            }
        });MySingleton.getInstance(AOILatest.this).addTorequestque(jsonArrayRequest);


//        progress.dismiss();
        rv_area_of_interest_latest.addOnItemTouchListener(
                new RecyclerItemClickListener(AOILatest.this, rv_area_of_interest_latest ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        RecyclerView.ViewHolder holder = null;
//                        try {
//                            tvaoi_.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                        }catch(NullPointerException e){
//                            e.printStackTrace();
//                        }
                        CardView cardView;
//                        cardView = (CardView) findViewById(R.id.CVaoi);
                        row_index = position;
                        /*cardView.setBackgroundColor(Color.parseColor("#5c396e"));
                        */
                        ColorDrawable colorDrawable = (ColorDrawable)rv_area_of_interest_latest.findViewHolderForAdapterPosition(position).itemView.getBackground();
                        int colorid = colorDrawable.getColor();
                        Log.wtf("Message",Integer.toString(colorid));
                        if(colorid==-1){
                            rv_area_of_interest_latest.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor((Color.parseColor("#5c396e")));
                            arrayListpost.add(arrayList.get(position).getAOI());
                        }
                        else{
                            rv_area_of_interest_latest.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor((Color.parseColor("#ffffff")));
                            arrayListpost.remove(arrayList.get(position).getAOI());
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Toast.makeText(AOILatest.this,"Ouch! Leave me",Toast.LENGTH_SHORT).show();

                    }
                })
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, post_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AOILatest.this,"Information posted",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AOILatest.this,HomeLatest.class);
                        i.putExtra("Username",personalmail);
                        startActivity(i);
//                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AOILatest.this,"Network Error",Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                protected Map<String, String> getParams()  {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email_id",personalmail);
                    String a = String.valueOf(arrayListpost);
                    params.put("area_of_interest", a);
                    return params;
                }
                };MySingleton.getInstance(AOILatest.this).addTorequestque(request);

            }
        });
    }

}
