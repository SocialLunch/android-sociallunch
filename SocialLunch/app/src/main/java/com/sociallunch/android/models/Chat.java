package com.sociallunch.android.models;

public class Chat {

    private String message;
    private String author;

    private Chat() {
    }

    public Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
