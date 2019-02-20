package com.example.hp.qask.Model;


import com.android.volley.toolbox.StringRequest;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Questions extends ExpandableGroup {
    private String title;
    public Questions(String title,List items){
        super(title,items);
    }



}
