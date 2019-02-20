package com.example.hp.qask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.example.hp.qask.Model.Questions;
import com.example.hp.qask.Model.Answer;
import com.example.hp.qask.viewholders.AnswerViewHolder;
import com.example.hp.qask.viewholders.QuestionsViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends ExpandableRecyclerViewAdapter<QuestionsViewHolder,AnswerViewHolder> implements Filterable{
    private GroupExpandCollapseListener expandCollapseListener;
    private Context context;
    private List<Questions> contactList;
    private List<Questions> contactListFiltered;
    private ContactsAdapterListener listener;
    private String pemail;
    public QuestionAdapter(List<? extends ExpandableGroup> groups,Context context,String email) {

        super(groups);
        this.context=context;
        this.listener = listener;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
        this.pemail=email;
    }

    @Override
    public QuestionsViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.layout_parent,parent,false);
        return new QuestionsViewHolder(view,context,pemail);
    }

    @Override
    public AnswerViewHolder onCreateChildViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext())
                .inflate(R.layout.layout_child,child,false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(AnswerViewHolder holder, int flatPosition, com.thoughtbot.expandablerecyclerview.models.ExpandableGroup group, int childIndex) {
        Answer answer = (Answer) group.getItems().get(childIndex);
        holder.setAnswerName(answer.getName());
    }

    @Override
    public void onBindGroupViewHolder(QuestionsViewHolder holder, int flatPosition, com.thoughtbot.expandablerecyclerview.models.ExpandableGroup group) {
        holder.setQuestionsName(group.getTitle());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Questions> filteredList = new ArrayList<>();
                    for (Questions row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Questions>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }
//    @Override
//    public int getItemCount() {
//        return 5;
//    }

}
