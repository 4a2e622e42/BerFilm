package com.ash.berfilm.SearchFragment.Series;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.SearchFragment.Movies.Adopters.SearchMovieAdopter;
import com.ash.berfilm.SearchFragment.Series.Adopter.SearchSeriesAdopter;
import com.ash.berfilm.ViewModel.AppViewModel;
import com.ash.berfilm.databinding.FragmentSeriesSearchBinding;

import java.util.List;

import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesSearchFragment extends Fragment
{

    FragmentSeriesSearchBinding fragmentSeriesSearchBinding;
    AppViewModel appViewModel;
    int page = 1;
    int totalPage;
    List<MovieResult> searchResult;
    SearchSeriesAdopter searchSeriesAdopter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        fragmentSeriesSearchBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_series_search, container, false);
        appViewModel = new ViewModelProvider(getActivity()).get(AppViewModel.class);

        SmoothBottomBar smoothBottomBar = getActivity().findViewById(R.id.smooth_bottombar);
        smoothBottomBar.setVisibility(View.GONE);


        String searchedText = getArguments().getString("searchedText");


        findSearchedSeries(searchedText,page);

        fragmentSeriesSearchBinding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
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
                    findSearchedSeries(searchedText,page);
                    fragmentSeriesSearchBinding.progressBar.setVisibility(View.VISIBLE);
                }
            }
        });



        return fragmentSeriesSearchBinding.getRoot();
    }

    private void findSearchedSeries(String searchText,int page)
    {
        appViewModel.makeSeriesSearchCall(searchText,page).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response)
            {
                searchResult = response.body().getResults();
                totalPage = response.body().getTotalPages();
                fragmentSeriesSearchBinding.progressBar.setVisibility(View.GONE);

                fragmentSeriesSearchBinding.movieCutAnimation.setVisibility(View.GONE);

                if(fragmentSeriesSearchBinding.searchRecyclerView.getAdapter() !=null)
                {
                    searchSeriesAdopter = (SearchSeriesAdopter) fragmentSeriesSearchBinding.searchRecyclerView.getAdapter();
                    searchSeriesAdopter.addItems(searchResult);
                }else
                {
                    searchSeriesAdopter = new SearchSeriesAdopter(searchResult);
                    fragmentSeriesSearchBinding.searchRecyclerView.setAdapter(searchSeriesAdopter);
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