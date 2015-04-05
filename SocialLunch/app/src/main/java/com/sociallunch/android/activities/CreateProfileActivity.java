package com.sociallunch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.models.User;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CreateProfileActivity extends ActionBarActivity {
    @InjectView(R.id.mCardContainer) CardContainer mCardContainer;
    SimpleCardStackAdapter adapter;
    private ArrayList<String> foodsLiked;
    private ArrayList<String> foodsDisliked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        ButterKnife.inject(this);

        adapter = new SimpleCardStackAdapter(this);
        adapter.add(new CardModel("Korean Food", null,
            getResources().getDrawable(R.drawable.korean_food)));
        adapter.add(new CardModel("Chinese Food", null,
            getResources().getDrawable(R.drawable.chinese_food)));
        adapter.add(new CardModel("Italian Food", null,
            getResources().getDrawable(R.drawable.italian_food)));
        adapter.add(new CardModel("Greek Food", null,
            getResources().getDrawable(R.drawable.greek_food)));
        adapter.add(new CardModel("French Food", null,
            getResources().getDrawable(R.drawable.french_food)));
        adapter.add(new CardModel("Vietnamese Food", null,
            getResources().getDrawable(R.drawable.vietnamese_food)));
        adapter.add(new CardModel("Japanese Food", null,
            getResources().getDrawable(R.drawable.japanese_food)));
        adapter.add(new CardModel("Mexican Food", null,
            getResources().getDrawable(R.drawable.mexican_food)));
        adapter.add(new CardModel("Middle Eastern Food", null,
            getResources().getDrawable(R.drawable.middle_eastern_food)));

        foodsLiked = new ArrayList<>();
        foodsDisliked = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            final CardModel card = adapter.getCardModel(i);
            card.setCardDislikeImageDrawable(null);
            card.setCardLikeImageDrawable(null);
            card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    foodsLiked.add(card.getTitle());

                    if (foodsLiked.size() + foodsDisliked.size() == adapter.getCount()) {
                        saveAndNavigateToHomepage();
                    }
                }

                @Override
                public void onDislike() {
                    foodsDisliked.add(card.getTitle());

                    if (foodsLiked.size() + foodsDisliked.size() == adapter.getCount()) {
                        saveAndNavigateToHomepage();
                    }
                }
            });
        }

        mCardContainer.setAdapter(adapter);
    }

    private void saveAndNavigateToHomepage() {
        OAuthApplication application = (OAuthApplication) getApplication();
        AuthData authData = application.getAuthData();
        if (authData == null) {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
            return;
        }

        Firebase ref = application.getFirebaseRef().child("user");
        User user = User.fromAuthData(authData);
        user.setFoodsLiked(TextUtils.join(",", foodsLiked));
        user.setFoodsDisliked(TextUtils.join(",", foodsDisliked));
        application.setCurrentUser(user);
        ref.child(authData.getUid()).setValue(user);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_profile, menu);
        ButterKnife.inject(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
