package com.example.hp.qask.viewholders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hp.qask.HomeLatest;
import com.example.hp.qask.LoginSignup;
import com.example.hp.qask.MySingleton;
import com.example.hp.qask.R;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class QuestionsViewHolder extends GroupViewHolder {
    private TextView tv_questions;
    private TextView tv_name,tv_time_stamp;
    private Button b_post_answer;
    private EditText et_post_answer;
    private ImageView iv_comment_box_visible,people_dp;
    private LinearLayout ll_visibility;
    private Context context;
    private String email_id;

    @SuppressLint("ResourceType")
    public QuestionsViewHolder(View itemView, final Context c, final String pemail) {
        super(itemView);
        this.context=c;
        tv_questions = (TextView)itemView.findViewById(R.id.TVParent);
        tv_name = (TextView)itemView.findViewById(R.id.TVName);
        tv_time_stamp = (TextView)itemView.findViewById(R.id.TVtimestam);
        b_post_answer = (Button)itemView.findViewById(R.id.BPostAnswer);
        et_post_answer = (EditText)itemView.findViewById(R.id.ETCommentyouranswer);
        iv_comment_box_visible = (ImageView)itemView.findViewById(R.id.IVCommentBoxView);
        ll_visibility = (LinearLayout)itemView.findViewById(R.id.LLvisibility);
        people_dp =(ImageView)itemView.findViewById(R.id.IVpeopledp);
        iv_comment_box_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_visibility.isShown())
                ll_visibility.setVisibility(View.GONE);
                else ll_visibility.setVisibility(View.VISIBLE);
            }
        });
        people_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Wait");
                alertDialogBuilder.setMessage("Dp functionality Coming Soon...");
                alertDialogBuilder.setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        b_post_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String question = tv_questions.getText().toString();
                final String comment = et_post_answer.getText().toString();
                String url_for_comment="http://172.16.16.22:7777/ask/answer";
                //code for posting comment to server
                StringRequest request = new StringRequest(Request.Method.POST, url_for_comment, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        et_post_answer.setText("");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Answer posted");
                        alertDialogBuilder.setMessage("Thanks for contributing!!");
                        alertDialogBuilder.setCancelable(true);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Error!");
                        alertDialogBuilder.setMessage("Some error occurred!");
                        alertDialogBuilder.setCancelable(true);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> Params = new HashMap<String, String>();
                        Params.put("Question",question);
                        Params.put("Answer",comment);
                        Params.put("email_id",pemail);
                        return Params;

                    }
                };MySingleton.getInstance(context).addTorequestque(request);
            }
        });



    }
    public void setQuestionsName(String questions){
        try {
            JSONObject jsonObject = new JSONObject(questions);
            String question1 = jsonObject.getString("question");
            String name = jsonObject.getString("username");
            String time_stamp = jsonObject.getString("timestamp");
            tv_questions.setText(question1);
            tv_name.setText(name);
            tv_time_stamp.setText(time_stamp.substring(4,21));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        iv_comment_box_visible.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ll_visibility.getVisibility()==View.GONE)
//                    ll_visibility.setVisibility(View.VISIBLE);
//                if(ll_visibility.getVisibility()==View.VISIBLE)
//                    ll_visibility.setVisibility(View.GONE);
//            }
//        });
//        b_post_answer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    JSONObject jsonObject = new JSONObject(questions);
//                    final String question = jsonObject.getString("question");
//                    final String answer;
//                    answer = et_post_answer.getText().toString();
//                    if (answer.equals("")) {
//
//                    }
//                    else{
//                        StringRequest request = new StringRequest(Request.Method.POST, "http://172.17.35.13:7777/ask/answer",
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//
//                                }
//                            }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//                    }) {
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("question", question);
//                            params.put("answer", answer);
//                            return params;
//                        }
//                    };
//                    MySingleton.getInstance(context).addTorequestque(request);
//                }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }
}
