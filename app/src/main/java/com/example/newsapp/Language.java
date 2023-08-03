package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Language extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLang;
    Button btSetLang;

    String language;

    public static final String FILENAME = "com.example.newsapp.Categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        spinnerLang = findViewById(R.id.spinnerLang);
        btSetLang = findViewById(R.id.btSetLang);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Language.this, R.array.languages, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLang.setAdapter(adapter);



        btSetLang.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getSharedPreferences(FILENAME, MODE_PRIVATE).edit();
            editor.putString("langpref", language);
            editor.apply();

            Intent intent = new Intent(Language.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        language = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}