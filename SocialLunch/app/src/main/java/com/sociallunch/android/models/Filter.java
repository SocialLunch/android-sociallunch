package com.sociallunch.android.models;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by kelei on 4/5/15.
 */
public class Filter implements Serializable {
    private Calendar earliestMeetingTime;
    private Calendar latestMeetingTime;

    public Calendar getEarliestMeetingTime() {
        return earliestMeetingTime;
    }

    public Calendar getLatestMeetingTime() {
        return latestMeetingTime;
    }

    public void setEarliestMeetingTime(Calendar earliestMeetingTime) {
        this.earliestMeetingTime = earliestMeetingTime;
    }

    public void setLatestMeetingTime(Calendar latestMeetingTime) {
        this.latestMeetingTime = latestMeetingTime;
    }
}
