package com.example.hp.qask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Contact> arrayList = new ArrayList<>();
    private Context context;


//    ArrayList<ContactAnswer> arrayListAnswer = new ArrayList<>();
    public RecyclerAdapter(ArrayList<Contact> arrayList,Context context){
        this.arrayList=arrayList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {/*Assigning value to textView*/
        Contact al = arrayList.get(position);
        holder.Question.setText(arrayList.get(position).getQuestion());
        holder.Answer.setText(arrayList.get(position).getAnswer());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView Question,Answer;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.Question = (TextView)itemView.findViewById(R.id.TVquestionrow);
            this.Answer = (TextView)itemView.findViewById(R.id.TVanswerrow);
            itemView.setClickable(true);
        }

    }
}
/*public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

private ImageLoader mImageLoader;
private Context context;

ArrayList<Coupon> couponArrayList = new ArrayList<>();

public MyAdapter(Context context,ArrayList<Coupon> couponArrayList)
{
    this.couponArrayList=couponArrayList;
    this.context=context;
}
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_row,parent,false);
    ViewHolder viewHolder = new ViewHolder(view);

    viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView redUrl = (TextView)view.findViewById(R.id.offer_url);
            String postUrl = redUrl.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
            Intent browserChooserIntent = Intent.createChooser(intent , "Choose browser of your choice");
            context.startActivity(browserChooserIntent);
        }
    });

    return viewHolder;
}

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
    Coupon coupon = couponArrayList.get(position);
    holder.title.setText(coupon.getTitle());
    holder.name.setText(coupon.getName());
    holder.coupon.setText(coupon.getCoupon());
    holder.expiry.setText(coupon.getExpiry());
    holder.url.setText(coupon.getLink());

    //Image loading using singleton class
    mImageLoader = MySingleton.getInstance(context).getImageLoader();
    holder.image.setImageUrl(coupon.getImage(),mImageLoader);
    holder.image.setDefaultImageResId(R.drawable.placeholder_image);
}

@Override
public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
}
public void remove(ContactsContract.Contacts.Data data) {
    int position = couponArrayList.indexOf(data);
    couponArrayList.remove(position);
    notifyItemRemoved(position);
}

@Override
public int getItemCount() {
    return couponArrayList.size();
}

public static class ViewHolder extends RecyclerView.ViewHolder{
    NetworkImageView image;
    RelativeLayout relativeLayout;
    TextView title,name,coupon,expiry,url;

    public ViewHolder(View itemView) {
        super(itemView);
        this.image = (NetworkImageView)itemView.findViewById(R.id.offer_image);
        this.title = (TextView)itemView.findViewById(R.id.offer_title);
        this.name = (TextView)itemView.findViewById(R.id.offer_name);
        this.coupon = (TextView)itemView.findViewById(R.id.coupon_code);
        this.expiry = (TextView)itemView.findViewById(R.id.expiry_date);
        this.url = (TextView)itemView.findViewById(R.id.offer_url);
        this.relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relLayout);

        //make sure it is clickable
        itemView.setClickable(true);
    }
}*/
