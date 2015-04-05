package com.sociallunch.android.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    private String message;
    private String author;
    private String profileImageURL;
    private Long timestamp;

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

    public java.util.Map<String, String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @JsonIgnore
    public Long getTimestampLong() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
