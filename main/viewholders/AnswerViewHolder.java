package com.example.hp.qask.viewholders;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.example.hp.qask.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerViewHolder extends ChildViewHolder {
    private TextView tv_answer,tv_timestamp,tv_name;
    @SuppressLint("ResourceType")
    public AnswerViewHolder(View itemView) {
        super(itemView);
        tv_answer = (TextView)itemView.findViewById(R.id.TVChild);
        tv_timestamp = (TextView)itemView.findViewById(R.id.TVtimestampchild);
        tv_name = (TextView)itemView.findViewById(R.id.TVNamechild);
    }
    public void setAnswerName(String name){

        try {
            JSONObject jsonObject = new JSONObject(name);
            String answer1 = jsonObject.getString("answer");
            String naming = jsonObject.getString("username");
            String time_stamp = jsonObject.getString("timestamp");
            tv_answer.setText(answer1);
            tv_name.setText(naming);
            tv_timestamp.setText(time_stamp.substring(4,21));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
