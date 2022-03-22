package com.ash.berfilm;

import android.os.Bundle;

import com.ash.berfilm.Models.Credits.Credits;
import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Service.ApiClient;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppRepository
{

    ApiClient apiClient;


    public AppRepository(ApiClient apiClient)
    {
        this.apiClient = apiClient;
    }



    public Future<Observable<Movie>> trendingMovieFutureCall()
    {
       final ExecutorService executorService = Executors.newSingleThreadExecutor();


       final Callable<Observable<Movie>> trendingMovieCallable = new Callable<Observable<Movie>>()
       {
           @Override
           public Observable<Movie> call() throws Exception
           {

               return apiClient.getTrendingMovie();
           }
       };

       final Future<Observable<Movie>> observableTrendingMovieFuture = new Future<Observable<Movie>>()
       {
           @Override
           public boolean cancel(boolean b)
           {
               if(b)
               {
                   return executorService.isShutdown();
               }
               return false;
           }

           @Override
           public boolean isCancelled()
           {
               return executorService.isShutdown();
           }

           @Override
           public boolean isDone()
           {
               return executorService.isTerminated();
           }

           @Override
           public Observable<Movie> get() throws ExecutionException, InterruptedException
           {
               return executorService.submit(trendingMovieCallable).get();
           }

           @Override
           public Observable<Movie> get(long timeout, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException
           {
               return executorService.submit(trendingMovieCallable).get(timeout,timeUnit);
           }
       };


        return observableTrendingMovieFuture;
    }



    public Future<Observable<Movie>> trendingSeriesFutureCall()
    {
        final  ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> trendingSeriesCallable = new Callable<Observable<Movie>>()
        {
            @Override
            public Observable<Movie> call() throws Exception
            {
                return apiClient.getTrendingSeries();
            }
        };


        final Future<Observable<Movie>> trendingSeriesFuture = new Future<Observable<Movie>>()
        {
            @Override
            public boolean cancel(boolean b)
            {
                if (b)
                {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled()
            {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone()
            {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException
            {
                return executorService.submit(trendingSeriesCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException
            {
                return executorService.submit(trendingSeriesCallable).get(timeOut,timeUnit);
            }
        };


        return  trendingSeriesFuture;

    }

































    public Future<Observable<Movie>> popularFutureCall()
    {
      final   ExecutorService executorService = Executors.newSingleThreadExecutor();

      final Callable<Observable<Movie>> popularCallable = new Callable<Observable<Movie>>()
      {
          @Override
          public Observable<Movie> call() throws Exception
          {
              return apiClient.getPopular();
          }
      };


      final Future<Observable<Movie>> popularFuture = new Future<Observable<Movie>>()
      {
          @Override
          public boolean cancel(boolean b)
          {
              if (b)
              {
                  return executorService.isShutdown();
              }
              return false;
          }

          @Override
          public boolean isCancelled()
          {
              return executorService.isShutdown();
          }

          @Override
          public boolean isDone()
          {
              return executorService.isTerminated();
          }

          @Override
          public Observable<Movie> get() throws ExecutionException, InterruptedException
          {
              return executorService.submit(popularCallable).get();
          }

          @Override
          public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException
          {
              return executorService.submit(popularCallable).get(timeOut,timeUnit);
          }
      };


        return  popularFuture;

    }




    public Future<Observable<Movie>> upComingFutureCall()
    {
        final   ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Callable<Observable<Movie>> popularCallable = new Callable<Observable<Movie>>()
        {
            @Override
            public Observable<Movie> call() throws Exception
            {
                return apiClient.getUpComing();
            }
        };


        final Future<Observable<Movie>> upComingFuture = new Future<Observable<Movie>>()
        {
            @Override
            public boolean cancel(boolean b)
            {
                if (b)
                {
                    return executorService.isShutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled()
            {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone()
            {
                return executorService.isTerminated();
            }

            @Override
            public Observable<Movie> get() throws ExecutionException, InterruptedException
            {
                return executorService.submit(popularCallable).get();
            }

            @Override
            public Observable<Movie> get(long timeOut, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException
            {
                return executorService.submit(popularCallable).get(timeOut,timeUnit);
            }
        };


        return  upComingFuture;

    }




}
