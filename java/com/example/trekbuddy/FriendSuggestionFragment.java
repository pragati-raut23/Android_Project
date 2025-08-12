//package com.example.trekbuddy;
//
//import android.os.Bundle;
//import android.widget.ListView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class FriendSuggestionFragment extends Fragment {
//    private ListView friendListView;
//    private DatabaseReference mDatabase;
//    private FirebaseAuth mAuth;
//    private List<User> suggestedFriendsList;
//    private Set<String> addedFriendsSet;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friend_suggestion);
//
//        friendListView = findViewById(R.id.friend_list);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        suggestedFriendsList = new ArrayList<>();
//        addedFriendsSet = new HashSet<>();
//
//        fetchAddedFriends();
//    }
//
//
//    private void fetchAddedFriends() {
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        mDatabase.child("users").child(currentUserId).child("friends")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        addedFriendsSet.clear();
//                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
//                            String friendId = friendSnapshot.getKey();
//                            addedFriendsSet.add(friendId);
//                        }
//                        fetchFriendSuggestions();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(FriendSuggestionActivity.this, "Failed to load friends", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void fetchFriendSuggestions() {
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        mDatabase.child("users").child(currentUserId).child("preferences")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        List<String> currentUserPreferences = new ArrayList<>();
//                        for (DataSnapshot prefSnapshot : snapshot.getChildren()) {
//                            String locationId = prefSnapshot.getKey(); // Using ID
//                            currentUserPreferences.add(locationId);
//                        }
//
//                        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                suggestedFriendsList.clear();
//                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                                    String userId = userSnapshot.getKey();
//                                    if (!userId.equals(currentUserId) && !addedFriendsSet.contains(userId)) {
//                                        String name = userSnapshot.child("name").getValue(String.class);
//                                        String email = userSnapshot.child("email").getValue(String.class);
//                                        List<String> friendPreferences = new ArrayList<>();
//                                        for (DataSnapshot prefSnapshot : userSnapshot.child("preferences").getChildren()) {
//                                            String friendLocationId = prefSnapshot.getKey(); // Using ID
//                                            friendPreferences.add(friendLocationId);
//                                        }
//
//                                        // Check for matching preferences (IDs)
//                                        for (String prefId : currentUserPreferences) {
//                                            if (friendPreferences.contains(prefId)) {
//                                                User friend = new User(userId, name,email, friendPreferences);
//                                                suggestedFriendsList.add(friend);
//                                                break; // No need to check further if a match is found
//                                            }
//                                        }
//                                    }
//                                }
//                                updateListView();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                Toast.makeText(FriendSuggestionActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(FriendSuggestionActivity.this, "Error fetching user preferences", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void updateListView() {
//        FriendSuggestionAdapter adapter = new FriendSuggestionAdapter(this, suggestedFriendsList);
//        friendListView.setAdapter(adapter);
//    }
//
//    private void sendFriendRequest(String friendId) {
//        String currentUserId = mAuth.getCurrentUser().getUid();
//        // Store friend request in both users' data for retrieval
//        mDatabase.child("friend_requests").child(friendId).child(currentUserId).setValue(true)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(FriendSuggestionActivity.this, "Friend request sent!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(FriendSuggestionActivity.this, "Failed to send request.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
//


package com.example.trekbuddy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FriendSuggestionFragment extends Fragment {
    private ListView friendListView;
    private TextView noFriendsText;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private List<User> suggestedFriendsList;
    private Set<String> addedFriendsSet;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_suggestion, container, false);

        friendListView = view.findViewById(R.id.friend_list);
        noFriendsText = view.findViewById(R.id.no_friends_text);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        suggestedFriendsList = new ArrayList<>();
        addedFriendsSet = new HashSet<>();

        fetchAddedFriends();
        return view;
    }

    private void fetchAddedFriends() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(currentUserId).child("friends")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        addedFriendsSet.clear();
                        for (DataSnapshot friendSnapshot : snapshot.getChildren()) {
                            String friendId = friendSnapshot.getKey();
                            addedFriendsSet.add(friendId);
                        }
                        fetchFriendSuggestions();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Failed to load friends", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchFriendSuggestions() {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(currentUserId).child("preferences")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> currentUserPreferences = new ArrayList<>();
                        for (DataSnapshot prefSnapshot : snapshot.getChildren()) {
                            String locationId = prefSnapshot.getKey();
                            currentUserPreferences.add(locationId);
                        }

                        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                suggestedFriendsList.clear();
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    String userId = userSnapshot.getKey();
                                    if (!userId.equals(currentUserId) && !addedFriendsSet.contains(userId)) {
                                        String name = userSnapshot.child("name").getValue(String.class);
                                        String email = userSnapshot.child("email").getValue(String.class);
                                        List<String> friendPreferences = new ArrayList<>();
                                        for (DataSnapshot prefSnapshot : userSnapshot.child("preferences").getChildren()) {
                                            String friendLocationId = prefSnapshot.getKey();
                                            friendPreferences.add(friendLocationId);
                                        }

                                        for (String prefId : currentUserPreferences) {
                                            if (friendPreferences.contains(prefId)) {
                                                User friend = new User(userId, name, email, friendPreferences);
                                                suggestedFriendsList.add(friend);
                                                break;
                                            }
                                        }
                                    }
                                }
                                updateListView();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getActivity(), "Error fetching user data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Error fetching user preferences", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateListView() {
        if (suggestedFriendsList.isEmpty()) {
            noFriendsText.setVisibility(View.VISIBLE);   // Show message if no suggestions
            friendListView.setVisibility(View.GONE);     // Hide ListView
        } else {
            noFriendsText.setVisibility(View.GONE);      // Hide message if suggestions are available
            friendListView.setVisibility(View.VISIBLE);  // Show ListView

            FriendSuggestionAdapter adapter = new FriendSuggestionAdapter(getActivity(), suggestedFriendsList);
            friendListView.setAdapter(adapter);
        }

//        FriendSuggestionAdapter adapter = new FriendSuggestionAdapter(getActivity(), suggestedFriendsList);
//        friendListView.setAdapter(adapter);
    }

    private void sendFriendRequest(String friendId) {
        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("friend_requests").child(friendId).child(currentUserId).setValue(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Friend request sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to send request.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
