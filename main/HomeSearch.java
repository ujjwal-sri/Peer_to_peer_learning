package com.example.hp.qask;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hp.qask.Model.Answer;
import com.example.hp.qask.Model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeSearch extends AppCompatActivity {
    private String query;
    private List<Questions> questions;
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private String personal_email;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        query = getIntent().getStringExtra("query");
        recyclerView=(RecyclerView)findViewById(R.id.RVHomedSearch);
        personal_email = getIntent().getStringExtra("Username");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeSearch.this, LinearLayoutManager.VERTICAL, false));
        questions = new ArrayList<>();
        questionAdapter = new QuestionAdapter(questions, HomeSearch.this, personal_email);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecorator(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(questionAdapter);
//        query = getIntent().getStringExtra("query");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "This page is for searching a question", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(HomeSearch.this,Ask.class);
                i.putExtra("Username",personal_email);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                // filter recycler view when query submitted
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://172.16.16.22:7777/ask/search", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("status");
                            if (code.equals("ERROR")) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeSearch.this);
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

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("query", query);
                        return params;
                    }
                };
                MySingleton.getInstance(HomeSearch.this).addTorequestque(stringRequest1);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
//                questionAdapter.getFilter().filter(query);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(HomeSearch.this,HomeLatest.class);
        i.putExtra("Username",personal_email);
        startActivity(i);
    }


}
