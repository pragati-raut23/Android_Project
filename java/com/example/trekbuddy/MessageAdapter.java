package com.example.trekbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Messages> messagesList;
    private String currentUserId;

    public MessageAdapter(Context context, List<Messages> messagesList, String currentUserId) {
        this.messagesList = messagesList;
        this.currentUserId = currentUserId;
    }

    @Override
    public int getItemViewType(int position) {
        Messages message = messagesList.get(position);
        String senderId = message.getSenderId();

        // Check for null currentUserId and senderId before calling equals
        if (currentUserId != null && senderId != null && currentUserId.equals(senderId)) {
            return VIEW_TYPE_MESSAGE_SENT; // Message is sent by the current user
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED; // Message is received from the friend
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Messages message = messagesList.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((SentMessageViewHolder) holder).bind(message);
        } else {
            ((ReceivedMessageViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    // Sent message view holder
    public class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.sent_message_text);
        }

        void bind(Messages message) {
            messageText.setText(message.getContent());
        }
    }

    // Received message view holder
    public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.received_message_text);
        }

        void bind(Messages message) {
            messageText.setText(message.getContent());
        }
    }
}
