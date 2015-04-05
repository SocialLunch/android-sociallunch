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
import com.sociallunch.android.models.Venue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VenuesArrayAdapter extends ArrayAdapter<Venue> {
    // View lookup cache
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
        public ImageView ivRating;
        public TextView tvAddress;
        public TextView tvCategories;
    }

    public VenuesArrayAdapter(Context context, ArrayList<Venue> venues) {
        super(context, 0, venues);
    }

    // Translates a particular `SearchResult` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Venue venue = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_venue, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.ivRating = (ImageView) convertView.findViewById(R.id.ivRating);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            viewHolder.tvCategories = (TextView) convertView.findViewById(R.id.tvCategories);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object
        viewHolder.tvName.setText(venue.name);
        Picasso.with(getContext()).load(Uri.parse(venue.imageUrl)).into(viewHolder.ivImage);
        Picasso.with(getContext()).load(Uri.parse(venue.ratingImgUrl)).into(viewHolder.ivRating);
        viewHolder.tvAddress.setText(venue.displayAddress);
        viewHolder.tvCategories.setText(venue.categories);
        // Return the completed view to render on screen
        return convertView;
    }
}
