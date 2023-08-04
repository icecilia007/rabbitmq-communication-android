package com.brzas.rabbitmqcommunication.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brzas.rabbitmqcommunication.R;
import com.brzas.rabbitmqcommunication.models.Message;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Message> messageList = new ArrayList<>();

    public MessageRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int itemPosition = position;
        final Message message = messageList.get(itemPosition);

        holder.messageTextView.setText(message.getMessage());
        holder.tmstTextView.setText(message.getTmst().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    public Message addMessage(Message message){
        int pos = getItemCount();
        if(!messageList.contains(message)){
            messageList.add(message);
            notifyItemInserted(pos);
            Log.d(TAG, "addMessage " + message);
            return message;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
