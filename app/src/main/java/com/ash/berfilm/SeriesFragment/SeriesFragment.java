package com.ash.berfilm.SeriesFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentSereisBinding;

public class SeriesFragment extends Fragment
{
    FragmentSereisBinding fragmentSereisBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragmentSereisBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sereis,container,false);


        return fragmentSereisBinding.getRoot();
    }
}