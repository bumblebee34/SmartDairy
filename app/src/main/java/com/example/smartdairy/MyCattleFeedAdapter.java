package com.example.smartdairy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCattleFeedAdapter extends RecyclerView.Adapter<MyCattleFeedAdapter.MessageViewHolder>{

    private List<ArrayList<String>> userMessageList;
    private View view;

    public MyCattleFeedAdapter(List<ArrayList<String>> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView date,quantity,weight,rate,cost,type;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.cattle_feed_date_edit_text);
            quantity = itemView.findViewById(R.id.cattle_feed_quantity_edit_text);
            weight = itemView.findViewById(R.id.cattle_feed_weight_edit_text);
            rate = itemView.findViewById(R.id.cattle_feed_rate_edit_text);
            cost = itemView.findViewById(R.id.cattle_feed_cost_edit_text);
            type = itemView.findViewById(R.id.cattle_feed_type_edit_text);
        }
    }

    @NonNull
    @Override
    public MyCattleFeedAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_feed_log_layout,parent,false);
        return new MyCattleFeedAdapter.MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyCattleFeedAdapter.MessageViewHolder holder, int position) {
        String date = userMessageList.get(position).get(0);
        String type = userMessageList.get(position).get(1);
        String quantity = userMessageList.get(position).get(2);
        String weight = userMessageList.get(position).get(3);
        String rate = userMessageList.get(position).get(4);
        String cost = userMessageList.get(position).get(5);
        holder.date.setText(date);
        holder.type.setText(type);
        holder.quantity.setText(quantity+" Sacks");
        holder.weight.setText(weight+"Kg");
        holder.rate.setText("Rs."+rate);
        holder.cost.setText("Rs."+cost);

    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }
}
