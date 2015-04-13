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
import com.sociallunch.android.adapters.SuggestedVenuesArrayAdapter;
import com.sociallunch.android.models.SuggestedVenue;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchListFragment extends Fragment {
    private ArrayList<SuggestedVenue> suggestedVenues;
    private SuggestedVenuesArrayAdapter aSuggestedVenues;
    private ListView lvSuggestions;
    private SwipeRefreshLayout swipeContainer;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        // Find the listview
        lvSuggestions = (ListView) view.findViewById(R.id.lvSuggestions);
        // Create the arraylist (data source)
        suggestedVenues = new ArrayList<>();
        aSuggestedVenues = new SuggestedVenuesArrayAdapter(getActivity(), suggestedVenues);
        lvSuggestions.setAdapter(aSuggestedVenues);

        lvSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    SuggestedVenue suggestedVenue = (SuggestedVenue) aSuggestedVenues.getItem(position);
                    View selectedView = getViewByPosition(position,lvSuggestions);
                    mListener.selectSuggestedvenue(suggestedVenue,selectedView);
                }
            }
        });

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRequestToRefresh();
                }
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            if (mListener != null) {
                mListener.onSearchListFragmentAttached(this);
            }
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
        public void onSearchListFragmentAttached(SearchListFragment searchListFragment);
        public void selectSuggestedvenue(SuggestedVenue suggestedVenue, View view);
        public void onRequestToRefresh();
    }

    public void updateItems(ArrayList<SuggestedVenue> suggestedVenues) {
        if (aSuggestedVenues != null) {
            aSuggestedVenues.clear();
            aSuggestedVenues.addAll(suggestedVenues);
            lvSuggestions.smoothScrollToPosition(0);
        }
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }
}
