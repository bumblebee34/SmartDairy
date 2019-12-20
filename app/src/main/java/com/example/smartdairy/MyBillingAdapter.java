package com.example.smartdairy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyBillingAdapter extends RecyclerView.Adapter<MyBillingAdapter.MessageViewHolder>{
    private List<ArrayList<String>> userMessageList;
    private View view;

    public MyBillingAdapter(List<ArrayList<String>> userMessageList) {
        this.userMessageList = userMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView fromdate,todate,totalmilkcost,dedloan,dedmedical,dedcattlefeed,paid,paiddate;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            fromdate = itemView.findViewById(R.id.billing_from_date_edit_text);
            todate = itemView.findViewById(R.id.billing_to_date_edit_text);
            totalmilkcost = itemView.findViewById(R.id.billing_total_milk_cost_edit_text);
            dedloan = itemView.findViewById(R.id.billing_deducted_loan_cost_edit_text);
            dedcattlefeed = itemView.findViewById(R.id.billing_deducted_cattle_feed_cost_edit_text);
            dedmedical = itemView.findViewById(R.id.billing_deducted_medical_cost_edit_text);
            paid = itemView.findViewById(R.id.billing_paid_amount_edit_text);
            paiddate = itemView.findViewById(R.id.billing_paid_date_edit_text);
        }
    }

    @NonNull
    @Override
    public MyBillingAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_log_layout,parent,false);
        return new MyBillingAdapter.MessageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyBillingAdapter.MessageViewHolder holder, int position) {
        String fromdate = userMessageList.get(position).get(0);
        String todate = userMessageList.get(position).get(1);
        String totalmilkcost = userMessageList.get(position).get(2);
        String dedloan = userMessageList.get(position).get(3);
        String dedcattlefeed = userMessageList.get(position).get(4);
        String dedmedical = userMessageList.get(position).get(5);
        String paid = userMessageList.get(position).get(6);
        String paiddate = userMessageList.get(position).get(7);
        holder.fromdate.setText(fromdate);
        holder.todate.setText(todate);
        holder.totalmilkcost.setText("Rs."+totalmilkcost);
        holder.dedloan.setText("Rs."+dedloan);
        holder.dedcattlefeed.setText("Rs."+dedcattlefeed);
        holder.dedmedical.setText("Rs."+dedmedical);
        holder.paid.setText("Rs."+paid);
        holder.paiddate.setText(paiddate);
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }
}
