package com.sociallunch.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Venue implements Parcelable {
    private static final String RESPONSE_KEY_ID = "id";
    private static final String RESPONSE_KEY_NAME = "name";
    private static final String RESPONSE_KEY_IMAGE_URL = "image_url";
    private static final String RESPONSE_KEY_RATING = "rating";
    private static final String RESPONSE_KEY_RATING_IMG_URL = "rating_img_url";
    private static final String RESPONSE_KEY_LOCATION = "location";
    private static final String RESPONSE_KEY_DISPLAY_ADDRESS = "display_address";
    private static final String RESPONSE_KEY_COORDINATE = "coordinate";
    private static final String RESPONSE_KEY_LATITUDE = "latitude";
    private static final String RESPONSE_KEY_LONGITUDE = "longitude";
    private static final String RESPONSE_KEY_CATEGORIES = "categories";
    public String yelpId;
    public String name;
    public String imageUrl;
    public Integer rating;
    public String ratingImgUrl;
    public double latitude;
    public double longitude;
    public String displayAddress;
    public String categories;

    public Venue() {
    }

    // Returns a SearchResult given the expected JSON
    public static Venue fromJson(JSONObject jsonObject) {
        Venue venue = new Venue();
        try {
            // Deserialize json into object fields
            venue.yelpId = jsonObject.has(RESPONSE_KEY_ID) ? jsonObject.getString(RESPONSE_KEY_ID) : "";
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
                if (jsonLocation.has(RESPONSE_KEY_COORDINATE)) {
                    JSONObject jsonCoordinate = jsonLocation.getJSONObject(RESPONSE_KEY_COORDINATE);
                    venue.latitude = jsonCoordinate.getDouble(RESPONSE_KEY_LATITUDE);
                    venue.longitude = jsonCoordinate.getDouble(RESPONSE_KEY_LONGITUDE);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(yelpId);
        out.writeString(name);
        out.writeString(imageUrl);
        out.writeInt(rating);
        out.writeString(ratingImgUrl);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeString(displayAddress);
        out.writeString(categories);
    }

    public static final Parcelable.Creator<Venue> CREATOR
            = new Parcelable.Creator<Venue>() {
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

    public Venue(Parcel in) {
        yelpId = in.readString();
        name = in.readString();
        imageUrl = in.readString();
        rating = in.readInt();
        ratingImgUrl = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        displayAddress = in.readString();
        categories = in.readString();

    }
}
