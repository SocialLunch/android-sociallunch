package com.sociallunch.android.workers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.models.Suggestion;
import com.sociallunch.android.models.User;

import java.util.ArrayList;

/**
 * Created by admin on 4/5/15.
 */
public class JoinedWorkerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public ArrayList<User> mJoined = new ArrayList<>();

    public static JoinedWorkerFragment newInstance() {
        return new JoinedWorkerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        cancelAsyncTasks();
        super.onDestroy();
    }

    public void cancelAsyncTasks() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement JoinedWorkerFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onUpdatedJoinedUsers(ArrayList<User> users);
    }

    public void joinSuggestion(Suggestion suggestion) {
        OAuthApplication application = (OAuthApplication) getActivity().getApplication();
        Firebase newSuggestionRef = application.getFirebaseRef().child(Suggestion.FIREBASE_OBJECT_NAME).child("joined").push();
        User user = application.getCurrentUser();
        newSuggestionRef.setValue(user, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                fetchJoinedUsers();
            }
        });
    }

    public void fetchJoinedUsers() {
        OAuthApplication application = (OAuthApplication) getActivity().getApplication();
        Firebase suggestionsRef = application.getFirebaseRef().child(Suggestion.FIREBASE_OBJECT_NAME).child("joined");
        suggestionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<User> users = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                }
                mJoined = users;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("com.sociallunch.android", "The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
