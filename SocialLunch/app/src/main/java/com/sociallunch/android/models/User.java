package com.sociallunch.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.AuthData;


import java.util.Map;

/**
 * Created by kelei on 3/22/15.
 */
public class User implements Parcelable {
    private String uid;
    private String fullName;
    private String email;
    private String foodsLiked;
    private String foodsDisliked;
    private String profileImage;

    public User() {
    }

    public static User fromAuthData(AuthData authData) {
        Map<String, Object> providerData = authData.getProviderData();
        User user = new User();
        user.uid = authData.getUid();
        Map<String, Object> cachedUserProfile = (Map<String, Object>) providerData.get("cachedUserProfile");
        user.fullName = (String) cachedUserProfile.get("name");
        user.email = (String) cachedUserProfile.get("email");
        user.profileImage = (String) cachedUserProfile.get("cover");
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

    public String getProfileImage() { return profileImage; }

    public void setFoodsLiked(String foodsLiked) {
        this.foodsLiked = foodsLiked;
    }

    public void setFoodsDisliked(String foodsDisliked) {
        this.foodsDisliked = foodsDisliked;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(fullName);
        out.writeString(uid);
        out.writeString(email);
        out.writeString(foodsLiked);
        out.writeString(foodsDisliked);
        out.writeString(profileImage);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        this.fullName = in.readString();
        this.uid = in.readString();
        this.email = in.readString();
        this.foodsLiked = in.readString();
        this.foodsDisliked = in.readString();
        this.profileImage = in.readString();
    }
}
