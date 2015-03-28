package com.sociallunch.android.models;

import com.firebase.client.AuthData;


import java.util.Map;

/**
 * Created by kelei on 3/22/15.
 */
public class User {
    private String uid;
    private String fullName;
    private String email;
    private String foodsLiked;
    private String foodsDisliked;

    public User() {
    }

    public static User fromAuthData(AuthData authData) {
        Map<String, Object> providerData = authData.getProviderData();
        User user = new User();
        user.uid = authData.getUid();
        Map<String, Object> cachedUserProfile = (Map<String, Object>) providerData.get("cachedUserProfile");
        user.fullName = (String) cachedUserProfile.get("name");
        user.email = (String) cachedUserProfile.get("email");
        return user;
    }

    public String getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getFoodsLiked() {
        return foodsLiked;
    }

    public String getFoodsDisliked() {
        return foodsDisliked;
    }

    public void setFoodsLiked(String foodsLiked) {
        this.foodsLiked = foodsLiked;
    }

    public void setFoodsDisliked(String foodsDisliked) {
        this.foodsDisliked = foodsDisliked;
    }
}
