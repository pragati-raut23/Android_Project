//package com.example.trekbuddy;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.ListView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChatListActivity extends AppCompatActivity {
//    private ListView friendListView;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private List<User> friendList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_list);
//
//        friendListView = findViewById(R.id.friend_list_view);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        friendList = new ArrayList<>();
//        loadFriendList();
//    }
//
//    private void loadFriendList() {
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        mDatabase.child("users").child(currentUserId).child("friends")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        friendList.clear();
//                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
//                            String friendId = friendSnapshot.getKey();
//                            fetchFriendDetails(friendId); // Fetch details for each friend
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(ChatListActivity.this, "Failed to load friends", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void fetchFriendDetails(String friendId) {
//        mDatabase.child("users").child(friendId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.child("name").getValue(String.class);
//                String email = snapshot.child("email").getValue(String.class);
//                User friend = new User(friendId, name, email);
//                friendList.add(friend);
//                updateFriendList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ChatListActivity.this, "Failed to fetch friend details", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // List of friends is shown and each friend item opens ChatActivity on click
//    private void updateFriendList() {
//        ChatListAdapter adapter = new ChatListAdapter(this, friendList);
//        friendListView.setAdapter(adapter);
//
//        // Set up click listener to open ChatActivity with friend details
//        friendListView.setOnItemClickListener((parent, view, position, id) -> {
//            User friend = friendList.get(position);
//            Intent intent = new Intent(ChatListActivity.this, ChatFragment.class);
//            intent.putExtra("friendId", friend.getUserId());
//            intent.putExtra("friendName", friend.getName());
//            startActivity(intent);
//        });
//    }
//
//}


package com.example.trekbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {
    private ListView friendListView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private List<User> friendList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // Initialize views
        friendListView = view.findViewById(R.id.friend_list_view);

        // Initialize Firebase and data
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        friendList = new ArrayList<>();

        loadFriendList();
        return view;
    }

    private void loadFriendList() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(currentUserId).child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendList.clear();
                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                            String friendId = friendSnapshot.getKey();
                            fetchFriendDetails(friendId); // Fetch details for each friend
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed to load friends", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchFriendDetails(String friendId) {
        mDatabase.child("users").child(friendId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                User friend = new User(friendId, name, email);
                friendList.add(friend);
                updateFriendList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch friend details", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void updateFriendList() {
//        ChatListAdapter adapter = new ChatListAdapter(getContext(), friendList);
//        friendListView.setAdapter(adapter);
//
//        // Set up click listener to open ChatActivity with friend details
//        friendListView.setOnItemClickListener((parent, view, position, id) -> {
//            User friend = friendList.get(position);
//            Intent intent = new Intent(getContext(), ChatActivity.class);
//            intent.putExtra("friendId", friend.getUserId());
//            intent.putExtra("friendName", friend.getName());
//            startActivity(intent);
//        });
//    }

    private void updateFriendList() {
        ChatListAdapter adapter = new ChatListAdapter(getContext(), friendList);
        friendListView.setAdapter(adapter);

        // Set up click listener to navigate to ChatFragment with friend details
        friendListView.setOnItemClickListener((parent, view, position, id) -> {
            User friend = friendList.get(position);

            // Create a new instance of ChatFragment
            ChatFragment chatFragment = new ChatFragment();

            // Pass friend details to ChatFragment using arguments
            Bundle args = new Bundle();
            args.putString("friendId", friend.getUserId());
            args.putString("friendName", friend.getName());
            chatFragment.setArguments(args);

            // Perform fragment transaction to navigate to ChatFragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_content, chatFragment) // assuming fragment_container is the container in the parent activity
                    .addToBackStack(null) // adds to back stack for back navigation
                    .commit();
        });
    }

}
