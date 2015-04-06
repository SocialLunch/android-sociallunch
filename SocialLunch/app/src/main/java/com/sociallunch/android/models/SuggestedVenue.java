package com.sociallunch.android.models;

import java.util.ArrayList;

public class SuggestedVenue {
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
}
