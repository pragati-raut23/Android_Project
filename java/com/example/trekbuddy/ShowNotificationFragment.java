package com.example.trekbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ShowNotificationFragment extends Fragment {

    public ShowNotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_notification, container, false);

        // Automatically navigate to FriendRequestFragment when this fragment is loaded
        navigateToFriendRequestFragment();

        return view;
    }

    private void navigateToFriendRequestFragment() {
        // Replace the current fragment with FriendRequestFragment

//        Fragment selectedFrag = new AdminNotificationFragment();
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.fragment_container, selectedFrag);
//        transaction.commit();

        Fragment friendRequestFragment = new FriendRequestFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, friendRequestFragment);
        transaction.commit();
        }
    }

