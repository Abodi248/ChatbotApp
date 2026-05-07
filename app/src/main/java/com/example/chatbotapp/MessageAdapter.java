package com.example.chatbotapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.data.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.tvMessage.setText(msg.content);
        holder.tvTimestamp.setText(sdf.format(new Date(msg.timestamp)));

        Context context = holder.itemView.getContext();

        if (msg.isUser) {
            holder.container.setGravity(Gravity.END);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_bubble_user);
            holder.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.text_on_amber));
            holder.tvTimestamp.setTextColor(ContextCompat.getColor(context, R.color.text_on_amber_muted));
        } else {
            holder.container.setGravity(Gravity.START);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_bubble_bot);
            holder.tvMessage.setTextColor(ContextCompat.getColor(context, R.color.text_cream));
            holder.tvTimestamp.setTextColor(ContextCompat.getColor(context, R.color.text_warm));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView tvMessage, tvTimestamp;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            container   = itemView.findViewById(R.id.message_container);
            tvMessage   = itemView.findViewById(R.id.tv_message);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);
        }
    }
}
