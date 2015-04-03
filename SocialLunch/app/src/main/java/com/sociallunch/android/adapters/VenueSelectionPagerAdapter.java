package com.sociallunch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sociallunch.android.R;
import com.sociallunch.android.fragments.VenueSelectionListFragment;
import com.sociallunch.android.fragments.VenueSelectionMapFragment;

public class VenueSelectionPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;

    public enum VenueSelectionTabIndex {
        LIST,
        MAP
    }

    private Context context;
    private VenueSelectionListFragment venueSelectionListFragment;
    private VenueSelectionMapFragment venueSelectionMapFragment;

    public VenueSelectionPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == VenueSelectionTabIndex.LIST.ordinal()) {
            if (venueSelectionListFragment == null) {
                venueSelectionListFragment = VenueSelectionListFragment.newInstance();
            }
            return venueSelectionListFragment;
        } else if (position == VenueSelectionTabIndex.MAP.ordinal()) {
            if (venueSelectionMapFragment == null) {
                venueSelectionMapFragment = VenueSelectionMapFragment.newInstance();
            }
            return venueSelectionMapFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == VenueSelectionTabIndex.LIST.ordinal()) {
            return context.getString(R.string.label_list);
        } else if (position == VenueSelectionTabIndex.MAP.ordinal()) {
            return context.getString(R.string.label_map);
        }
        return context.getString(R.string.app_name);
    }
}
