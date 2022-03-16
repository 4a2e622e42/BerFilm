package com.ash.berfilm.MovieFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.MainActivity;
import com.ash.berfilm.R;
import com.ash.berfilm.databinding.FragmentMovieBinding;

public class MovieFragment extends Fragment
{
    FragmentMovieBinding fragmentMoviesBinding;
    MainActivity mainActivity;

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setUpToolBar(view);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        fragmentMoviesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie, container, false);



        return  fragmentMoviesBinding.getRoot();
    }

    private void setUpToolBar(View view)
    {
        NavController navController = Navigation.findNavController(view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.movieFragment)
                .setOpenableLayout(mainActivity.drawerLayout)
                .build();

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.movieFragment)
                {
                    toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                    toolbar.setTitle("Movies");
                    toolbar.setTitleTextAppearance(view.getContext(),R.style.toolBarTitleFont);

                }
            }
        });

    }
}