package com.example.hp.qask;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity{
    String server_url = "http://172.16.16.22:7777/api/register";
    EditText Name,Email,Password,ConfirmPassword;
    Button SignUp;
//    TextView sName,sEmail,sPassword,sConfirmpassword;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

//        sName = (TextView)findViewById(R.id.TVName);
//        sEmail = (TextView)findViewById(R.id.TVEmail);
//        sPassword = (TextView)findViewById(R.id.TVPassword);
//        sConfirmpassword = (TextView)findViewById(R.id.TVConfirmPassword);
        Name = (EditText) findViewById(R.id.ETName);
        Email = (EditText)findViewById(R.id.ETEmail);
        Password = (EditText)findViewById(R.id.ETPassword);
        ConfirmPassword = (EditText)findViewById(R.id.ETConfirmPassword);
        SignUp = (Button)findViewById(R.id.BSignup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stremailid;
                final String vsignupname,vsignupemailid,vsignuppassword,vsignupconfirmpassword;
                vsignupname = Name.getText().toString();
                vsignupemailid = Email.getText().toString();
                vsignuppassword = Password.getText().toString();
                vsignupconfirmpassword = ConfirmPassword.getText().toString();
                if(vsignuppassword.equals(vsignupconfirmpassword)){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("status");
                            String message = jsonObject.getString("msg");
                            if(code.equals("ERROR")){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Signup.this);
                                alertDialogBuilder.setTitle("Error!");
                                alertDialogBuilder.setMessage(message);
                                alertDialogBuilder.setCancelable(true);
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }else{
                            Intent i = new Intent(Signup.this, AOILatest.class);
                            i.putExtra("Emailid", vsignupemailid);
                            startActivity(i);}
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Signup.this);
                        alertDialogBuilder.setTitle("Error!");
                        alertDialogBuilder.setMessage("Check your Network Connection!");
                        alertDialogBuilder.setCancelable(true);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        error.printStackTrace();
                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> Params = new HashMap<String, String>();
                        Params.put("name",vsignupname);
                        Params.put("email_id",vsignupemailid);
                        Params.put("password",vsignuppassword);
                        Params.put("confirm_password",vsignupconfirmpassword);
                        return Params;

                    }
                };MySingleton.getInstance(Signup.this).addTorequestque(stringRequest);
                }else{
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Signup.this);
                    alertDialogBuilder.setTitle("Error!");
                    alertDialogBuilder.setMessage("Password don't match");
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            }
        });


    }


}
