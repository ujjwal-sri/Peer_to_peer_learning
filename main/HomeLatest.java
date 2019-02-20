package com.example.hp.qask;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.example.hp.qask.Model.Answer;


import com.example.hp.qask.Model.Questions;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeLatest extends AppCompatActivity {
    ProgressDialog progress;
    private RecyclerView recyclerView;
    private List<Questions> questions;
    private String personal_email;
    private String jsonurl = "http://172.16.16.22:7777/ask/";
    private Button b_post_answer;
    private String url_for_comment = "http://172.16.16.22:7777/ask/answer";
    private int position_for_comment;
    private String responseall;
    private EditText et_comment_box;
    private ExpandableGroup expandedGroup;
    private EditText et_post_answer;
    private ImageView iv_comment_box_visible;
    private LinearLayout ll_visibility;
    private ArrayList storeList_datas = new ArrayList<>();
    private SearchView searchView;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_latest);

        b_post_answer = (Button) findViewById(R.id.BPostAnswer);
        et_comment_box = (EditText) findViewById(R.id.ETCommentyouranswer);
        personal_email = getIntent().getStringExtra("Username");
        recyclerView = (RecyclerView) findViewById(R.id.RVHomed);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeLatest.this, LinearLayoutManager.VERTICAL, false));
        String req_url = jsonurl.concat(personal_email);
        progress = new ProgressDialog(HomeLatest.this);
        progress.setTitle("Wait!");
        progress.setMessage("Loading...");
        progress.show();
        questions = new ArrayList<>();

        questionAdapter = new QuestionAdapter(questions, HomeLatest.this, personal_email);
//        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecorator(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(questionAdapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, req_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("status");
                            if (code.equals("ERROR")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeLatest.this);
                                alertDialogBuilder.setTitle("Error!");
                                alertDialogBuilder.setMessage(jsonObject.getString("msg"));
                                alertDialogBuilder.setCancelable(true);
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            } else {
                                JSONArray message = jsonObject.getJSONArray("msg");
                                Log.wtf("Message", message.toString());
                                int message_length = message.length();
                                for (int count = 0; count < message_length; count++) {
                                    JSONObject jsonObject1 = message.getJSONObject(count);
                                    JSONArray jsonAnswer = jsonObject1.getJSONArray("Answer");
                                    int json_answer_length = jsonAnswer.length();
                                    List<Answer> answer = new ArrayList<>(json_answer_length);
                                    for (int i = 0; i < json_answer_length; i++) {
                                        answer.add(new Answer(jsonAnswer.getString(i)));
                                    }
                                    questions.add(new Questions(jsonObject1.getString("Question"), answer));
                                }
                                questionAdapter.notifyDataSetChanged();
                                Log.wtf("ADAPTER", "REACHED");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeLatest.this, "ERROR of no response from the server", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }
        );
        MySingleton.getInstance(HomeLatest.this).addTorequestque(stringRequest);
        Log.wtf("Data", "inserted");
        progress.dismiss();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeLatest.this,HomeSearch.class);
                i.putExtra("Username",personal_email);
                startActivity(i);
            }
        });

        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

}
