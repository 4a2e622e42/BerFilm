package com.ash.berfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.PopupMenu;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.ViewModel.AppViewModel;
import com.ash.berfilm.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding activityMainBinding;
    NavHostFragment navHostFragment;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    AppViewModel appViewModel;
    CompositeDisposable disposable;
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        drawerLayout = activityMainBinding.drawerLayout;

        disposable = new CompositeDisposable();


        setUpViewModel();
        setUpNavigationComponent();
        callTrendingApi();



    }

    private void setUpNavigationComponent()
    {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController   = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment,R.id.movieFragment,R.id.seriesFragment,R.id.favoriteFragment)
                .setOpenableLayout(activityMainBinding.drawerLayout)
                .build();


        NavigationUI.setupWithNavController(activityMainBinding.navigationView,navController);


        setupSmoothBottomBar();

    }

    private void setupSmoothBottomBar()
    {
        PopupMenu popupMenu = new PopupMenu(this,null);
        popupMenu.inflate(R.menu.smooth_bottombar_menu);
        Menu menu = popupMenu.getMenu();
        activityMainBinding.smoothBottombar.setupWithNavController(menu,navController);
    }

    private void setUpViewModel()
    {
        appViewModel = new ViewModelProvider(MainActivity.this).get(AppViewModel.class);

    }


    private void callTrendingApi()
    {
        Observable.interval(3, TimeUnit.SECONDS)
                .flatMap(n -> appViewModel.makeFutureCall().get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>()
                {
                    @Override
                    public void onSubscribe(@NonNull Disposable d)
                    {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Movie movie)
                    {
                        appViewModel.insertTrending(movie);
                    }

                    @Override
                    public void onError(@NonNull Throwable e)
                    {

                    }

                    @Override
                    public void onComplete()
                    {

                    }
                });
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        disposable.clear();
    }
}