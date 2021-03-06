package com.sociallunch.android.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sociallunch.android.R;
import com.sociallunch.android.application.OAuthApplication;
import com.sociallunch.android.models.User;
import com.sociallunch.android.support.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserJoinedArrayAdapter extends ArrayAdapter<User> {

    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
    }

    public UserJoinedArrayAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        User ourUser = ((OAuthApplication) getContext().getApplicationContext()).getCurrentUser();
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_joined_user, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvUserName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(user.getFullName());
        Picasso.with(getContext()).load(Uri.parse(user.getProfileImage())).transform(new CircleTransform()).into(viewHolder.ivImage);

        if (!user.getUid().equalsIgnoreCase(ourUser.getUid())) {
            Toast.makeText(getContext(), user.getFullName() + "Joined Your Suggestion", Toast.LENGTH_SHORT).show();
        }

        return convertView;
    }
}
