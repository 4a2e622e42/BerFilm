package com.ash.berfilm.FavoriteFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment
{
    FragmentFavoriteBinding fragmentFavoriteBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragmentFavoriteBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false);



        return fragmentFavoriteBinding.getRoot();
    }
}