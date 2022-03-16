package com.ash.berfilm.RoomDb.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ash.berfilm.Models.MovieModel.Movie;

@Entity(tableName = "trending")
public class TrendingEntity
{
    @PrimaryKey
    public int uid;


    @ColumnInfo(name = "trending")
    public Movie trending;

    public TrendingEntity(Movie trending)
    {
        this.trending = trending;
    }

    public int getUid()
    {
        return uid;
    }

    public Movie getTrending()
    {
        return trending;
    }
}
