package com.example.trekbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FriendListAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> friendsList;

    public FriendListAdapter(Context context, List<User> friendsList) {
        super(context, R.layout.friend_list_item, friendsList);
        this.context = context;
        this.friendsList = friendsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_list_item, parent, false);
        }

        User friend = friendsList.get(position);
        TextView friendName = convertView.findViewById(R.id.friend_name);

        // Display the friend's name
        friendName.setText(friend.getName());

        // OnClickListener to open ChatActivity with selected friend
//        convertView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, Chat.class);
//            intent.putExtra("friendId", friend.getUserId()); // Pass the friend's user ID
//            intent.putExtra("friendName", friend.getName()); // Pass the name for display in ChatActivity
//            context.startActivity(intent);
//        });

        return convertView;
    }


}
