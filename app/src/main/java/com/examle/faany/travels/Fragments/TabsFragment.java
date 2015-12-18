package com.examle.faany.travels.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.examle.faany.travels.R;

/**
 * Created by farha on 19-Dec-15.
 */
public class TabsFragment extends Fragment
{

    ViewPager VPager;
    ViewPagerAdapter  mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tabs , container , false);
        return view;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        VPager = (ViewPager) getView().findViewById(R.id.pager);

        mAdapter = new ViewPagerAdapter (getChildFragmentManager());        //pager ko chalany k liye adapter chaiye hota

        VPager.setAdapter(mAdapter);


    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter
    {
        public ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = null;

            if (position == 0)
            {
                fragment = new TukTukFragment();
            }
            if (position == 1)
            {
                fragment = new BusFragment();

            }

            return fragment;
        }

        @Override
        public int getCount()
        {
            return 2;
        }



        @Override
        public CharSequence getPageTitle(int position)      //for page titile of every page
        {
            if (position == 0)
                return " TUK TUK";
            if (position == 1)
                return "Bus ";

            return null;
        }

    }


}
