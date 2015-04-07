package com.sociallunch.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sociallunch.android.R;
import com.sociallunch.android.adapters.UserJoinedArrayAdapter;
import com.sociallunch.android.models.User;
import java.util.ArrayList;

public class UsersJoinedFragment extends Fragment {

    public static UsersJoinedFragment newInstance() {
        UsersJoinedFragment fragment = new UsersJoinedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public UsersJoinedFragment() {
    }

    private ArrayList<User> users;
    private UserJoinedArrayAdapter aJoined;
    private ListView lvJoined;
    private SwipeRefreshLayout swipeContainer;
    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_joined, container, false);
        lvJoined = (ListView) view.findViewById(R.id.lvJoined);
        users = new ArrayList<>();
        aJoined = new UserJoinedArrayAdapter(getActivity(), users);
        lvJoined.setAdapter(aJoined);

        lvJoined.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    User user = aJoined.getItem(position);
                    mListener.selectUser(user);
                }
            }
        });

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRequestToRefresh();
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            if (mListener != null) {
                mListener.onUsersJoinedFragmentAttached(this);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UsersJoinedFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onUsersJoinedFragmentAttached(UsersJoinedFragment usersJoinedFragment);
        public void selectUser(User user);
        public void onRequestToRefresh();
    }

    public void updateItems(ArrayList<User> users) {
        if (aJoined != null) {
            aJoined.clear();
            aJoined.addAll(users);
            lvJoined.smoothScrollToPosition(0);
        }
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }
}
