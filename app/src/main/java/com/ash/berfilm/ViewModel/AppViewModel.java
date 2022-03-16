package com.ash.berfilm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.ash.berfilm.AppRepository;
import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.RoomDb.Entity.TrendingEntity;

import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class AppViewModel extends ViewModel
{

    @Inject
    AppRepository appRepository;


    @Inject
    public AppViewModel(Application application)
    {
        super();
    }

    public Future<Observable<Movie>> makeFutureCall()
    {
        return appRepository.trendingFutureCall();
    }

    public void insertTrending(Movie movie)
    {
        appRepository.InsertTrending(movie);
    }

    public Flowable<TrendingEntity> getTrending()
    {
        return appRepository.getTrending();
    }



}
