package com.sociallunch.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sociallunch.android.R;
import com.sociallunch.android.adapters.SuggestionsArrayAdapter;
import com.sociallunch.android.models.SuggestedVenue;
import com.sociallunch.android.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestedVenueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestedVenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestedVenueFragment extends MapFragment {
    private static final String ARG_SUGGESTED_VENUE = "arg.SUGGESTED_VENUE";

    private SuggestedVenue suggestedVenue;
    public ImageView ivImage;
    public TextView tvName;
    public ImageView ivRating;
    public TextView tvAddress;
    public TextView tvCategories;

    private SuggestionsArrayAdapter aSuggestions;
    private ListView lvSuggestions;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SuggestedVenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestedVenueFragment newInstance(SuggestedVenue suggestedVenue) {
        SuggestedVenueFragment fragment = new SuggestedVenueFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SUGGESTED_VENUE, suggestedVenue);
        fragment.setArguments(args);
        return fragment;
    }

    public SuggestedVenueFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            suggestedVenue = getArguments().getParcelable(ARG_SUGGESTED_VENUE);
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_suggested_venue;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        tvName = (TextView) view.findViewById(R.id.tvName);
        ivRating = (ImageView) view.findViewById(R.id.ivRating);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvCategories = (TextView) view.findViewById(R.id.tvCategories);

        // Find the listview
        lvSuggestions = (ListView) view.findViewById(R.id.lvSuggestions);
        // Create the arraylist (data source)
        aSuggestions = new SuggestionsArrayAdapter(getActivity(), suggestedVenue.suggestions);
        lvSuggestions.setAdapter(aSuggestions);

        lvSuggestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    Suggestion suggestion = aSuggestions.getItem(position);
                    mListener.selectSuggestion(suggestion);
                }
            }
        });

        return view;
    }

    public void updateViews() {
        tvName.setText(suggestedVenue.venue.name);
        Picasso.with(getActivity()).load(Uri.parse(suggestedVenue.venue.imageUrl)).into(ivImage);
        Picasso.with(getActivity()).load(Uri.parse(suggestedVenue.venue.ratingImgUrl)).into(ivRating);
        tvAddress.setText(suggestedVenue.venue.displayAddress);
        tvCategories.setText(suggestedVenue.venue.categories);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
    }

    @Override
    protected void loadMap(GoogleMap googleMap) {
        super.loadMap(googleMap);

        if (map != null) {
            LatLng coordinate = new LatLng(suggestedVenue.venue.latitude, suggestedVenue.venue.longitude);
            MarkerOptions markerOpts = new MarkerOptions()
                    .title(suggestedVenue.venue.name)
                    .snippet(suggestedVenue.venue.displayAddress)
                    .position(coordinate)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_venue));
            map.addMarker(markerOpts);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, getResources().getInteger(R.integer.lm_map_default_zoom_level)));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SuggestedVenueFragment.OnFragmentInteractionListener");
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
        public void selectSuggestion(Suggestion suggestion);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateViews();
    }
}
