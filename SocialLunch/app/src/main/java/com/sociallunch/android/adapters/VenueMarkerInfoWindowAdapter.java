package com.sociallunch.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sociallunch.android.R;
import com.sociallunch.android.models.Venue;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class VenueMarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private HashMap<Marker, Venue> venuesByMarker;

    public VenueMarkerInfoWindowAdapter(Context context, HashMap<Marker, Venue> venuesByMarker) {
        this.context = context;
        this.venuesByMarker = venuesByMarker;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.item_venue, null);

        if (marker != null) {
            Venue venue = venuesByMarker.get(marker);

            ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            ImageView ivRating = (ImageView) view.findViewById(R.id.ivRating);
            TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            TextView tvCategories = (TextView) view.findViewById(R.id.tvCategories);

            tvName.setText(venue.name);
            Picasso.with(context).load(Uri.parse(venue.imageUrl)).into(ivImage);
            Picasso.with(context).load(Uri.parse(venue.ratingImgUrl)).into(ivRating);
            tvAddress.setText(venue.displayAddress);
            tvCategories.setText(venue.categories);
        }

        return view;
    }
}
