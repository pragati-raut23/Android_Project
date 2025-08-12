////package com.example.trekbuddy;
////
////import android.os.Bundle;
////import android.widget.ListView;
////import android.widget.Toast;
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////import com.google.firebase.auth.FirebaseAuth;
////import com.google.firebase.database.DataSnapshot;
////import com.google.firebase.database.DatabaseError;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////import com.google.firebase.database.ValueEventListener;
////
////import java.util.ArrayList;
////import java.util.List;
////
////public class FriendRequestActivity extends AppCompatActivity {
////    private ListView requestListView;
////    private DatabaseReference mDatabase;
////    private FirebaseAuth mAuth;
////    private List<User> requestList;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_friend_request);
////
////        requestListView = findViewById(R.id.request_list);
////        mAuth = FirebaseAuth.getInstance();
////        mDatabase = FirebaseDatabase.getInstance().getReference();
////
////        requestList = new ArrayList<>();
////        fetchFriendRequests();
////    }
////
////    private void fetchFriendRequests() {
////        String currentUserId = mAuth.getCurrentUser().getUid();
////        mDatabase.child("friend_requests").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                requestList.clear();
////                for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
////                    String senderId = requestSnapshot.getKey();
////                    fetchUserDetails(senderId); // Helper method to retrieve user details
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Toast.makeText(FriendRequestActivity.this, "Failed to fetch requests", Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
////
////    private void fetchUserDetails(String senderId) {
////        mDatabase.child("users").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                String name = snapshot.child("name").getValue(String.class);
////                String email = snapshot.child("email").getValue(String.class);
////                User user = new User(senderId, name, email, new ArrayList<>());
////                requestList.add(user);
////                updateRequestList();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Toast.makeText(FriendRequestActivity.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
////
////    private void updateRequestList() {
////        FriendRequestAdapter adapter = new FriendRequestAdapter(this, requestList);
////        requestListView.setAdapter(adapter);
////    }
////}
//
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
import java.util.List;

public class FriendRequestFragment extends Fragment {
    private ListView requestListView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private List<User> requestList;
    private TextView noNotificationText;

    public FriendRequestFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        View view1 = inflater.inflate(R.layout.fragment_admin_notification, container, false);
//
//        notificationListView = view1.findViewById(R.id.notification_list);
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        notificationList = new ArrayList<>();
//        fetchNotifications();

        View view = inflater.inflate(R.layout.fragment_friend_request, container, false);

        requestListView = view.findViewById(R.id.request_list);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        noNotificationText = view.findViewById(R.id.no_notification_text);

        requestList = new ArrayList<>();
        fetchFriendRequests();

        return view;
    }

    private void fetchFriendRequests() {

        String currentUserId = mAuth.getCurrentUser().getUid();
        mDatabase.child("friend_requests").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                if (snapshot.exists() && snapshot.hasChildren()) {
                    // Friend requests are present
                    for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                        String senderId = requestSnapshot.getKey();
                        fetchUserDetails(senderId); // Helper method to retrieve user details
                    }
                } else {
                    // No friend requests present
                    noNotificationText.setVisibility(View.VISIBLE);   // Show message if no suggestions
                    requestListView.setVisibility(View.GONE);
                   // Toast.makeText(getContext(), "No friend requests found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch requests", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserDetails(String senderId) {
        mDatabase.child("users").child(senderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                User user = new User(senderId, name, email, new ArrayList<>());
                requestList.add(user);
                updateRequestList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRequestList() {
        FriendRequestAdapter adapter = new FriendRequestAdapter(getContext(), requestList);
        requestListView.setAdapter(adapter);
    }

    // admin notification fetch function
//
//    private void fetchNotifications() {
//        // Fetch notifications from the database (assuming "notifications" is the node used)
//        mDatabase.child("notifications").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                notificationList.clear();
//                for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
//                    String notificationMessage = notificationSnapshot.getValue(String.class);
//                    if (notificationMessage != null) {
//                        notificationList.add(notificationMessage);
//                    }
//                }
//                updateNotificationList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Failed to load notifications", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void updateNotificationList() {
//        NotificationAdapter adapter = new NotificationAdapter(getContext(), notificationList);
//        notificationListView.setAdapter(adapter);
//    }
}