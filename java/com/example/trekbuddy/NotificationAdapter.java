package com.example.trekbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> notifications;

    public NotificationAdapter(Context context, List<String> notifications) {
        super(context, R.layout.notification_list_item, notifications);
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notification_list_item, parent, false);
        }

        String notification = notifications.get(position);
        TextView notificationTextView = convertView.findViewById(R.id.notification_text);
        notificationTextView.setText(notification);

        return convertView;
    }
}
