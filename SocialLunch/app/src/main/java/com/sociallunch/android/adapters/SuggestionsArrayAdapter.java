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
import com.sociallunch.android.models.Suggestion;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SuggestionsArrayAdapter extends ArrayAdapter<Suggestion> {
    // View lookup cache
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
        public ImageView ivRating;
        public TextView tvAddress;
        public TextView tvCategories;
        public TextView tvMeetingTime;
    }

    public SuggestionsArrayAdapter(Context context, ArrayList<Suggestion> suggestions) {
        super(context, 0, suggestions);
    }

    // Translates a particular `SearchResult` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Suggestion suggestion = getItem(position);
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
        viewHolder.tvName.setText(String.format(getContext().getString(R.string.item_venue_label_name), position + 1, suggestion.venue.name));
        Picasso.with(getContext()).load(Uri.parse(suggestion.venue.imageUrl)).into(viewHolder.ivImage);
        Picasso.with(getContext()).load(Uri.parse(suggestion.venue.ratingImgUrl)).into(viewHolder.ivRating);
        viewHolder.tvAddress.setText(suggestion.venue.displayAddress);
        viewHolder.tvCategories.setText(suggestion.venue.categories);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        viewHolder.tvMeetingTime.setText(String.format(getContext().getString(R.string.create_suggestion_label_meeting_time), sdf.format(suggestion.meetingTime.getTime())));
        // Return the completed view to render on screen
        return convertView;
    }
}
