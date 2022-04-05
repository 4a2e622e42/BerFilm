package com.ash.berfilm.DetailFragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.DetailFragment.Adopters.CreditsAdopter;
import com.ash.berfilm.DetailFragment.Adopters.RecommendedMovieAdopter;
import com.ash.berfilm.Models.Credits.Cast;
import com.ash.berfilm.Models.Credits.Credits;
import com.ash.berfilm.Models.Credits.Crew;
import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.Service.ApiClient;
import com.ash.berfilm.databinding.FragmentDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class DetailFragment extends Fragment
{
    FragmentDetailBinding fragmentDetailBinding;
    CreditsAdopter creditsAdopter;
    List<Cast> castList;
    List<Crew> crewList;
    List<MovieResult> recommendedMovie;
    RecommendedMovieAdopter recommendedMovieAdopter;
    @Inject
    ApiClient apiClient;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        MovieResult movie = getArguments().getParcelable("model");




        setupDetail(movie);
        setUpMovieCreditsDetail(movie.getId());
        getRecommendedMovie(movie.getId());


        fragmentDetailBinding.playTrailerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putInt("movieId",movie.getId());
                Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_movieTrailerFragment,bundle);
            }
        });


        return fragmentDetailBinding.getRoot();
    }

    private void setupDetail(MovieResult moviesResult) {

        Glide.with(fragmentDetailBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500" + moviesResult.getBackdropPath())
                .into(fragmentDetailBinding.poster);

        Glide.with(fragmentDetailBinding.getRoot().getContext())
                .load("https://image.tmdb.org/t/p/w500" + moviesResult.getPosterPath())
                .into(fragmentDetailBinding.mainPoster);


        fragmentDetailBinding.movieName.setText(moviesResult.getTitle());
        fragmentDetailBinding.releaseDateText.setText(moviesResult.getReleaseDate());


        fragmentDetailBinding.rateText.setText(String.format("%.1f" ,moviesResult.getVoteAverage(),moviesResult.getVoteAverage()));
        fragmentDetailBinding.overViewContent.setText(moviesResult.getOverview());


    }

    private void setUpMovieCreditsDetail(int id)
    {

        Call<Credits> call = apiClient.getMovieCredits(id);

        call.enqueue(new Callback<Credits>()
        {
            @Override
            public void onResponse(Call<Credits> call, Response<Credits> response)
            {
                castList = response.body().getCast();
                crewList = response.body().getCrew();


                if(fragmentDetailBinding.castRecyclerView.getAdapter() != null)
                {
                    creditsAdopter = (CreditsAdopter) fragmentDetailBinding.castRecyclerView.getAdapter();
                }else
                {
                    creditsAdopter = new CreditsAdopter(castList);
                    fragmentDetailBinding.castRecyclerView.setAdapter(creditsAdopter);
                }
                for (int i = 0; i < crewList.size(); i++)
                {
                    if (crewList.get(i).getJob().equals("Director"))
                    {
                        fragmentDetailBinding.directerName.setText(crewList.get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<Credits> call, Throwable t)
            {

            }
        });

    }


    private void getRecommendedMovie(int id)
    {
        Call<Movie> call = apiClient.getRecommendedMovie(id);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                recommendedMovie = response.body().getResults();


                if (fragmentDetailBinding.RecommendedRecyclerView.getAdapter() != null) {
                    recommendedMovieAdopter = (RecommendedMovieAdopter) fragmentDetailBinding.RecommendedRecyclerView.getAdapter();
                } else {
                    recommendedMovieAdopter = new RecommendedMovieAdopter(recommendedMovie);
                    fragmentDetailBinding.RecommendedRecyclerView.setAdapter(recommendedMovieAdopter);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
