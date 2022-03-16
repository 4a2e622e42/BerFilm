package com.ash.berfilm.HomeFragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ash.berfilm.MainActivity;
import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.R;
import com.ash.berfilm.RoomDb.Entity.TrendingEntity;
import com.ash.berfilm.ViewModel.AppViewModel;
import com.ash.berfilm.databinding.FragmentHomeBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class HomeFragment extends Fragment
{
    FragmentHomeBinding fragmentHomeBinding;
    MainActivity mainActivity;
    AppViewModel appViewModel;
    CompositeDisposable compositeDisposable;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);

        appViewModel = new ViewModelProvider(getActivity()).get(AppViewModel.class);

        compositeDisposable = new CompositeDisposable();



        getTrending();


        return fragmentHomeBinding.getRoot();
    }


    private void setUpToolBar(View view)
    {
       NavController navController = Navigation.findNavController(view);

       AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment)
               .setOpenableLayout(mainActivity.drawerLayout)
               .build();

       Toolbar toolbar = view.findViewById(R.id.toolbar);

       NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);


       navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
       {
           @Override
           public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
           {
               if(destination.getId() == R.id.homeFragment)
               {
                   toolbar.setNavigationIcon(R.drawable.ic_baseline_sort_24);
                   toolbar.setTitle("BerFilm");
                   toolbar.setTitleTextAppearance(view.getContext(),R.style.toolBarTitleFont);
               }

           }
       });


    }


    private void getTrending()
    {
        Disposable disposable = appViewModel.getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrendingEntity>() {
                    @Override
                    public void accept(TrendingEntity trendingEntity) throws Throwable
                    {
                        Movie trending = trendingEntity.getTrending();
                        Log.e("TAG","Name:  "+trending.getResults().get(0).getOriginalTitle());
                    }
                });

        compositeDisposable.add(disposable);

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        compositeDisposable.clear();
    }
}