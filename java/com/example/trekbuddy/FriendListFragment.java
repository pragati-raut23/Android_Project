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
//public class FriendListActivity extends AppCompatActivity {
//    private ListView friendListView;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private List<User> friendsList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friend_list);
//
//        friendListView = findViewById(R.id.friend_list);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        friendsList = new ArrayList<>();
//        fetchAddedFriends();
//    }
//
//    private void fetchAddedFriends() {
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        mDatabase.child("users").child(currentUserId).child("friends")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        friendsList.clear();
//                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
//                            String friendId = friendSnapshot.getKey();
//                            fetchFriendDetails(friendId); // Populate details for each friend
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(FriendListActivity.this, "Failed to load friends", Toast.LENGTH_SHORT).show();
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
//                User friend = new User(friendId, name,email); // Ensure constructor matches your data
//                friendsList.add(friend);
//                updateFriendList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(FriendListActivity.this, "Failed to fetch friend details", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateFriendList() {
//        FriendListAdapter adapter = new FriendListAdapter(this, friendsList);
//        friendListView.setAdapter(adapter);
//        friendListView.setOnItemClickListener((parent, view, position, id) -> {
//            User friend = friendsList.get(position);
//            openChat(friend);
//        });
//    }
//
//    private void openChat(User friend) {
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.putExtra("friendId", friend.getUserId());
//        intent.putExtra("friendName", friend.getName());
//        startActivity(intent);
//    }
//}


package com.example.trekbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendListFragment extends Fragment {
    private ListView friendListView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private List<User> friendsList;

    public FriendListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        friendListView = view.findViewById(R.id.friend_list);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        friendsList = new ArrayList<>();
        fetchAddedFriends();

        return view;
    }

    private void fetchAddedFriends() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(currentUserId).child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        friendsList.clear();
                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                            String friendId = friendSnapshot.getKey();
                            fetchFriendDetails(friendId); // Populate details for each friend
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
                friendsList.add(friend);
                updateFriendList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch friend details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFriendList() {
        FriendListAdapter adapter = new FriendListAdapter(getContext(), friendsList);
        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener((parent, view, position, id) -> {
            User friend = friendsList.get(position);
            openChat(friend);
        });
    }

    private void openChat(User friend) {
        ChatFragment chatFragment = new ChatFragment();

        // Set up arguments to pass friend details to ChatFragment
        Bundle args = new Bundle();
        args.putString("friendId", friend.getUserId());
        args.putString("friendName", friend.getName());
        chatFragment.setArguments(args);

        // Replace the current fragment with ChatFragment and add it to the back stack
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_content, chatFragment);
        transaction.addToBackStack(null); // Enable back navigation
        transaction.commit();
    }
}
