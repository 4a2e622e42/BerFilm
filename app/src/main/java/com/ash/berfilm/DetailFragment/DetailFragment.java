package com.ash.berfilm.DetailFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class DetailFragment extends Fragment
{
    FragmentDetailBinding fragmentDetailBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {


        fragmentDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false);

        MovieResult movie = getArguments().getParcelable("model");



        setupDetail(movie);


        return fragmentDetailBinding.getRoot();
    }

    private void setupDetail(MovieResult moviesResult)
    {

        Glide.with(fragmentDetailBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500"+ moviesResult.getBackdropPath())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 3)))
                .into(fragmentDetailBinding.poster);

        Glide.with(fragmentDetailBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500"+ moviesResult.getPosterPath())
                .into(fragmentDetailBinding.mainPoster);



        fragmentDetailBinding.movieName.setText(moviesResult.getTitle());
        fragmentDetailBinding.releaseDateText.setText(moviesResult.getReleaseDate());


        fragmentDetailBinding.rateText.setText(String.valueOf(moviesResult.getVoteAverage()));
        fragmentDetailBinding.overViewContent.setText(moviesResult.getOverview());



    }
}