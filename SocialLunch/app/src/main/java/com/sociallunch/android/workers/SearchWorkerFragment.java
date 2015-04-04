package com.sociallunch.android.workers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sociallunch.android.models.Suggestion;

import java.util.ArrayList;

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
    public ArrayList<Suggestion> mSearchResults = new ArrayList<>();

    public static SearchWorkerFragment newInstance() {
        return new SearchWorkerFragment();
    }

    public SearchWorkerFragment() {
        mSearchResults = new ArrayList<>();
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
    }
}
