package com.sociallunch.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sociallunch.android.R;
import com.sociallunch.android.models.Suggestion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SuggestionsArrayAdapter extends ArrayAdapter<Suggestion> {
    // View lookup cache
    private static class ViewHolder {
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
            viewHolder.tvMeetingTime = (TextView) convertView.findViewById(R.id.tvMeetingTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        viewHolder.tvMeetingTime.setText(sdf.format(suggestion.meetingTime.getTime()));
        // Return the completed view to render on screen
        return convertView;
    }
}
