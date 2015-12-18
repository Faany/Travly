package com.examle.faany.travels.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.examle.faany.travels.R;

/**
 * Created by farha on 17-Dec-15.
 */
public class GeneralFragment extends android.support.v4.app.Fragment
{

    FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        manager = getFragmentManager();

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_general , container , false);
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        super.onActivityCreated(savedInstanceState);

    }


}
