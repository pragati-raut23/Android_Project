//package com.example.trekbuddy;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.*;
//
//public class ChatActivity extends AppCompatActivity {
//    private String currentUserId;
//    private String friendId;
//    private String friendName;
//    private DatabaseReference mDatabase;
//    private RecyclerView messageRecyclerView;
//    private EditText messageInput;
//    private Button sendButton;
//    private MessageAdapter messageAdapter;
//    private List<Messages> messagesList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        // Initialize Firebase and get Intent extras for friend details
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser == null) {
//            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        currentUserId = currentUser.getUid();
//        friendId = getIntent().getStringExtra("friendId");
//        friendName = getIntent().getStringExtra("friendName");
//
//        if (friendId == null || friendId.isEmpty()) {
//            Toast.makeText(this, "Friend ID is missing.", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        // Setup UI components
//        TextView chatHeader = findViewById(R.id.chat_header);
//        messageRecyclerView = findViewById(R.id.message_recycler_view);
//        messageInput = findViewById(R.id.message_input);
//        sendButton = findViewById(R.id.send_button);
//
//        chatHeader.setText(friendName != null ? friendName : "Chat");
//
//        // Set up RecyclerView and adapter
//        messagesList = new ArrayList<>();
//        messageAdapter = new MessageAdapter(this, messagesList, currentUserId);
//        messageRecyclerView.setAdapter(messageAdapter);
//        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Load chat history and handle send message functionality
//        loadMessages();
//        sendButton.setOnClickListener(v -> sendMessage());
//    }
//
//
//    private void checkIfFriendIsAccepted(String friendId, OnFriendCheckListener listener) {
//        mDatabase.child("users").child(currentUserId).child("friends").child(friendId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        listener.onFriendCheck(snapshot.exists());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        listener.onFriendCheck(false);
//                    }
//                });
//    }
//
//    private void setupChat() {
//        mDatabase.child("users").child(currentUserId).child("friends").child(friendId)
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            loadMessages();
//                        } else {
//                            Toast.makeText(ChatActivity.this, "Friendship not established.", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ChatActivity.this, "Failed to verify friendship.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void loadMessages() {
//        mDatabase.child("messages").child(currentUserId).child(friendId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        messagesList.clear(); // Clear the list to prevent duplicates
//                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
//                            Messages message = messageSnapshot.getValue(Messages.class);
//                            if (message != null) {
//                                messagesList.add(message);
//                            }
//                        }
//                        messageAdapter.notifyDataSetChanged();
//                        messageRecyclerView.scrollToPosition(messagesList.size() - 1); // Scroll to last message
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//
//
//    private void sendMessage() {
//        String messageText = messageInput.getText().toString().trim();
//
//        if (!messageText.isEmpty()) {
//            // Create a map to hold the message data
//            Map<String, Object> messageMap = new HashMap<>();
//            messageMap.put("senderId", currentUserId); // Use the current user's ID
//            messageMap.put("receiverId", friendId);    // Include the friend's ID
//            messageMap.put("content", messageText);     // Message text
//            messageMap.put("timestamp", System.currentTimeMillis()); // Timestamp for the message
//
//            // Define the path for the message in the database
//            DatabaseReference senderMessageRef = mDatabase.child("messages").child(currentUserId).child(friendId).push();
//            DatabaseReference receiverMessageRef = mDatabase.child("messages").child(friendId).child(currentUserId).push();
//
//            // Store the message for both the sender and receiver
//            senderMessageRef.setValue(messageMap).addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    // Also write to the receiver's chat
//                    receiverMessageRef.setValue(messageMap).addOnCompleteListener(receiverTask -> {
//                        if (receiverTask.isSuccessful()) {
//                            messageInput.setText(""); // Clear the input field after sending
//                            // Optionally: Update local message list if needed
//                            messagesList.add(new Messages(currentUserId, friendId, messageText, System.currentTimeMillis()));
//                            messageAdapter.notifyItemInserted(messagesList.size() - 1);
//                            messageRecyclerView.scrollToPosition(messagesList.size() - 1); // Scroll to the last message
//                        } else {
//                            Toast.makeText(ChatActivity.this, "Failed to send message to friend", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(ChatActivity.this, "Failed to send message", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//
//
//
//    interface OnFriendCheckListener {
//        void onFriendCheck(boolean isAccepted);
//    }
//}

package com.example.trekbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatFragment extends Fragment {
    private String currentUserId;
    private String friendId;
    private String friendName;
    private DatabaseReference mDatabase;
    private RecyclerView messageRecyclerView;
    private EditText messageInput;
    private Button sendButton;
    private MessageAdapter messageAdapter;
    private List<Messages> messagesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Initialize Firebase and get arguments for friend details
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(getActivity(), "User not logged in.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return null;
        }

        currentUserId = currentUser.getUid();
        if (getArguments() != null) {
            friendId = getArguments().getString("friendId");
            friendName = getArguments().getString("friendName");
        }

        if (friendId == null || friendId.isEmpty()) {
            Toast.makeText(getActivity(), "Friend ID is missing.", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return null;
        }

        // Setup UI components
        TextView chatHeader = view.findViewById(R.id.chat_header);
        messageRecyclerView = view.findViewById(R.id.message_recycler_view);
        messageInput = view.findViewById(R.id.message_input);
        sendButton = view.findViewById(R.id.send_button);

        chatHeader.setText(friendName != null ? friendName : "Chat");

        // Set up RecyclerView and adapter
        messagesList = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), messagesList, currentUserId);
        messageRecyclerView.setAdapter(messageAdapter);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Load chat history and handle send message functionality
        loadMessages();
        sendButton.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void checkIfFriendIsAccepted(String friendId, OnFriendCheckListener listener) {
        mDatabase.child("users").child(currentUserId).child("friends").child(friendId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listener.onFriendCheck(snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        listener.onFriendCheck(false);
                    }
                });
    }

    private void setupChat() {
        mDatabase.child("users").child(currentUserId).child("friends").child(friendId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            loadMessages();
                        } else {
                            Toast.makeText(getActivity(), "Friendship not established.", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Failed to verify friendship.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadMessages() {
        mDatabase.child("messages").child(currentUserId).child(friendId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesList.clear();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            Messages message = messageSnapshot.getValue(Messages.class);
                            if (message != null) {
                                messagesList.add(message);
                            }
                        }
                        messageAdapter.notifyDataSetChanged();
                        messageRecyclerView.scrollToPosition(messagesList.size() - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Failed to load messages.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();

        if (!messageText.isEmpty()) {
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("senderId", currentUserId);
            messageMap.put("receiverId", friendId);
            messageMap.put("content", messageText);
            messageMap.put("timestamp", System.currentTimeMillis());

            DatabaseReference senderMessageRef = mDatabase.child("messages").child(currentUserId).child(friendId).push();
            DatabaseReference receiverMessageRef = mDatabase.child("messages").child(friendId).child(currentUserId).push();

            senderMessageRef.setValue(messageMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    receiverMessageRef.setValue(messageMap).addOnCompleteListener(receiverTask -> {
                        if (receiverTask.isSuccessful()) {
                            messageInput.setText("");
                            messagesList.add(new Messages(currentUserId, friendId, messageText, System.currentTimeMillis()));
                            messageAdapter.notifyItemInserted(messagesList.size() - 1);
                            messageRecyclerView.scrollToPosition(messagesList.size() - 1);
                        } else {
                            Toast.makeText(getActivity(), "Failed to send message to friend", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Failed to send message", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Please enter a message.", Toast.LENGTH_SHORT).show();
        }
    }

    interface OnFriendCheckListener {
        void onFriendCheck(boolean isAccepted);
    }
}
