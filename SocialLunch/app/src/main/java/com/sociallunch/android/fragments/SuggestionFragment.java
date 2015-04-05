package com.sociallunch.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sociallunch.android.R;
import com.sociallunch.android.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends MapFragment {
    private static final String ARG_SUGGESTION = "arg.SUGGESTION";

    private Suggestion suggestion;
    public ImageView ivImage;
    public TextView tvName;
    public ImageView ivRating;
    public TextView tvAddress;
    public TextView tvCategories;
    public TextView tvMeetingTime;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SuggestionFragment.
     */
    public static SuggestionFragment newInstance(Suggestion suggestion) {
        SuggestionFragment fragment = new SuggestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SUGGESTION, suggestion);
        fragment.setArguments(args);
        return fragment;
    }

    public SuggestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            suggestion = getArguments().getParcelable(ARG_SUGGESTION);
        }
    }

    public int getLayoutResourceId() {
        return R.layout.fragment_suggestion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ivImage = (ImageView) view.findViewById(R.id.ivImage);
        tvName = (TextView) view.findViewById(R.id.tvName);
        ivRating = (ImageView) view.findViewById(R.id.ivRating);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvCategories = (TextView) view.findViewById(R.id.tvCategories);
        tvMeetingTime = (TextView) view.findViewById(R.id.tvMeetingTime);
        return view;
    }

    public void updateViews() {
        tvName.setText(suggestion.venue.name);
        Picasso.with(getActivity()).load(Uri.parse(suggestion.venue.imageUrl)).into(ivImage);
        Picasso.with(getActivity()).load(Uri.parse(suggestion.venue.ratingImgUrl)).into(ivRating);
        tvAddress.setText(suggestion.venue.displayAddress);
        tvCategories.setText(suggestion.venue.categories);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        tvMeetingTime.setText(String.format(getString(R.string.create_suggestion_label_meeting_time), sdf.format(suggestion.meetingTime.getTime())));
    }

    @Override
    protected void loadMap(GoogleMap googleMap) {
        super.loadMap(googleMap);

        if (map != null) {
            LatLng coordinate = new LatLng(suggestion.venue.latitude, suggestion.venue.longitude);
            MarkerOptions markerOpts = new MarkerOptions()
                    .title(suggestion.venue.name)
                    .snippet(suggestion.venue.displayAddress)
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
                    + " must implement SuggestionFragment.OnFragmentInteractionListener");
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

    @Override
    public void onResume() {
        super.onResume();

        updateViews();
    }
}
