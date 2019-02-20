package com.example.hp.qask;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;


public class LoginSignup extends AppCompatActivity {


//    SessionManager session;
    //    TextView mResult;
    String server_url ="http://172.16.16.22:7777/api/login";
    TextView TUSERNAME,TPASSWORD,SIGNUP;
    Button LOGIN;

    AlertDialog.Builder builder;
    ProgressDialog pDialog;
    EditText EUSERNAME,EPASSWORD;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        EUSERNAME = (EditText)findViewById(R.id.ETUSERNAME);
        EPASSWORD = (EditText)findViewById(R.id.ETPASSWORD);
        String personal_mail;
        personal_mail = EUSERNAME.getText().toString();
        LOGIN = (Button)findViewById(R.id.BLOGIN);
        SIGNUP = (TextView) findViewById(R.id.BSIGNUP);
////        if (session.isLoggedIn()) {
//            // User is already logged in. Take him to main activity
//            Intent intent = new Intent(LoginSignup.this,HomePage.class);
//            startActivity(intent);
//            finish();
//        }

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String string_username,string_password;
                string_username = EUSERNAME.getText().toString();
                string_password = EPASSWORD.getText().toString();
                if(string_username.equals("")||string_password.equals("")){
//                    Toast.makeText(LoginSignup.this,"Empty field",Toast.LENGTH_LONG).show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginSignup.this);
                    alertDialogBuilder.setTitle("Error!");

                    alertDialogBuilder.setMessage("Some fields are empty");
                    alertDialogBuilder.setCancelable(true);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else{
                    StringRequest request = new StringRequest(Request.Method.POST, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
//                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = new JSONObject(response);
//                                        Log.wtf("WORK","DONE");
                                        String code = jsonObject.getString("status");
                                        String message =jsonObject.getString("msg");
                                        if(code.equals("ERROR")){
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginSignup.this);
                                            alertDialogBuilder.setTitle("Error!");
                                            alertDialogBuilder.setMessage(message);
                                            alertDialogBuilder.setCancelable(true);
                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            alertDialog.show();
                                        }
                                        else {
                                            Intent i = new Intent(LoginSignup.this,HomeLatest.class);
//                                            Bundle bundle = new Bundle();
//                                            bundle.putString("name",jsonObject.getString("name"));
//                                            bundle.putString("email_id",jsonObject.getString("email_id"));

                                            i.putExtra("Username",string_username);
                                            startActivity(i);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginSignup.this);
                                            alertDialogBuilder.setMessage("Successfully Logged in!");
                                            alertDialogBuilder.setCancelable(true);
                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            alertDialog.show();
                                            finish();
                                        }
                                    }
                                    catch(JSONException e){
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginSignup.this);
                            alertDialogBuilder.setTitle("Error!");
                            alertDialogBuilder.setMessage("Check Network Connection!");
                            alertDialogBuilder.setCancelable(true);
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String,String>();
                            params.put("email_id",string_username);
                            params.put("password",string_password);
                            return params;
                        }
                    };MySingleton.getInstance(LoginSignup.this).addTorequestque(request);

                }


            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                final String email_id_string, password_id_string;
//////                ParametersOfUsername pou = new ParametersOfUsername();
//                email_id_string = email_edittext.getText().toString();
//                password_id_string = password_editext.getText().toString();
//                if (!email_id_string.isEmpty() && !password_id_string.isEmpty()){
//
////                if (!email_id_string.isEmpty() && !password_id_string.isEmpty()) {
////                    // login user
////
////                    checkLogin(email_id_string, password_id_string);
////                } else {
////                    // Prompt user to enter credentials
////                    Toast.makeText(getApplicationContext(),
////                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
////                }
////
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        {
//                            builder.setTitle("Server Response");
//                            builder.setMessage("Response :" + response);
//                            Log.d("myResponse", response);
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    email_edittext.setText("");
//                                    password_editext.setText("");
//                                }
//                            });
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//                            Intent i = new Intent(LoginSignup.this, HomePage.class);
//                            startActivity(i);
//                            finish();
//
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(LoginSignup.this, "some error found .....", Toast.LENGTH_SHORT).show();
//                        error.printStackTrace();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> Params = new HashMap<String, String>();
////                        ParametersOfUsername pou = new ParametersOfUsername();
//                        Params.put("email_id", email_id_string);
//                        Params.put("password", password_id_string);
//                        return Params;
//
//                    }
//                };
//                MySingleton.getInstance(LoginSignup.this).addTorequestque(stringRequest);
////                new GetDataTask().execute("http://172.17.29.122:7777/api/register");
////                new PostDataTask().execute("http://172.17.29.122:7777/api/register");
//
//
//            }
//            else{
//                    Toast.makeText(getApplicationContext(),
//                            "Please enter the credentials!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSignup.this, Signup.class);
                startActivity(i);
            }
        });

    }

}

//    public void checkLogin(final String email, final String password){
//        // Tag used to cancel the request
//        String tag_string_req = "req_login";
//        try {
//            pDialog.setMessage("Logging in ...");
//            showDialog();
//        }catch(NullPointerException e){
//            e.printStackTrace();
//        }
//        StringRequest strReq = new StringRequest(Request.Method.POST,server_url, new Response.Listener<String>() {
//
////            @Override
////            public void onResponse(String response) {
////                Log.d("myResponse", "Login Response: " + response.toString());
////                hideDialog();
////
////                try {
////                    JSONObject jObj = new JSONObject(response);
////                    boolean error = jObj.getBoolean("error");
////
////                    // Check for error node in json
////                    if (!error) {
////                        // user successfully logged in
////                        // Create login session
////                        session.setLogin(true);
////
////                        // Now store the user in SQLite
//////                        String uid = jObj.getString("uid");
//////
//////                        JSONObject user = jObj.getJSONObject("user");
//////                        String name = user.getString("name");
//////                        String email = user.getString("email");
//////                        String created_at = user.getString("created_at");
////
////                        // Inserting row in users table
////
////                        // Launch main activity
////                        Intent intent = new Intent(LoginSignup.this, HomePage.class);
////                        startActivity(intent);
////                        finish();
////                    } else {
////                        // Error in login. Get the error message
////                        String errorMsg = jObj.getString("error_msg");
////                        Toast.makeText(getApplicationContext(),
////                                errorMsg, Toast.LENGTH_LONG).show();
////                    }
////                } catch (JSONException e) {
////                    // JSON error
////                    e.printStackTrace();
////                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
////                }
////
////            }
////        }, new Response.ErrorListener() {
////
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.e("myErrorResponse", "Login Error: " + error.getMessage());
////                Toast.makeText(getApplicationContext(),
////                        error.getMessage(), Toast.LENGTH_LONG).show();
////                hideDialog();
////            }
////        }) {
////
////            @Override
////            protected Map<String, String> getParams() {
////                // Posting parameters to login url
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("email", email);
////                params.put("password", password);
////
////                return params;
////            }
////
////        };
////
////        // Adding request to request queue
////        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
////    }
////    private void showDialog() {
////        if (!pDialog.isShowing())
////            pDialog.show();
////    }
////
////    private void hideDialog() {
////        if (pDialog.isShowing())
////            pDialog.dismiss();
////    }
//
//            class GetDataTask extends AsyncTask<String, Void, String> {
//
//
//                ProgressDialog progressDialog;
//
//                @Override
//                protected void onPreExecute() {
//
//                    super.onPreExecute();
//
//                    progressDialog = new ProgressDialog(LoginSignup.this);
//                    progressDialog.setMessage("Loading data...");
//                    progressDialog.show();
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//
//                    try {
//                        return getData(params[0]);
//                    } catch (IOException ex) {
//                        return "Network error !";
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    super.onPostExecute(result);
//
//                    //set data response to textView
////            mResult.setText(result);
//
//                    //cancel progress dialog
//                    if (progressDialog != null) {
//                        progressDialog.dismiss();
//                    }
//                }
//
//                private String getData(String urlPath) throws IOException {
//                    StringBuilder result = new StringBuilder();
//                    BufferedReader bufferedReader = null;
//
//                    try {
//                        //Initialize and config request, then connect to server
//                        URL url = new URL(urlPath);
//                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                        urlConnection.setReadTimeout(10000 /* milliseconds */);
//                        urlConnection.setConnectTimeout(10000 /* milliseconds */);
//                        urlConnection.setRequestMethod("GET");
//                        urlConnection.setRequestProperty("Content-Type", "application/json");// set header
//                        urlConnection.connect();
//
//                        //Read data response from server
//                        InputStream inputStream = urlConnection.getInputStream();
//                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result.append(line).append("\n");
//                        }
//
//                    } finally {
//                        if (bufferedReader != null) {
//                            bufferedReader.close();
//                        }
//                    }
//
//                    return result.toString();
//                }
//            }
//
//            class PostDataTask extends AsyncTask<String, Void, String> {
//
//
//                ProgressDialog progressDialog;
//
//                //        final String susername,spassword;
////        susername = username.getText().toString();
////        password = password.getText().toString();
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//
//                    progressDialog = new ProgressDialog(LoginSignup.this);
//                    progressDialog.setMessage("Inserting data...");
//                    progressDialog.show();
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//
//                    try {
//                        return postData(params[0]);
//                    } catch (IOException ex) {
//                        return "Network error !";
//                    } catch (JSONException ex) {
//                        return "Data Invalid !";
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    super.onPostExecute(result);
//
////            mResult.setText(result);
//
//                    if (progressDialog != null) {
//                        progressDialog.dismiss();
//                    }
//                }
//
//                private String postData(String urlPath) throws IOException, JSONException {
//
//                    StringBuilder result = new StringBuilder();
//                    BufferedWriter bufferedWriter = null;
//                    BufferedReader bufferedReader = null;
////            String psuname,pspword;
////            psuname = email_id_edittext.getText().toString();
////            pspword = password_id_editext.getText().toString();
//                    try {
//                        //Create data to send to server
//                        JSONObject dataToSend = new JSONObject();
////                ParametersOfUsername pou = new ParametersOfUsername();
//                        dataToSend.put("email_id", "");
//                        dataToSend.put("password", "");
//
//                        //Initialize and config request, then connect to server.
//                        URL url = new URL(urlPath);
//                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                        urlConnection.setReadTimeout(10000 /* milliseconds */);
//                        urlConnection.setConnectTimeout(10000 /* milliseconds */);
//                        urlConnection.setRequestMethod("POST");
//                        urlConnection.setDoOutput(true);  //enable output (body data)
//                        urlConnection.setRequestProperty("Content-Type", "application/json");// set header
//                        urlConnection.connect();
//
//                        //Write data into server
//                        OutputStream outputStream = urlConnection.getOutputStream();
//                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
//                        bufferedWriter.write(dataToSend.toString());
//                        bufferedWriter.flush();
//
//                        //Read data response from server
//                        InputStream inputStream = urlConnection.getInputStream();
//                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            result.append(line).append("\n");
//                        }
//                    } finally {
//                        if (bufferedReader != null) {
//                            bufferedReader.close();
//                        }
//                        if (bufferedWriter != null) {
//                            bufferedWriter.close();
//                        }
//                    }
//
//                    return result.toString();
//                }
//            }
////    public class ParametersOfUsername{
////        String susername;
////        String spassword;
////    }
//        }
//    }
