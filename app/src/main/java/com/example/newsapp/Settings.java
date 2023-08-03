package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Settings extends Fragment {

    TextView tvChangeCategories, tvChangeLanguage;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);

        tvChangeCategories = view.findViewById(R.id.tvChangeCategories);
        tvChangeLanguage = view.findViewById(R.id.tvChangeLanguage);

        tvChangeCategories.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), UserPreferences.class);
            startActivity(intent);
        });

        tvChangeLanguage.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), Language.class);
            startActivity(intent);
        });


        return view;
    }
}