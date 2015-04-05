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
import com.sociallunch.android.models.Venue;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.sociallunch.android.workers.SearchWorkerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.sociallunch.android.workers.SearchWorkerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchWorkerFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    public ArrayList<Suggestion> mSuggestions = new ArrayList<>();
    public ArrayList<Suggestion> mFilteredSuggestions = new ArrayList<>();
    public String mQuery;

    public static SearchWorkerFragment newInstance() {
        return new SearchWorkerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
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
                    + " must implement SearchWorkerFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onUpdatedSuggestions(ArrayList<Suggestion> suggestions);
    }

    public void addSuggestion(Venue venue, Calendar meetingTime) {
        OAuthApplication application = (OAuthApplication) getActivity().getApplication();
        Firebase newSuggestionRef = application.getFirebaseRef().child(Suggestion.FIREBASE_OBJECT_NAME).push();

        Suggestion suggestion = new Suggestion(newSuggestionRef.getKey(), application.getCurrentUser().getUid(), venue, meetingTime);
        newSuggestionRef.setValue(suggestion, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                fetchSuggestions();
            }
        });
    }

    public void fetchSuggestions() {
        // Get a reference to suggestions
        OAuthApplication application = (OAuthApplication) getActivity().getApplication();
        Firebase suggestionsRef = application.getFirebaseRef().child(Suggestion.FIREBASE_OBJECT_NAME);
        // Attach an listener to read the data at our posts reference
        suggestionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Suggestion> suggestions = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Suggestion suggestion = child.getValue(Suggestion.class);
                    suggestions.add(suggestion);
                }
                mSuggestions = suggestions;
                filterSuggestions(mQuery);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("com.sociallunch.android", "The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void filterSuggestions(String query) {
        mQuery = query;

        if (query == null || query.isEmpty()) {
            mFilteredSuggestions = mSuggestions;
        } else {
            ArrayList<Suggestion> filteredSuggestions = new ArrayList<>();
            String queryInLowerCase = query.toLowerCase();
            for (Suggestion suggestion : mSuggestions) {
                if (suggestion.venue.name.toLowerCase().contains(queryInLowerCase) ||
                        (suggestion.venue.categories != null && !suggestion.venue.categories.isEmpty() && suggestion.venue.categories.toLowerCase().contains(queryInLowerCase))) {
                    filteredSuggestions.add(suggestion);
                }
            }
            mFilteredSuggestions = filteredSuggestions;
        }

        if (mListener != null) {
            mListener.onUpdatedSuggestions(mFilteredSuggestions);
        }
    }
}
