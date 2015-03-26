package com.sociallunch.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sociallunch.android.fragments.SearchListFragment;
import com.sociallunch.android.fragments.SearchMapFragment;

public class SearchFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    public enum SearchTabIndex {
        LIST,
        MAP
    }
    private String tabTitles[] = new String[] { "List", "Map" };

    private SearchListFragment searchListFragment;
    private SearchMapFragment searchMapFragment;

    public SearchFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == SearchTabIndex.LIST.ordinal()) {
            if (searchListFragment == null) {
                searchListFragment = SearchListFragment.newInstance();
            }
            return searchListFragment;
        }
        else if (position == SearchTabIndex.MAP.ordinal()) {
            if (searchMapFragment == null) {
                searchMapFragment = SearchMapFragment.newInstance();
            }
            return searchMapFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
