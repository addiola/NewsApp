package com.example.android.newsapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Addi_ola on 21/01/2018.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private Context context;


    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PoliticsFragment();
        } else if (position == 1) {
            return new BusinessFragment();
        } else {
            return new WorldFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        if (position == 0) {
            return context.getString(R.string.politics);
        } else if (position == 1) {
            return context.getString(R.string.business);
        } else

            return context.getString(R.string.world);

    }
}
