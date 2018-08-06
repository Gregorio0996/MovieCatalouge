package com.example.gregorio.moviecatalouge;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class HomeFragment extends Fragment {
    ViewPager mViewPager;
    TabLayout mTabLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));

        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;

    }

    private class sliderAdapter extends FragmentStatePagerAdapter {

        String nowplay = getResources().getString(R.string.nowplay);
        String upcome = getResources().getString(R.string.upcome);
        final String tabs[] = {nowplay, upcome};

        public sliderAdapter(FragmentManager fragmentman) {
            super(fragmentman);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NowPlayFragment();
                case 1:
                    return new UpComeFragment();


            }
            return null;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }

}
