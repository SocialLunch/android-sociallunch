package com.sociallunch.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YelpSearchAPIResponse {
    public final static String PARAM_KEY_TOTAL = "total";
    public final static String PARAM_KEY_BUSINESSES = "businesses";
    public int offset;
    public int limit;
    public int total;
    public ArrayList<Venue> venues;

    public YelpSearchAPIResponse() {
        venues = new ArrayList<>();
    }

    public YelpSearchAPIResponse(String response, int offset, int limit) {
        this();

        this.offset = offset;
        this.limit = limit;

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has(PARAM_KEY_TOTAL)) {
                total = jsonObject.getInt(PARAM_KEY_TOTAL);
            }
            if (jsonObject.has(PARAM_KEY_BUSINESSES)) {
                JSONArray jsonArrayBusinesses = jsonObject.getJSONArray(PARAM_KEY_BUSINESSES);
                venues = Venue.fromJson(jsonArrayBusinesses);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
