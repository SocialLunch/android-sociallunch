package com.sociallunch.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.sociallunch.android.R;
import com.sociallunch.android.adapters.VenuesArrayAdapter;
import com.sociallunch.android.models.Venue;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VenueSelectionListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VenueSelectionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueSelectionListFragment extends ListFragment {
    private ArrayList<Venue> venues;
    private VenuesArrayAdapter aVenues;
    private View listHeader;
    private View listFooter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VenueSelectionListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenueSelectionListFragment newInstance() {
        return new VenueSelectionListFragment();
    }

    public VenueSelectionListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the arraylist (data source)
        venues = new ArrayList<>();
        // Construct the adapter from data source
        aVenues = new VenuesArrayAdapter(getActivity(), venues);
        setListAdapter(aVenues);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        listHeader = inflater.inflate(R.layout.list_header_venue, null);
        listFooter = inflater.inflate(R.layout.list_footer_venue, null);

        Button buttonPrevious = (Button) listHeader.findViewById(R.id.buttonPrevious);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.requestToFetchPreviousResults();
                }
            }
        });

        Button buttonNext = (Button) listFooter.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.requestToFetchNextResults();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEmptyText(getString(R.string.label_empty));
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mListener != null) {
            Venue venue = (Venue) getListAdapter().getItem(position);
            mListener.selectVenue(venue);
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement VenueSelectionListFragment.OnFragmentInteractionListener");
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
        public void selectVenue(Venue venue);
        public void requestToFetchNextResults();
        public void requestToFetchPreviousResults();
    }

    public void updateItems(ArrayList<Venue> venues, boolean hasPrevious, boolean hasNext) {
        if (aVenues != null) {
            aVenues.clear();
            aVenues.addAll(venues);
        }
        ListView lv = getListView();
        if (hasPrevious) {
            if (lv.getHeaderViewsCount() == 0) {
                lv.addHeaderView(listHeader, null, false);
            }
        } else {
            if (lv.getHeaderViewsCount() > 0) {
                lv.removeHeaderView(listHeader);
            }
        }
        if (hasNext) {
            if (lv.getFooterViewsCount() == 0) {
                lv.addFooterView(listFooter, null, false);
            }
        } else {
            if (lv.getFooterViewsCount() > 0) {
                lv.removeHeaderView(listFooter);
            }
        }
        lv.smoothScrollToPosition(0);
    }
}
