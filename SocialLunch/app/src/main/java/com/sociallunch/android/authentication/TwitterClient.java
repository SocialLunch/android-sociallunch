package com.sociallunch.android.authentication;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/**
 * Created by kelei on 3/22/15.
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "39JccLaJEycEIn5gGp7vWmOM3";       // Change this
    public static final String REST_CONSUMER_SECRET = "8Ign3thZ7zfrjKbqVcOMy8ksUDFVrN4BgUDLxmbySi2K4mm5r6"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://simplelunch"; // Change this (here and in manifest)

    public static final int PAGE_SIZE = 10;

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
}
