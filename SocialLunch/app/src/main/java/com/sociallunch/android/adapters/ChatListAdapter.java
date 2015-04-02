package com.sociallunch.android.adapters;

import android.app.Activity;
import android.graphics.Color;
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
        TextView authorText = (TextView) view.findViewById(R.id.author);
        ImageView ivAuthorImage = (ImageView) view.findViewById(R.id.ivAuthorImage);
        authorText.setText(author + ": ");
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.BLUE);
        }
        if (imageURL != null) {
            Picasso.with(view.getContext()).load(imageURL).transform(new CircleTransform()).into(ivAuthorImage);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    }
}
