package com.ash.berfilm.DetailFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentSereisDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class SeriesDetailFragment extends Fragment
{
    FragmentSereisDetailBinding fragmentSeriesBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {


        fragmentSeriesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sereis_detail,container,false);

        MovieResult movie = getArguments().getParcelable("model");


        setupDetail(movie);


        return fragmentSeriesBinding.getRoot();
    }

    private void setupDetail(MovieResult seriesResult)
    {

        Glide.with(fragmentSeriesBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500"+ seriesResult.getBackdropPath())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(5, 3)))
                .into(fragmentSeriesBinding.poster);

        Glide.with(fragmentSeriesBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500"+ seriesResult.getPosterPath())
                .into(fragmentSeriesBinding.mainPoster);


        if(seriesResult.getMediaType().equals("tv"))
        {
            fragmentSeriesBinding.movieName.setText(seriesResult.getName());
            fragmentSeriesBinding.releaseDateText.setText(seriesResult.getFirstAirDate());
        }else
        {
            fragmentSeriesBinding.movieName.setText(seriesResult.getTitle());
            fragmentSeriesBinding.releaseDateText.setText(seriesResult.getReleaseDate());
        }







        fragmentSeriesBinding.rateText.setText(String.valueOf(seriesResult.getVoteAverage()));
        fragmentSeriesBinding.overViewContent.setText(seriesResult.getOverview());



    }

}