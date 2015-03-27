package com.sociallunch.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sociallunch.android.fragments.ActiveSessionsFragment;
import com.sociallunch.android.fragments.PastSessionsFragment;

public class SessionsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    public enum SearchTabIndex {
        ACTIVE,
        PAST
    }
    private String tabTitles[] = new String[] { "Upcoming", "History" };

    private ActiveSessionsFragment activeSessionsFragment;
    private PastSessionsFragment pastSessionsFragment;

    public SessionsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == SearchTabIndex.ACTIVE.ordinal()) {
            if (activeSessionsFragment == null) {
                activeSessionsFragment = ActiveSessionsFragment.newInstance();
            }
            return activeSessionsFragment;
        }
        else if (position == SearchTabIndex.PAST.ordinal()) {
            if (pastSessionsFragment == null) {
                pastSessionsFragment = PastSessionsFragment.newInstance();
            }
            return pastSessionsFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
