package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=f15b8a9e05ad1e6771743010037d83da&units=metric")
    Call<WeatherMap> getWeather(@Query("lat") String latitude, @Query("lon") String longitude);
}
