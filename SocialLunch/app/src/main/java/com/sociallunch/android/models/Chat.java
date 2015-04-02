package com.sociallunch.android.models;

public class Chat {

    private String message;
    private String author;
    private String profileImageURL;

    private Chat() {
    }

    public Chat(String message, String author, String profileImageURL) {
        this.message = message;
        this.author = author;
        this.profileImageURL = profileImageURL;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getProfileImageURL() { return profileImageURL; }
}
