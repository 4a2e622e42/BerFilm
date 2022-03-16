package com.ash.berfilm.RoomDb.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.ash.berfilm.RoomDb.Entity.TrendingEntity;

import io.reactivex.rxjava3.core.Flowable;

@androidx.room.Dao
public interface TrendingDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrendingEntity trendingEntity);


    @Query("SELECT * FROM trending")
    Flowable<TrendingEntity> getTrendingFromDb();

}
