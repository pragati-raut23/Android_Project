package com.example.trekbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> friendList;

    public ChatListAdapter(Context context, List<User> friendList) {
        super(context, R.layout.chat_list_item, friendList);
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
        }

        User friend = friendList.get(position);
        TextView friendNameText = convertView.findViewById(R.id.friend_name_text);
        friendNameText.setText(friend.getName());

        return convertView;
    }
}
