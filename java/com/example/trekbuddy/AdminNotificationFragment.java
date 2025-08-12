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

public class AdminNotificationFragment extends Fragment {
    private ListView notificationListView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private List<String> notificationList;

    public AdminNotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_notification, container, false);

        notificationListView = view.findViewById(R.id.notification_list);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        notificationList = new ArrayList<>();
        fetchNotifications();

        return view;
    }

    private void fetchNotifications() {
        // Fetch notifications from the database (assuming "notifications" is the node used)
        mDatabase.child("notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notificationList.clear();
                for (DataSnapshot notificationSnapshot : snapshot.getChildren()) {
                    String notificationMessage = notificationSnapshot.getValue(String.class);
                    if (notificationMessage != null) {
                        notificationList.add(notificationMessage);
                    }
                }
                updateNotificationList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load notifications", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateNotificationList() {
        NotificationAdapter adapter = new NotificationAdapter(getContext(), notificationList);
        notificationListView.setAdapter(adapter);
    }
}
