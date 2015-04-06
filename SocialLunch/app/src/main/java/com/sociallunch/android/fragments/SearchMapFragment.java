package com.sociallunch.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sociallunch.android.R;
import com.sociallunch.android.adapters.SuggestedVenueMarkerInfoWindowAdapter;
import com.sociallunch.android.models.SuggestedVenue;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchMapFragment extends MapFragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchMapFragment newInstance() {
        return new SearchMapFragment();
    }

    public SearchMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            if (mListener != null) {
                mListener.onSearchMapFragmentAttached(this);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SearchMapFragment.OnFragmentInteractionListener");
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
        public void onSearchMapFragmentAttached(SearchMapFragment searchMapFragment);
        public void onMapInSearchMapFragmentLoaded(SearchMapFragment searchMapFragment, GoogleMap googleMap);
        public void selectSuggestedvenue(SuggestedVenue suggestedVenue);
    }

    protected void loadMap(GoogleMap googleMap) {
        super.loadMap(googleMap);

        if (map != null && mListener != null) {
            mListener.onMapInSearchMapFragmentLoaded(this, map);
        }
    }

    public void updateItems(ArrayList<SuggestedVenue> suggestedVenues) {
        if (map != null) {
            map.clear();
            if (!suggestedVenues.isEmpty()) {

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                int length = suggestedVenues.size();
                final HashMap<Marker, SuggestedVenue> suggestedVenuesByMarker = new HashMap<>();
                for (int i = 0 ; i < length ; i++) {
                    SuggestedVenue suggestedVenue = suggestedVenues.get(i);
                    LatLng coordinate = new LatLng(suggestedVenue.venue.latitude, suggestedVenue.venue.longitude);
                    if (length == 1) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, getResources().getInteger(R.integer.lm_map_default_zoom_level)));
                    }
                    MarkerOptions markerOpts = new MarkerOptions()
                            .title(suggestedVenue.venue.name)
                            .position(coordinate)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_venue));
                    Marker marker = map.addMarker(markerOpts);
                    suggestedVenuesByMarker.put(marker, suggestedVenue);
                    builder.include(coordinate);
                }
                map.setInfoWindowAdapter(new SuggestedVenueMarkerInfoWindowAdapter(getActivity(), suggestedVenuesByMarker));
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if (marker != null) {
                            SuggestedVenue suggestedVenue = suggestedVenuesByMarker.get(marker);

                            if (suggestedVenue != null) {
                                if (mListener != null) {
                                    mListener.selectSuggestedvenue(suggestedVenue);
                                }
                            }
                        }
                    }
                });
                if (length > 1) {
                    LatLngBounds bounds = builder.build();
                    int padding = getResources().getInteger(R.integer.lm_map_bounds_padding);
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    map.moveCamera(cu);
                }
            }
        }
    }
}
