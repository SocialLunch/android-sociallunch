package com.sociallunch.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YelpSearchAPIResponse {
    public final static String PARAM_KEY_BUSINESSES = "businesses";
    public ArrayList<Venue> venues;

    public YelpSearchAPIResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArrayBusinesses = jsonObject.getJSONArray(PARAM_KEY_BUSINESSES);
            venues = Venue.fromJson(jsonArrayBusinesses);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
