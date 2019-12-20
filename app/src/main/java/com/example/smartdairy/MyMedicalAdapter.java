package com.example.smartdairy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyMedicalAdapter extends RecyclerView.Adapter<MyMedicalAdapter.MessageViewHolder>{

    private List<ArrayList<String>> userMessageList;
    private View view;

    public MyMedicalAdapter(List<ArrayList<String>> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView date,amount;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.medical_date_edit_text);
            amount = itemView.findViewById(R.id.medical_amount_edit_text);
        }
    }

    @NonNull
    @Override
    public MyMedicalAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_log_layout,parent,false);
        return new MyMedicalAdapter.MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyMedicalAdapter.MessageViewHolder holder, int position) {
        String amount = userMessageList.get(position).get(0);
        String date = userMessageList.get(position).get(1);
        holder.date.setText(date);
        holder.amount.setText(amount);


    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }
}
