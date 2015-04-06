package com.sociallunch.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SuggestedVenue implements Parcelable {
    public Venue venue;
    public ArrayList<Suggestion> suggestions;

    public SuggestedVenue() {
        suggestions = new ArrayList<>();
    }

    public SuggestedVenue(Venue venue) {
        this();
        this.venue = venue;
    }

    public SuggestedVenue(Venue venue, ArrayList<Suggestion> suggestions) {
        this.venue = venue;
        this.suggestions = suggestions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(venue, flags);
        out.writeTypedList(suggestions);
    }

    public static final Parcelable.Creator<SuggestedVenue> CREATOR
            = new Parcelable.Creator<SuggestedVenue>() {
        public SuggestedVenue createFromParcel(Parcel in) {
            return new SuggestedVenue(in);
        }
        public SuggestedVenue[] newArray(int size) {
            return new SuggestedVenue[size];
        }
    };

    public SuggestedVenue(Parcel in) {
        this.venue = in.readParcelable(Venue.class.getClassLoader());
        suggestions = new ArrayList<>();
        in.readTypedList(suggestions, Suggestion.CREATOR);
    }
}
