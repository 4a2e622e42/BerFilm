package com.ash.berfilm.Hilt.Modules;

import android.content.Context;

import com.ash.berfilm.RoomDb.AppDataBase;
import com.ash.berfilm.RoomDb.Dao.TrendingDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RoomDbModule
{
    @Provides
    @Singleton
    AppDataBase provideAppDataBase(@ApplicationContext Context context)
    {
        return AppDataBase.getInstance(context);
    }

    @Provides
    @Singleton
    TrendingDao provideRoomDao(AppDataBase appDataBase)
    {
        return appDataBase.trendingDao();
    }
}
