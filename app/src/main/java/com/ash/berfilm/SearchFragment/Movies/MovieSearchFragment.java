package com.ash.berfilm.SearchFragment.Movies;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.MovieFragment.Adopter.MoviesAdopter;
import com.ash.berfilm.R;
import com.ash.berfilm.SearchFragment.Movies.Adopters.SearchMovieAdopter;
import com.ash.berfilm.Service.ApiClient;
import com.ash.berfilm.ViewModel.AppViewModel;
import com.ash.berfilm.databinding.FragmentMovieSearchBinding;

import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieSearchFragment extends Fragment
{
    FragmentMovieSearchBinding fragmentMovieSearchBinding;
    AppViewModel appViewModel;
    int page = 1;
    int totalPage;
    List<MovieResult> searchResult;
    SearchMovieAdopter searchMovieAdopter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        fragmentMovieSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie_search, container, false);
        appViewModel = new ViewModelProvider(getActivity()).get(AppViewModel.class);

        SmoothBottomBar smoothBottomBar = getActivity().findViewById(R.id.smooth_bottombar);
        smoothBottomBar.setVisibility(View.GONE);


        String searchedText = getArguments().getString("searchedText");

        findSearchedMovie(searchedText,page);

        fragmentMovieSearchBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    if(page < totalPage)
                    {
                        page++;
                    }else if(page == totalPage)
                    {
                        page = totalPage;
                    }
                    findSearchedMovie(searchedText,page);
                    fragmentMovieSearchBinding.progressBar.setVisibility(View.VISIBLE);
                }
            }
        });






        return fragmentMovieSearchBinding.getRoot();
    }


    private void  findSearchedMovie(String searchText,int page)
    {
        appViewModel.makeSearchCall(searchText,page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response)
            {
                searchResult = response.body().getResults();
                totalPage = response.body().getTotalPages();
                fragmentMovieSearchBinding.progressBar.setVisibility(View.GONE);

                fragmentMovieSearchBinding.movieCutAnimation.setVisibility(View.GONE);

                if(fragmentMovieSearchBinding.searchRecyclerView.getAdapter() !=null)
                {
                    searchMovieAdopter = (SearchMovieAdopter) fragmentMovieSearchBinding.searchRecyclerView.getAdapter();
                    searchMovieAdopter.addItems(searchResult);
                }else
                {
                    searchMovieAdopter = new SearchMovieAdopter(searchResult);
                    fragmentMovieSearchBinding.searchRecyclerView.setAdapter(searchMovieAdopter);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        SmoothBottomBar smoothBottomBar = getActivity().findViewById(R.id.smooth_bottombar);
        smoothBottomBar.setVisibility(View.VISIBLE);
    }
}