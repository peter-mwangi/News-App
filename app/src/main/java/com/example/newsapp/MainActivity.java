package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView categoriesRV, newsRV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        65f35bc9ef764efe9326fe1c33912289

//        Initializing view
        categoriesRV = findViewById(R.id.categories_recyclerview);
        newsRV = findViewById(R.id.main_recyclerview);
        progressBar = findViewById(R.id.progress_bar);
    }
}