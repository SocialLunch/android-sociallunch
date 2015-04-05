package com.sociallunch.android.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;
import com.sociallunch.android.R;
import com.sociallunch.android.models.Chat;
import com.sociallunch.android.support.CircleTransform;
import com.squareup.picasso.Picasso;

public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, Chat.class, layout, activity);
        this.mUsername = mUsername;
    }

    @Override
    protected void populateView(View view, Chat chat) {
        String author = chat.getAuthor();
        String imageURL = chat.getProfileImageURL();
        TextView tvAuthor = (TextView) view.findViewById(R.id.author);
        TextView tvTime = (TextView) view.findViewById(R.id.time);
        ImageView ivAuthorImage = (ImageView) view.findViewById(R.id.ivAuthorImage);
        tvAuthor.setText(author + ": ");
        if (author != null && author.equals(mUsername)) {
            tvAuthor.setTextColor(Color.BLUE);
        }
        Long t = chat.getTimestampLong();
        Time time = new Time();
        time.set(Long.valueOf(t));
        tvTime.setText(time.format("%I:%M %p"));
        if (imageURL != null) {
            Picasso.with(view.getContext()).load(imageURL).transform(new CircleTransform()).into(ivAuthorImage);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    }
}
