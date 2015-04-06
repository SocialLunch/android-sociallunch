package com.sociallunch.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Map;

public class Suggestion implements Parcelable {
    public static String FIREBASE_OBJECT_NAME = "suggestion";
    public String id;
    public Venue venue;
    public Calendar meetingTime;
    public String userId;
    public Map joined;

    public Suggestion() {
    }

    public Suggestion(String id, String userId, Venue venue, Calendar meetingTime) {
        this();
        this.id = id;
        this.userId = userId;
        this.venue = venue;
        this.meetingTime = meetingTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(userId);
        out.writeLong(meetingTime.getTimeInMillis());
        out.writeParcelable(venue, flags);
    }

    public static final Parcelable.Creator<Suggestion> CREATOR
            = new Parcelable.Creator<Suggestion>() {
        public Suggestion createFromParcel(Parcel in) {
            return new Suggestion(in);
        }
        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };

    public Suggestion(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        Calendar meetingTime = Calendar.getInstance();
        meetingTime.setTimeInMillis(in.readLong());
        this.meetingTime = meetingTime;
        this.venue = in.readParcelable(Venue.class.getClassLoader());
    }
}
