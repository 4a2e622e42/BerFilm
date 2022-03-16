package com.ash.berfilm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.ash.berfilm.AppRepository;
import com.ash.berfilm.Models.MovieModel.Movie;

import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
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



}
