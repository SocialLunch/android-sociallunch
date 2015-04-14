package com.sociallunch.android.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;
import com.sociallunch.android.R;
import com.sociallunch.android.fragments.HowItWorksStepFragment;

public class HowItWorksActivity extends ActionBarActivity {
    public static final String PREF_HOW_IT_WORKS_SHOWN = "how_it_works_shown";

    public static final int NUM_OF_TABS = 3;
    public static final int TAB_INDEX_BADGE_CREATE_OR_JOIN = 0;
    public static final int TAB_INDEX_CHAT = 1;
    public static final int TAB_INDEX_BE_SOCIAL = 2;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private View mViewPagingCtrl;
    private Button mButtonSkip;
    private Button mButtonNext;
    private Button mButtonGetStarted;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);

        mViewPager = (ViewPager) findViewById(R.id.how_it_works__pager);
        mViewPagingCtrl = findViewById(R.id.how_it_works__view_paging_ctrl);
        mButtonSkip = (Button) findViewById(R.id.how_it_works__button_skip);
        mButtonNext = (Button) findViewById(R.id.how_it_works__button_next);
        mButtonGetStarted = (Button) findViewById(R.id.how_it_works__button_get_started);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.how_it_works__tabs);
        mPagerSlidingTabStrip.setIndicatorColorResource(R.color.dark_purple);
        mPagerSlidingTabStrip.setTextColorResource(android.R.color.white);
        mPagerSlidingTabStrip.setDividerColorResource(android.R.color.transparent);
        mPagerSlidingTabStrip.setShouldExpand(true);    // has to be invoked before setting view pager
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == TAB_INDEX_BE_SOCIAL) {
                    mViewPagingCtrl.setVisibility(View.GONE);
                    mButtonGetStarted.setVisibility(View.VISIBLE);
                } else {
                    mButtonGetStarted.setVisibility(View.GONE);
                    mViewPagingCtrl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mPagerSlidingTabStrip.setViewPager(mViewPager);

        mButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        mButtonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case TAB_INDEX_BADGE_CREATE_OR_JOIN:
                    return HowItWorksStepFragment.newInstance(R.drawable.how_it_works_create_or_join,
                            R.string.how_it_works__label_create_or_join_title,
                            R.string.how_it_works__label_create_or_join_desc);
                case TAB_INDEX_CHAT:
                    return HowItWorksStepFragment.newInstance(R.drawable.how_it_works_chat,
                            R.string.how_it_works__label_chat_title,
                            R.string.how_it_works__label_chat_desc);
                case TAB_INDEX_BE_SOCIAL:
                    return HowItWorksStepFragment.newInstance(R.drawable.how_it_works_be_social,
                            R.string.how_it_works__label_be_social_title,
                            R.string.how_it_works__label_be_social_desc);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show total tabs.
            return NUM_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
