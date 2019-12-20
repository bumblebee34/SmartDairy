package com.example.smartdairy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyLoanAdapter extends RecyclerView.Adapter<MyLoanAdapter.MessageViewHolder>{

    private List<ArrayList<String>> userMessageList;
    private View view;

    public MyLoanAdapter(List<ArrayList<String>> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView date,amount;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.loan_date_edit_text);
            amount = itemView.findViewById(R.id.loan_amount_edit_text);
        }
    }

    @NonNull
    @Override
    public MyLoanAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_log_layout,parent,false);
        return new MyLoanAdapter.MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyLoanAdapter.MessageViewHolder holder, int position) {
        String amount = userMessageList.get(position).get(0);
        String date = userMessageList.get(position).get(1);
        holder.date.setText(date);
        holder.amount.setText("Rs."+amount);
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }
}
