package com.ash.berfilm.DetailFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.DetailFragment.Adopters.CreditsAdopter;
import com.ash.berfilm.DetailFragment.Adopters.SeriesCastAdopter;
import com.ash.berfilm.DetailFragment.Adopters.SimilarMovieAdopter;
import com.ash.berfilm.DetailFragment.Adopters.SimilarSeriesAdopter;
import com.ash.berfilm.Models.Credits.Cast;
import com.ash.berfilm.Models.Credits.Credits;
import com.ash.berfilm.Models.Credits.Crew;
import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.Service.ApiClient;
import com.ash.berfilm.databinding.FragmentSereisDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SeriesDetailFragment extends Fragment
{
    FragmentSereisDetailBinding fragmentSeriesBinding;
    SeriesCastAdopter seriesCastAdopter;
    SimilarSeriesAdopter similarSeriesAdopter;
    List<Cast> castList;
    List<Crew> crewList;
    List<MovieResult> similarList;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {


        fragmentSeriesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sereis_detail,container,false);

        MovieResult series = getArguments().getParcelable("model");

        setUpCreditsDetail(series.getId());
        getSimilar(series.getId());
        setupDetail(series);


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


        fragmentSeriesBinding.movieName.setText(seriesResult.getName());
        fragmentSeriesBinding.releaseDateText.setText(seriesResult.getFirstAirDate());

        fragmentSeriesBinding.rateText.setText(String.format("%.1f" ,seriesResult.getVoteAverage()));
        fragmentSeriesBinding.overViewContent.setText(seriesResult.getOverview());

    }


    private void setUpCreditsDetail(int id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<Credits> call = apiClient.getSeriesCredits(id);


        call.enqueue(new Callback<Credits>() {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response)
            {
                castList = response.body().getCast();
                crewList = response.body().getCrew();

                if(fragmentSeriesBinding.castRecyclerView.getAdapter() != null)
                {
                    seriesCastAdopter = (SeriesCastAdopter) fragmentSeriesBinding.castRecyclerView.getAdapter();
                }else
                {
                    seriesCastAdopter = new SeriesCastAdopter(castList);
                    fragmentSeriesBinding.castRecyclerView.setAdapter(seriesCastAdopter);
                }

                //Get Producer Name
                for(int i = 0;i<crewList.size();i++)
                {
                    if(crewList.get(i).getJob().equals("Producer"))
                    {
                        fragmentSeriesBinding.directerName.setText(crewList.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t)
            {

            }
        });



    }


    private void getSimilar(int id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient apiClient = retrofit.create(ApiClient.class);

        Call<Movie> call = apiClient.getSimilarSeries(id);

      call.enqueue(new Callback<Movie>()
      {
          @Override
          public void onResponse(Call<Movie> call, Response<Movie> response)
          {
              similarList = response.body().getResults();


              if(fragmentSeriesBinding.similarRecyclerView.getAdapter() != null)
              {
                  similarSeriesAdopter = (SimilarSeriesAdopter) fragmentSeriesBinding.similarRecyclerView.getAdapter();
              }else
              {
                  similarSeriesAdopter = new SimilarSeriesAdopter(similarList);
                  fragmentSeriesBinding.similarRecyclerView.setAdapter(similarSeriesAdopter);
              }

          }

          @Override
          public void onFailure(Call<Movie> call, Throwable t)
          {

          }
      });


    }





}