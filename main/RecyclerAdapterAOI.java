package com.example.hp.qask;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapterAOI extends SelectableAdapter<RecyclerAdapterAOI.MyViewHolder> {
    @SuppressWarnings("usual")

    ArrayList<ContactAOI> arrayListAOI = new ArrayList<>();
    public RecyclerAdapterAOI(ArrayList<ContactAOI> arrayListAOI){
        this.arrayListAOI=arrayListAOI;
    }
    public RecyclerAdapterAOI(AreaOfInterest areaOfInterest, ArrayList<ContactAOI> al) {
        this.arrayListAOI=al;
    }
    @NonNull
    @Override
    public RecyclerAdapterAOI.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_aoi,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterAOI.MyViewHolder holder, int position) {
        holder.AOI.setText(arrayListAOI.get(position).getAOI());

//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                row_index=position;
//                notifyDataSetChanged();
//            }
//        });
//        if(row_index==position){
//            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
//            holder.tv1.setTextColor(Color.parseColor("#ffffff"));
//        }
//        else
//        {
//            holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
//            holder.tv1.setTextColor(Color.parseColor("#000000"));
//        }

    }

    @Override
    public int getItemCount() {

        return arrayListAOI.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView AOI;

        public MyViewHolder(View itemView) {
            super(itemView);
            AOI = (TextView)itemView.findViewById(R.id.TVAOI_);
        }

    }
}



/*public class Adapter extends SelectableAdapter<Adapter.ViewHolder> {
@SuppressWarnings("unused")
private static final String TAG = Adapter.class.getSimpleName();
private static final int ITEM_COUNT = 50;
private List<Item> items;

private ViewHolder.ClickListener clickListener;

public Adapter(ViewHolder.ClickListener clickListener) {
    super();

    this.clickListener = clickListener;

    items = new ArrayList<>();
    for (int i = 0; i < ITEM_COUNT; ++i) {
        items.add(new Item("Item " + i));
    }
}

public void removeItem(int position) {
    items.remove(position);
    notifyItemRemoved(position);
}

public void removeItems(List<Integer> positions) {
    // Reverse-sort the list
    Collections.sort(positions, new Comparator<Integer>() {
        @Override
        public int compare(Integer lhs, Integer rhs) {
            return rhs - lhs;
        }
    });

    // Split the list in ranges
    while (!positions.isEmpty()) {
        if (positions.size() == 1) {
            removeItem(positions.get(0));
            positions.remove(0);
        } else {
            int count = 1;
            while (positions.size() > count && positions.get(count).equals(positions.get(count - 1) - 1)) {
                ++count;
            }

            if (count == 1) {
                removeItem(positions.get(0));
            } else {
                removeRange(positions.get(count - 1), count);
            }

            for (int i = 0; i < count; ++i) {
                positions.remove(0);
            }
        }
    }
}

private void removeRange(int positionStart, int itemCount) {
    for (int i = 0; i < itemCount; ++i) {
        items.remove(positionStart);
    }
    notifyItemRangeRemoved(positionStart, itemCount);
}

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
    return new ViewHolder(v, clickListener);
}

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
    final Item item = items.get(position);

    holder.title.setText(item.getTitle());

    // Highlight the item if it's selected
    holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
}

@Override
public int getItemCount() {
    return items.size();
}


public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {
    @SuppressWarnings("unused")
    private static final String TAG = ViewHolder.class.getSimpleName();

    TextView title;
    TextView subtitle;
    View selectedOverlay;

    private ClickListener listener;

    public ViewHolder(View itemView, ClickListener listener) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.textView);
        selectedOverlay = (View)itemView.findViewById(R.id.selectedOverlay);

        this.listener = listener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClicked(getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (listener != null) {
            return listener.onItemLongClicked(getLayoutPosition());
        }

        return false;
    }

    public interface ClickListener {
         void onItemClicked(int position);
         boolean onItemLongClicked(int position);
    }
}

}*/
