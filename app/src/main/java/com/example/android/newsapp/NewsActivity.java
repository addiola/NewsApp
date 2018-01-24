package com.example.android.newsapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;


/**
 * Created by Addi_ola on 17/01/2018.
 */

public class NewsActivity extends AppCompatActivity {



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Set the content of the activity to use the activity_tab.xml layout file
            setContentView(R.layout.view_pager);

            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

            // Create an adapter that knows which fragment should be shown on each page
            MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), this);

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout =  findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);

        }
    }


