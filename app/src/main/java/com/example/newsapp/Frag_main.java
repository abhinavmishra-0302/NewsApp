package com.example.newsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Frag_main extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    TextView tvDate;
    TextView tvTemp;
    ImageView ivWeather;

    final int PERMISSION_CODE = 2;

    LocalDateTime time;
    DateTimeFormatter dtf;
    DateTimeFormatter dtfDate;

    ArrayList<ModelClass> news;

    ArrayList<Address> userAddressList;
    Address userAddress;
    Geocoder geocoder;
    Location location;

    public final String CHANNEL_ID = "1";
    public Frag_main() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_main, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        tvDate = view.findViewById(R.id.tvDate);
        tvTemp = view.findViewById(R.id.tvTemperature);
        ivWeather = view.findViewById(R.id.ivWeather);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            dtfDate = DateTimeFormatter.ofPattern("EEE, dd-MM-yyyy");
            time = LocalDateTime.now();
        }

        tvDate.setText(dtfDate.format(time));

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
        else {
            getLocation();
        }

        return view;
    }

    public void getHeadlines(String country){
        NewsAPI newsAPI = RetrofitNews.getClient().create(NewsAPI.class);
        Call<News> call = newsAPI.getHeadlines(country, "100");

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                news = new ArrayList<>();

                if(getView().findViewById(R.id.fragMain) != null) {
                    layoutManager = new LinearLayoutManager(getContext());
                }
                else if(getView().findViewById(R.id.fragMainLand) != null){
                    layoutManager = new GridLayoutManager(getContext(), 2);
                }

                myAdapter = new HeadlinesAdapter(getContext(), news);

                recyclerView.setAdapter(myAdapter);

                recyclerView.setLayoutManager(layoutManager);

                myAdapter.notifyDataSetChanged();

                for (int i = 0; i < response.body().getArticles().size(); i++){
                    String dateUnFormatted = response.body().getArticles().get(i).getPublishedAt();
                    String dateFormatted = dateUnFormatted.substring(5,10);
                    news.add(new ModelClass(response.body().getArticles().get(i).getTitle()
                            , response.body().getArticles().get(i).getUrlToImage()
                            ,response.body().getArticles().get(i).getUrl()
                            ,response.body().getArticles().get(i).getDescription()
                            ,dateFormatted));
                }
                sendNotification();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<WeatherMap> call1 = weatherAPI.getWeather(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

        call1.enqueue(new Callback<WeatherMap>() {
            @Override
            public void onResponse(Call<WeatherMap> call, Response<WeatherMap> response) {
                tvTemp.setText(response.body().getMain().getTemp() + "°C");
                String icon = response.body().getWeather().get(0).getIcon();

                Picasso.get().load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                        .placeholder(R.color.white)
                        .into(ivWeather);
            }

            @Override
            public void onFailure(Call<WeatherMap> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CODE && permissions.length > 0 && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }
        else {
            getHeadlines("in");
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocation(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        geocoder = new Geocoder(getContext());
        userAddressList = new ArrayList<>();

        try {
            userAddressList = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userAddress = userAddressList.get(0);
        getHeadlines(userAddress.getCountryCode().toLowerCase(Locale.ROOT));

    }

    public void sendNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 0);

        Intent i = new Intent(getContext().getApplicationContext(), Notification_Reciever.class);
        PendingIntent intent = PendingIntent.getBroadcast(getContext().getApplicationContext(), 100, i, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, intent);
    }
}