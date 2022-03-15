package com.ash.berfilm.MovieFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentMovieBinding;

public class MovieFragment extends Fragment
{
    FragmentMovieBinding fragmentMovieBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragmentMovieBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false);



        return fragmentMovieBinding.getRoot();
    }
}