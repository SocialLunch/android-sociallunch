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
import com.sociallunch.android.models.SuggestedVenue;
import com.sociallunch.android.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class SuggestedVenueMarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private HashMap<Marker, SuggestedVenue> suggestedVenuesByMarker;

    public SuggestedVenueMarkerInfoWindowAdapter(Context context, HashMap<Marker, SuggestedVenue> suggestedVenuesByMarker) {
        this.context = context;
        this.suggestedVenuesByMarker = suggestedVenuesByMarker;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.item_suggested_venue, null);

        if (marker != null) {
            SuggestedVenue suggestedVenue = suggestedVenuesByMarker.get(marker);

            ImageView ivImage = (ImageView) view.findViewById(R.id.ivImage);
            TextView tvName = (TextView) view.findViewById(R.id.tvName);
            ImageView ivRating = (ImageView) view.findViewById(R.id.ivRating);
            TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            TextView tvCategories = (TextView) view.findViewById(R.id.tvCategories);
            TextView tvMeetingTime = (TextView) view.findViewById(R.id.tvMeetingTime);

            tvName.setText(suggestedVenue.venue.name);
            Picasso.with(context).load(Uri.parse(suggestedVenue.venue.imageUrl)).into(ivImage);
            Picasso.with(context).load(Uri.parse(suggestedVenue.venue.ratingImgUrl)).into(ivRating);
            tvAddress.setText(suggestedVenue.venue.displayAddress);
            tvCategories.setText(suggestedVenue.venue.categories);
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
            String meetingTimes = "";
            for (Suggestion suggestion : suggestedVenue.suggestions) {
                if (!meetingTimes.isEmpty()) {
                    meetingTimes += ", ";
                }
                meetingTimes += sdf.format(suggestion.meetingTime.getTime());
            }
            tvMeetingTime.setText(String.format(context.getString(R.string.create_suggestion_label_meeting_time), meetingTimes));
        }

        return view;
    }
}
