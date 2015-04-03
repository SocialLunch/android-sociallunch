package com.sociallunch.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Venue implements Serializable {
    private static final String RESPONSE_KEY_NAME = "name";
    private static final String RESPONSE_KEY_IMAGE_URL = "image_url";
    private static final String RESPONSE_KEY_RATING = "rating";
    public String name;
    public String imageUrl;
    public Integer rating;

    // Returns a SearchResult given the expected JSON
    public static Venue fromJson(JSONObject jsonObject) {
        Venue venue = new Venue();
        try {
            // Deserialize json into object fields
            venue.name = jsonObject.has(RESPONSE_KEY_NAME) ? jsonObject.getString(RESPONSE_KEY_NAME) : "";
            venue.imageUrl = jsonObject.has(RESPONSE_KEY_IMAGE_URL) ? jsonObject.getString(RESPONSE_KEY_IMAGE_URL) : "";
            venue.rating = jsonObject.has(RESPONSE_KEY_RATING) ? jsonObject.getInt(RESPONSE_KEY_RATING) : null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return venue;
    }

    // Decodes array of search result json results into business model objects
    public static ArrayList<Venue> fromJson(JSONArray jsonArray) {
        ArrayList<Venue> venues = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject searchResultJson;
            try {
                searchResultJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Venue venue = Venue.fromJson(searchResultJson);
            if (venue != null) {
                venues.add(venue);
            }
        }
        return venues;
    }
}
