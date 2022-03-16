package com.ash.berfilm.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ash.berfilm.RoomDb.Converter.TrendingConverter;
import com.ash.berfilm.RoomDb.Dao.TrendingDao;
import com.ash.berfilm.RoomDb.Entity.TrendingEntity;

@TypeConverters({TrendingConverter.class})
@Database(entities = TrendingEntity.class,version = 1)
public abstract class AppDataBase extends RoomDatabase
{
    private static final String DbName = "AppDataBase";
    private static AppDataBase instance;
    public abstract TrendingDao trendingDao();


    public static synchronized AppDataBase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,DbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
