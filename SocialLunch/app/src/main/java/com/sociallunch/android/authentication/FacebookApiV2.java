package com.sociallunch.android.authentication;

/**
 * Created by kelei on 3/22/15.
 */

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.utils.OAuthEncoder;
import org.scribe.utils.Preconditions;

public class FacebookApiV2 extends DefaultApi20
{
    private static final String AUTHORIZE_URL = "https://www.facebook.com/v2.0/dialog/oauth?client_id=%s&redirect_uri=%s";
    private static final String SCOPED_AUTHORIZE_URL = AUTHORIZE_URL + "&scope=%s";

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://graph.facebook.com/v2.0/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        Preconditions.checkValidUrl(config.getCallback(), "Must provide a valid url as callback. Facebook does not support OOB");

        // Append scope if present
        return String.format(SCOPED_AUTHORIZE_URL,
            config.getApiKey(), OAuthEncoder.encode(config.getCallback()),
            "user_friends,");
    }
}