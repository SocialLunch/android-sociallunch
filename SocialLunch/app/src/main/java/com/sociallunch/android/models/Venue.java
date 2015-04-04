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
    private static final String RESPONSE_KEY_RATING_IMG_URL = "rating_img_url";
    private static final String RESPONSE_KEY_LOCATION = "location";
    private static final String RESPONSE_KEY_DISPLAY_ADDRESS = "display_address";
    private static final String RESPONSE_KEY_CATEGORIES = "categories";
    public String name;
    public String imageUrl;
    public Integer rating;
    public String ratingImgUrl;
    public String displayAddress;
    public String categories;

    // Returns a SearchResult given the expected JSON
    public static Venue fromJson(JSONObject jsonObject) {
        Venue venue = new Venue();
        try {
            // Deserialize json into object fields
            venue.name = jsonObject.has(RESPONSE_KEY_NAME) ? jsonObject.getString(RESPONSE_KEY_NAME) : "";
            venue.imageUrl = jsonObject.has(RESPONSE_KEY_IMAGE_URL) ? jsonObject.getString(RESPONSE_KEY_IMAGE_URL) : "";
            venue.rating = jsonObject.has(RESPONSE_KEY_RATING) ? jsonObject.getInt(RESPONSE_KEY_RATING) : null;
            venue.ratingImgUrl = jsonObject.has(RESPONSE_KEY_RATING_IMG_URL) ? jsonObject.getString(RESPONSE_KEY_RATING_IMG_URL) : "";
            if (jsonObject.has(RESPONSE_KEY_LOCATION)) {
                JSONObject jsonLocation = jsonObject.getJSONObject(RESPONSE_KEY_LOCATION);
                if (jsonLocation.has(RESPONSE_KEY_DISPLAY_ADDRESS)) {
                    JSONArray jsonDisplayAddress = jsonLocation.getJSONArray(RESPONSE_KEY_DISPLAY_ADDRESS);

                    int length = jsonDisplayAddress.length();
                    if (length > 0) {
                        String displayAddress = "";
                        for (int i = 0; i < length; i++) {
                            if (i != length - 1) {
                                if (i != 0) displayAddress += ", ";
                                displayAddress += jsonDisplayAddress.getString(i);
                            }
                        }
                        venue.displayAddress = displayAddress;
                    }
                }
            }
            if (jsonObject.has(RESPONSE_KEY_CATEGORIES)) {
                JSONArray jsonCategories = jsonObject.getJSONArray(RESPONSE_KEY_CATEGORIES);

                int length = jsonCategories.length();
                if (length > 0) {
                    String categories = "";
                    for (int i = 0; i < length; i++) {
                        if (i != length - 1) {
                            if (i != 0) categories += ", ";
                            JSONArray jsonCategory = jsonCategories.getJSONArray(i);
                            if (jsonCategory != null && jsonCategory.length() == 2) {
                                categories += jsonCategory.getString(0);
                            }
                        }
                    }
                    venue.categories = categories;
                }
            }
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
