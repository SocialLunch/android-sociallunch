package com.sociallunch.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.sociallunch.android.adapters.SuggestionsArrayAdapter;
import com.sociallunch.android.adapters.VenuesArrayAdapter;
import com.sociallunch.android.fragments.dummy.DummyContent;
import com.sociallunch.android.models.Suggestion;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchListFragment extends ListFragment {
    private ArrayList<Suggestion> suggestions;
    private SuggestionsArrayAdapter aSuggestions;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchListFragment newInstance() {
        return new SearchListFragment();
    }

    public SearchListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the arraylist (data source)
        suggestions = new ArrayList<>();
        // Construct the adapter from data source
        aSuggestions = new SuggestionsArrayAdapter(getActivity(), suggestions);
        setListAdapter(aSuggestions);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SearchListFragment.OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
    }


    public void updateItems(ArrayList<Suggestion> suggestions) {
        if (aSuggestions != null) {
            aSuggestions.clear();
            aSuggestions.addAll(suggestions);
        }
    }
}
