package com.sociallunch.android.models;

/**
 * Created by kelei on 3/22/15.
 */
public class User {
    private String uid;
    private String foodsLiked;
    private String foodsDisliked;

    public void setFoodsLiked(String foodsLiked) {
        this.foodsLiked = foodsLiked;
    }

    public void setFoodsDisliked(String foodsDisliked) {
        this.foodsDisliked = foodsDisliked;
    }
}
