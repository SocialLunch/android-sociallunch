package com.sociallunch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sociallunch.android.R;
import com.sociallunch.android.fragments.SearchListFragment;
import com.sociallunch.android.fragments.SearchMapFragment;

public class SearchFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    public enum SearchTabIndex {
        LIST,
        MAP
    }
    private Context context;
    private SearchListFragment searchListFragment;
    private SearchMapFragment searchMapFragment;

    public SearchFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
        if (position == SearchTabIndex.LIST.ordinal()) {
            return context.getString(R.string.label_list);
        }
        else if (position == SearchTabIndex.MAP.ordinal()) {
            return context.getString(R.string.label_map);
        }
        return context.getString(R.string.app_name);
    }
}
