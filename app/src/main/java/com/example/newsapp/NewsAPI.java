package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("top-headlines?apiKey=5ac5daaeb47e45a7856bf72bf207d2d1&searchIn=title,content&pageSize=100")
    Call<News> getHeadlines(@Query("country") String nationalCode, @Query("pageSize") String pageSize);

    @GET("everything?apiKey=5ac5daaeb47e45a7856bf72bf207d2d1&searchIn=title,content&sortBy=publishedAt&pageSize=100")
    Call<News> getNews(@Query("q") String query, @Query("language") String langCode);


}
