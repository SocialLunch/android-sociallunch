package com.sociallunch.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.models.Suggestion;
import com.sociallunch.android.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class SuggestionActivity extends ActionBarActivity {
    public static final String EXTRA_SUGGESTION = "extra.BOOKING";
    private Suggestion suggestion;
    public ImageView ivImage;
    public TextView tvName;
    public ImageView ivRating;
    public TextView tvAddress;
    public TextView tvCategories;
    public TextView tvMeetingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        if (getIntent() != null) {
            suggestion = getIntent().getParcelableExtra(EXTRA_SUGGESTION);
        }

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvName = (TextView) findViewById(R.id.tvName);
        ivRating = (ImageView) findViewById(R.id.ivRating);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvCategories = (TextView) findViewById(R.id.tvCategories);
        tvMeetingTime = (TextView) findViewById(R.id.tvMeetingTime);

        updateViews();
    }

    public void updateViews() {
        tvName.setText(suggestion.venue.name);
        Picasso.with(this).load(Uri.parse(suggestion.venue.imageUrl)).into(ivImage);
        Picasso.with(this).load(Uri.parse(suggestion.venue.ratingImgUrl)).into(ivRating);
        tvAddress.setText(suggestion.venue.displayAddress);
        tvCategories.setText(suggestion.venue.categories);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm aa");
        tvMeetingTime.setText(String.format(getString(R.string.create_suggestion_label_meeting_time), sdf.format(suggestion.meetingTime.getTime())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggestion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_chat:
                launchChat();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void launchChat() {
        Intent i = new Intent(this, ChatActivity.class);
        OAuthApplication application = (OAuthApplication) getApplication();
//        User user = application.getCurrentUser();
        User user = new User();

        // TODO: Remove once user is populated
        user.setFullName("Jonny Appleseed");
        user.setProfileImage("https://media.licdn.com/media/p/4/000/15b/027/1a18058.jpg");
        // End TODO

        i.putExtra("user", user);
//        i.putExtra("identifier", suggestion.id);
        i.putExtra("identifier", "1234");
        startActivity(i);
    }
}
