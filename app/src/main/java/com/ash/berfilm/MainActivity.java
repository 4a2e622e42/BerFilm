package com.ash.berfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.widget.PopupMenu;

import com.ash.berfilm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding activityMainBinding;
    NavHostFragment navHostFragment;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        drawerLayout = activityMainBinding.drawerLayout;


        setUpNavigationComponent();



    }

    private void setUpNavigationComponent()
    {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        navController   = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(R.layout.fragment_home,R.id.moviesFragment,R.id.seriesFragment,R.id.favoritFragment)
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
}