package com.example.trekbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FriendSuggestionAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> suggestedFriends;

    public FriendSuggestionAdapter(Context context, List<User> suggestedFriends) {
        super(context, R.layout.friend_suggestion_item, suggestedFriends);
        this.context = context;
        this.suggestedFriends = suggestedFriends;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_suggestion_item, parent, false);
        }

        User friend = suggestedFriends.get(position);
        TextView friendName = convertView.findViewById(R.id.friend_name); // Updated to display the name
        Button addButton = convertView.findViewById(R.id.add_button);

        // Show the user's name instead of email
        friendName.setText(friend.getName());

        addButton.setOnClickListener(v -> addFriend(friend));

        return convertView;
    }

    private void addFriend(User friend) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // Create a friend request in the database
        mDatabase.child("friend_requests").child(currentUserId).child(friend.getUserId()).setValue(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Friend request sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to send friend request", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
