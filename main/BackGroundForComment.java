package com.example.hp.qask;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BackGroundForComment {
    Context context;
    ArrayList<ContactAnswer> arrayList = new ArrayList<>();
//    Button comment_answer;
//    String json_url="http://172.17.60.215:7777/ask/";
    public BackGroundForComment(Context context){
        this.context=context;
    }
    public ArrayList<ContactAnswer> getArrayList(String response,int pos) {
        try {
            JSONArray jj = new JSONArray(response);
            JSONObject asked_question = jj.getJSONObject(pos);
            JSONArray ans = asked_question.getJSONArray("Answer");

            int count=0;
            while(count<ans.length()){
            ContactAnswer contactAnswer = new ContactAnswer(ans.getString(count));
              arrayList.add(contactAnswer);
              count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        String req_url = json_url.concat(email);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, req_url, (String) null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
////                        Button[] b = new Button[response.length()];
//                        int count = 0;
//                        try {
//                            while (count < response.length()) {
//
//                                JSONObject jsonObject = response.getJSONObject(count);
//                                ContactAnswer contactAnswer = new ContactAnswer(jsonObject.getString("Answer"));
//                                arrayList.add(contactAnswer);
//                                count++;
//
//
//                            }
//                        }
//                        catch(JSONException e){
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "May Be no answer posted for this particular question", Toast.LENGTH_SHORT).show();
////                Log.wtf("Work","Done");
//                error.printStackTrace();
//
//            }
//        }
//        );MySingleton.getInstance(context).addTorequestque(jsonArrayRequest);
        return arrayList;

    }
}
