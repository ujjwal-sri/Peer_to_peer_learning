package com.example.hp.qask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterComment extends RecyclerView.Adapter<RecyclerAdapterComment.MyViewHolder> {

        ArrayList<ContactAnswer> arrayListAnswers = new ArrayList<>();
    public RecyclerAdapterComment(ArrayList<ContactAnswer> arrayListAnswers){
        this.arrayListAnswers=arrayListAnswers;
        }

    @NonNull
    @Override
    public RecyclerAdapterComment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_answer_item,parent,false);
        MyViewHolder mVH = new MyViewHolder(view);
        return  mVH;
        }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterComment.MyViewHolder holder, int position) {
        holder.Answer.setText(arrayListAnswers.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return arrayListAnswers.size();
        }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Answer;

        public MyViewHolder(View itemView) {
        super(itemView);
//       Question = (TextView)itemView.findViewById(R.id.TVquestionrow);
        Answer = (TextView)itemView.findViewById(R.id.TVAnswers);
        }

    }
}
