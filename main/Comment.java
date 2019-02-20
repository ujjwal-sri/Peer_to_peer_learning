package com.example.hp.qask;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Comment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final EditText commentbox;
        final TextView tvanswers;
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        RecyclerView.LayoutManager LayoutManager;
        ArrayList<ContactAnswer> arrayListAnswer;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String personal_email;
        personal_email = getIntent().getStringExtra("Username");
//        String pos = getIntent().getStringExtra("Position_from_RV");
        int position = getIntent().getIntExtra("Position_for_RV",-1);
        String json_string_from_homepage = getIntent().getStringExtra("json_string");
        commentbox = (EditText)findViewById(R.id.ETComment);
        tvanswers = (TextView)findViewById(R.id.TVAnswers);

        recyclerView = (RecyclerView)findViewById(R.id.RVAnswer);
        LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setHasFixedSize(true);
        BackGroundForComment backGroundForComment = new BackGroundForComment(Comment.this);
        arrayListAnswer = backGroundForComment.getArrayList(json_string_from_homepage,position);
        adapter = new RecyclerAdapterComment(arrayListAnswer);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String question = getIntent().getStringExtra("Question");
                final String comment = commentbox.getText().toString();
                String url_for_comment="http://172.16.16.22:7777/ask/answer";
                //code for posting comment to server
                StringRequest request = new StringRequest(Request.Method.POST, url_for_comment, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Comment.this,"Your respone has been recorded",Toast.LENGTH_LONG).show();
                        commentbox.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Comment.this,"NetworkError! cannot be posted",Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> Params = new HashMap<String, String>();
                        Params.put("Question",question);
                        Params.put("Answer",comment);
                        return Params;

                    }
                };MySingleton.getInstance(Comment.this).addTorequestque(request);
            }
        });
    }

}
