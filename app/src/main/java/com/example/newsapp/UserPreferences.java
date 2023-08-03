package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class UserPreferences extends AppCompatActivity {

    CheckBox chNationalNews, chWorld, chFinance, chScience, chSports, chEntertainment;
    Button btSubmit;

    public static final String FILENAME = "com.example.newsapp.Categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        chNationalNews = findViewById(R.id.chNationalNews);
        chWorld = findViewById(R.id.chWorld);
        chEntertainment = findViewById(R.id.chEntertainment);
        chFinance = findViewById(R.id.chFinance);
        chScience = findViewById(R.id.chScience);
        chSports = findViewById(R.id.chSports);
        btSubmit = findViewById(R.id.btSubmit);

        btSubmit.setOnClickListener(view -> {
            SharedPreferences.Editor prefs = getSharedPreferences(FILENAME, MODE_PRIVATE).edit();

            if(chNationalNews.isChecked()){
                prefs.putBoolean("catNationalNews", true);
            }
            if(chWorld.isChecked()){
                prefs.putBoolean("catWorld", true);
            }
            if(chEntertainment.isChecked()){
                prefs.putBoolean("catEntertainment", true);
            }
            if(chFinance.isChecked()){
                prefs.putBoolean("catFinance", true);
            }
            if(chScience.isChecked()){
                prefs.putBoolean("catScience", true);
            }
            if(chSports.isChecked()){
                prefs.putBoolean("catSports", true);
            }

            prefs.putBoolean("regStatus", true);
            prefs.commit();

            Intent intent = new Intent(UserPreferences.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*SharedPreferences preferences = getSharedPreferences(FILENAME, MODE_PRIVATE);
        boolean regStatus = preferences.getBoolean("regStatus", false);

        if(regStatus){
            Intent intent = new Intent(UserPreferences.this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/
    }
}