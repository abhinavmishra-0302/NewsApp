package com.example.newsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NationalNews extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter myAdapter;

    LocalDateTime localDateTime;
    DateTimeFormatter dateTimeFormatterDate;
    DateTimeFormatter dateTimeFormatterTime;


    ArrayList<ModelClass> newsList;

    ArrayList<Address> userAddressList;
    Address userAddress;
    Geocoder geocoder;
    Location location;

    final int PERMISSION_CODE = 1;

    public NationalNews() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_national_news, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTimeFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dateTimeFormatterTime = DateTimeFormatter.ofPattern("00:mm:ss");
            localDateTime = LocalDateTime.now();
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
        else {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            geocoder = new Geocoder(getContext());
            try {
                userAddressList = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                userAddress = userAddressList.get(0);
                getNews(userAddress.getCountryName() + " local news"
                        , dateTimeFormatterDate.format(localDateTime)+"T"+dateTimeFormatterTime.format(localDateTime), "en");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public void getNews(String query, String time, String langCode){
        NewsAPI newsAPI = RetrofitNews.getClient().create(NewsAPI.class);
        Call<News> call = newsAPI.getNews(query, langCode);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                recyclerView = getView().findViewById(R.id.recyclerViewNews);

                if(getView().findViewById(R.id.fragNews) != null) {
                    layoutManager = new LinearLayoutManager(getContext());
                }
                else if(getView().findViewById(R.id.fragNewsLand) != null) {
                    layoutManager = new GridLayoutManager(getContext(), 2);
                }

                newsList = new ArrayList<>();
                recyclerView.setLayoutManager(layoutManager);

                for (int i = 0; i < response.body().getArticles().size(); i++){
                    String dateUnFormatted = response.body().getArticles().get(i).getPublishedAt();
                    String dateFormatted = dateUnFormatted.substring(5,10);
                    newsList.add(new ModelClass(response.body().getArticles().get(i).getTitle()
                            , response.body().getArticles().get(i).getUrlToImage()
                            , response.body().getArticles().get(i).getUrl()
                            , response.body().getArticles().get(i).getDescription()
                            , dateFormatted));
                }

                myAdapter = new NewsAdapter(getContext(), newsList);

                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_CODE && permissions.length > 0 && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            geocoder = new Geocoder(getContext());
            try {
                userAddressList = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                userAddress = userAddressList.get(0);
                getNews(userAddress.getCountryName()
                        , dateTimeFormatterDate.format(localDateTime)+"T"+dateTimeFormatterTime.format(localDateTime), "en");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            getNews("india news", dateTimeFormatterDate.format(localDateTime)+"T"+dateTimeFormatterTime.format(localDateTime), "en");
        }
    }



}