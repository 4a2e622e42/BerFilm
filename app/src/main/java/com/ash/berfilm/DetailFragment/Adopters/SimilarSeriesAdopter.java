package com.ash.berfilm.DetailFragment.Adopters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ash.berfilm.Models.MovieModel.MovieResult;
import com.ash.berfilm.R;
import com.ash.berfilm.databinding.MovieItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class SimilarSeriesAdopter extends RecyclerView.Adapter<SimilarSeriesAdopter.SimilarSeriesViewHolder>
{
    List<MovieResult> similarList;
    LayoutInflater layoutInflater;

    public SimilarSeriesAdopter(List<MovieResult> similarList)
    {
        this.similarList = similarList;
    }

    @NonNull
    @Override
    public SimilarSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item,parent,false);

        return new SimilarSeriesViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarSeriesViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.bind(similarList.get(position));
        holder.movieItemBinding.trendingPoster.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putParcelable("model",similarList.get(position));

                Navigation.findNavController(view).navigate(R.id.action_seriesDetailFragment_self,bundle);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return similarList.size();
    }

    public class SimilarSeriesViewHolder extends RecyclerView.ViewHolder
    {
        MovieItemBinding movieItemBinding;

        public SimilarSeriesViewHolder(@NonNull MovieItemBinding movieItemBinding)
        {
            super(movieItemBinding.getRoot());

            this.movieItemBinding = movieItemBinding;
        }


        public void bind(MovieResult SeriesResult)
        {
            loadPoster(SeriesResult);
            loadRate(SeriesResult);

            movieItemBinding.executePendingBindings();

        }


        private void loadPoster(MovieResult similarSeries)
        {
            Glide.with(movieItemBinding.getRoot().getContext())
                    .load("https://image.tmdb.org/t/p/w500"+ similarSeries.getPosterPath())
                    .thumbnail(Glide.with(movieItemBinding.getRoot().getContext()).load(R.drawable.loading))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(movieItemBinding.trendingPoster);



        }
        private void loadRate(MovieResult similarSeries)
        {
            movieItemBinding.trendingRateButton.setText("TMDB: "+ String.format("%.1f" ,similarSeries.getVoteAverage()));
        }









    }


}
