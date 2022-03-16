package com.ash.berfilm.Service;

import com.ash.berfilm.Models.MovieModel.Movie;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiClient
{
    //Base_URL =https://api.themoviedb.org/3

    //Get Trending
    @GET("trending/all/day?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getTrending();

    @GET("movie/popular?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getPopular();


}
