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

public class BackGroundForAOI {
    Context context;
    ArrayList<ContactAOI> arrayList = new ArrayList<>();
    public BackGroundForAOI(Context context){
        this.context=context;
    }



}
