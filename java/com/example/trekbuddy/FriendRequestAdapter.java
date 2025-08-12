package com.example.trekbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestAdapter extends ArrayAdapter<User> {
    private final Context context;
    private final List<User> friendRequests;

    public FriendRequestAdapter(Context context, List<User> friendRequests) {
        super(context, R.layout.friend_request_item, friendRequests);
        this.context = context;
        this.friendRequests = friendRequests;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_request_item, parent, false);
        }

        User request = friendRequests.get(position);
        TextView friendName = convertView.findViewById(R.id.friend_name);
        Button acceptButton = convertView.findViewById(R.id.accept_button);
        Button declineButton = convertView.findViewById(R.id.decline_button);

        friendName.setText(request.getName());

        // Accept button click listener
        acceptButton.setOnClickListener(v -> acceptRequest(request.getUserId()));

        // Decline button click listener
        declineButton.setOnClickListener(v -> declineRequest(request.getUserId()));

        return convertView;
    }

    private void acceptRequest(String senderId) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // Add to current user's friends list
        mDatabase.child("users").child(currentUserId).child("friends").child(senderId).setValue(true)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        // Also add current user to the sender's friends list
                        mDatabase.child("users").child(senderId).child("friends").child(currentUserId).setValue(true)
                                .addOnCompleteListener(innerTask -> {
                                    if (innerTask.isSuccessful()) {
                                        Toast.makeText(FriendRequestAdapter.this.getContext(), "Friend Added Successfully", Toast.LENGTH_SHORT).show();
                                        // Remove friend request from both users' friend_requests lists
                                        mDatabase.child("friend_requests").child(currentUserId).child(senderId).removeValue();
                                        mDatabase.child("friend_requests").child(senderId).child(currentUserId).removeValue();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(FriendRequestAdapter.this.getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void declineRequest(String senderId) {

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // Remove the friend request
        mDatabase.child("friend_requests").child(currentUserId).child(senderId).removeValue();
        mDatabase.child("friend_requests").child(senderId).child(currentUserId).removeValue();
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        // Update the request status to "rejected"
//        mDatabase.child("friend_requests").child(currentUserId).child(senderId).setValue("rejected")
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(getContext(), "Friend request declined", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(), "Failed to decline request", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }


}
