package com.ash.berfilm.Service;

import com.ash.berfilm.Models.CastInfo.CastInfo;
import com.ash.berfilm.Models.Credits.Credits;
import com.ash.berfilm.Models.MovieModel.Movie;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClient
{
    //Base_URL =https://api.themoviedb.org/3

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    //Movies Api

    //Get Trending Movie
    @GET("trending/movie/day?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getTrendingMovie();

    //Get Popular Movie
    @GET("movie/popular?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getPopular();

    //Get Movie Cast
    @GET("movie/{id}/credits?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Call<Credits> getMovieCredits(@Path("id") int id);

    //Get Upcoming Movie
    @GET("movie/upcoming?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getUpComing();

    //Get Similar Movie
    @GET("movie/{id}/similar?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Call<Movie> getSimilarMovie(@Path("id") int id);



    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    //Series Api

    //Get Trending Series
    @GET("trending/tv/day?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Observable<Movie> getTrendingSeries();

    //Get Series Cast
    @GET("tv/{id}/credits?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Call<Credits> getSeriesCredits(@Path("id") int id);


    //Get Similar Series
    @GET("tv/{id}/similar?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Call<Movie> getSimilarSeries(@Path("id") int id);

    //->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->->
    //Cast Information

    @GET("person/{id}?api_key=cabfea895bffe3f80d9b51460c2c2693")
    Call<CastInfo> getCastInfo(@Path("id") int id);


}
