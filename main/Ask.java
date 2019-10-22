package com.example.hp.qask;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ask extends AppCompatActivity {
//    EditText email_id_from_login_page;
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
    String server_url = "http://172.16.16.22:7777/ask/question";
    EditText whats_your_question;
    EditText whats_its_field;
    Button bask;
    String personal_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        personal_email = getIntent().getStringExtra("Username");
        whats_your_question = (EditText)findViewById(R.id.ETwhatsyourquestion);
        whats_its_field = (EditText)findViewById(R.id.ETwhatsitsfield);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ask.this);
        alertDialogBuilder.setTitle("Before Asking question make sure...");
        alertDialogBuilder.setMessage("1. There is no grammatical error.\n\n2. Specify the field from the list.");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("OK",null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


        rv_area_of_interest_latest = (RecyclerView)findViewById(R.id.RVwhatsitsfield);
        layoutManager = new LinearLayoutManager(this);
        rv_area_of_interest_latest.setLayoutManager(layoutManager);
        rv_area_of_interest_latest.setHasFixedSize(true);
        adapter = new RecyclerAdapterAOI(arrayList);
        rv_area_of_interest_latest.setAdapter(adapter);

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
                Toast.makeText(Ask.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });MySingleton.getInstance(Ask.this).addTorequestque(jsonArrayRequest);



        rv_area_of_interest_latest.addOnItemTouchListener(
                new RecyclerItemClickListener(Ask.this, rv_area_of_interest_latest ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {
                        RecyclerView.ViewHolder holder = null;
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
                            Toast.makeText(Ask.this,"Select your Prime areas!!",Toast.LENGTH_SHORT).show();
                    }
                })
        );




        bask = (Button)findViewById(R.id.BAsk);
//        email_id_from_login_page = (EditText)findViewById(R.id.ETUSERNAME);
        bask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String question_string,answer_string,email_id_form_login;
                question_string = whats_your_question.getText().toString();
                answer_string = whats_its_field.getText().toString();
//                email_id_form_login = email_id_from_login_page.getText().toString();
                if(question_string==""||answer_string==""){
                    Toast.makeText(Ask.this,"Some field is left blank",Toast.LENGTH_LONG).show();
                }
                else{
                    StringRequest request = new StringRequest(Request.Method.POST, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Ask.this);
                                    alertDialogBuilder.setMessage("Question Posted");
                                    alertDialogBuilder.setCancelable(true);
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();
                                    whats_your_question.setText("");
                                    whats_its_field.setText("");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Ask.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("question",question_string);
                            params.put("area", String.valueOf(arrayListpost));
                            params.put("email_id",personal_email);
                            return params;
                        }
                    };MySingleton.getInstance(Ask.this).addTorequestque(request);

                }
            }
        });
    }
}
