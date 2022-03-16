package com.ash.berfilm;

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

import io.reactivex.rxjava3.core.Observable;

public class AppRepository
{

    ApiClient apiClient;

    public AppRepository(ApiClient apiClient)
    {
        this.apiClient = apiClient;
    }



    public Future<Observable<Movie>> trendingFutureCall()
    {
       final ExecutorService executorService = Executors.newSingleThreadExecutor();


       final Callable<Observable<Movie>> trendingCallable = new Callable<Observable<Movie>>()
       {
           @Override
           public Observable<Movie> call() throws Exception
           {

               return apiClient.getTrending();
           }
       };

       final Future<Observable<Movie>> observableFuture = new Future<Observable<Movie>>()
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
               return executorService.submit(trendingCallable).get();
           }

           @Override
           public Observable<Movie> get(long timeout, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException
           {
               return executorService.submit(trendingCallable).get(timeout,timeUnit);
           }
       };


        return observableFuture;
    }



}
