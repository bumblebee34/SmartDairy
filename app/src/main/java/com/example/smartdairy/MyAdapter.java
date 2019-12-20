package com.example.smartdairy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MessageViewHolder>{

    private List<ArrayList<String>> userMessageList;
    private View view;

    public MyAdapter(List<ArrayList<String>> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView date,quantity,fat,rate,cost,type;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.milk_log_date_edit_text);
            quantity = itemView.findViewById(R.id.milk_log_quantity_edit_text);
            fat = itemView.findViewById(R.id.milk_log_fat_edit_text);
            rate = itemView.findViewById(R.id.milk_log_rate_edit_text);
            cost = itemView.findViewById(R.id.milk_log_cost_edit_text);
            type = itemView.findViewById(R.id.milk_log_type_edit_text);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.milk_log_layout,parent,false);
        return new MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        String date = userMessageList.get(position).get(0);
        String type = userMessageList.get(position).get(1);
        String quantity = userMessageList.get(position).get(2);
        String fat = userMessageList.get(position).get(3);
        String rate = userMessageList.get(position).get(4);
        String cost = userMessageList.get(position).get(5);
        holder.date.setText(date);
        holder.type.setText(type);
        holder.quantity.setText(quantity+"L");
        holder.fat.setText(fat);
        holder.rate.setText("Rs."+rate);
        holder.cost.setText("Rs."+cost);

    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

}
