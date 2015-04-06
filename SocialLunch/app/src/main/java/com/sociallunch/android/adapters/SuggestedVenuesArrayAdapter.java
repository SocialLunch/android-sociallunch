package com.sociallunch.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sociallunch.android.R;
import com.sociallunch.android.models.SuggestedVenue;
import com.sociallunch.android.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SuggestedVenuesArrayAdapter extends ArrayAdapter<SuggestedVenue> {
    // View lookup cache
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
        public ImageView ivRating;
        public TextView tvAddress;
        public TextView tvCategories;
        public TextView tvMeetingTime;
    }

    public SuggestedVenuesArrayAdapter(Context context, ArrayList<SuggestedVenue> suggestedVenues) {
        super(context, 0, suggestedVenues);
    }

    // Translates a particular `SearchResult` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SuggestedVenue suggestedVenue = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_suggestion, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.ivRating = (ImageView) convertView.findViewById(R.id.ivRating);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            viewHolder.tvCategories = (TextView) convertView.findViewById(R.id.tvCategories);
            viewHolder.tvMeetingTime = (TextView) convertView.findViewById(R.id.tvMeetingTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object
        viewHolder.tvName.setText(suggestedVenue.venue.name);
        Picasso.with(getContext()).load(Uri.parse(suggestedVenue.venue.imageUrl)).into(viewHolder.ivImage);
        Picasso.with(getContext()).load(Uri.parse(suggestedVenue.venue.ratingImgUrl)).into(viewHolder.ivRating);
        viewHolder.tvAddress.setText(suggestedVenue.venue.displayAddress);
        viewHolder.tvCategories.setText(suggestedVenue.venue.categories);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        String meetingTimes = "";
        for (Suggestion suggestion : suggestedVenue.suggestions) {
            if (!meetingTimes.isEmpty()) {
                meetingTimes += ", ";
            }
            meetingTimes += sdf.format(suggestion.meetingTime.getTime());
        }
        viewHolder.tvMeetingTime.setText(String.format(getContext().getString(R.string.create_suggestion_label_meeting_time), meetingTimes));
        // Return the completed view to render on screen
        return convertView;
    }
}
