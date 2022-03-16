package com.ash.berfilm.RoomDb.Converter;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ash.berfilm.Models.MovieModel.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TrendingConverter
{

    @TypeConverter
    public String toJson(Movie trending)
    {
        if(trending == null)
        {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Movie>() {}.getType();
        String json = gson.toJson(trending,type);

        return json;
    }

    @TypeConverter
    public Movie toDataClass(String trending)
    {
        if(trending == null)
        {
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Movie>() {}.getType();
        Movie moviesClass = gson.fromJson(trending,type);

        return moviesClass;


    }




}
