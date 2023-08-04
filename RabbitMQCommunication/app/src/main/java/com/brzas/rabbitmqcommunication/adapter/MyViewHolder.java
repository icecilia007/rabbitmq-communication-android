package com.brzas.rabbitmqcommunication.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.rabbitmqcommunication.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView messageTextView;
    TextView tmstTextView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.message);
        tmstTextView = itemView.findViewById(R.id.tmst);
    }
}
