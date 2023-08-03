package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsStory extends AppCompatActivity {

    WebView webNewsStory;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_story);

        webNewsStory = findViewById(R.id.webNewsStory);
        ivBack = findViewById(R.id.ivBack);

        webNewsStory.getSettings().setLoadsImagesAutomatically(true);
        webNewsStory.getSettings().setJavaScriptEnabled(true);
        webNewsStory.getSettings().setLoadWithOverviewMode(true);
        webNewsStory.getSettings().setUseWideViewPort(true);
        webNewsStory.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webNewsStory.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("url");

        webNewsStory.loadUrl(url);

        ivBack.setOnClickListener(view -> {
            finish();
        });


    }

}