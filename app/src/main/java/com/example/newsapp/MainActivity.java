package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface
{
    private RecyclerView categoriesRV, newsRV;
    private ProgressBar progressBar;

    private ArrayList<CategoriesModel> categoriesModelArrayList;
    private ArrayList<Article> articleArrayList;

    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

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

        categoriesModelArrayList = new ArrayList<>();
        articleArrayList = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(categoriesModelArrayList, this, this::onCategoryClick);
        newsAdapter = new NewsAdapter(articleArrayList, this);

        newsRV.setAdapter(newsAdapter);

        categoriesRV.setAdapter(categoryAdapter);

        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();
    }

    private void getCategories()
    {
        categoriesModelArrayList.add(new CategoriesModel("All","https://images.unsplash.com/photo-1534848812-17d9c573d7d1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1034&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Science","https://images.unsplash.com/photo-1532094349884-543bc11b234d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Business","https://images.unsplash.com/photo-1516321318423-f06f85e504b3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Health","https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Entertainment","https://images.unsplash.com/photo-1567593810070-7a3d471af022?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=871&q=80"));
        categoriesModelArrayList.add(new CategoriesModel("Sports","https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoriesModelArrayList.add(new CategoriesModel("General","https://images.unsplash.com/photo-1487088678257-3a541e6e3922?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"));

        categoryAdapter.notifyDataSetChanged();
    }
    private void getNews(String category)
    {
        progressBar.setVisibility(View.VISIBLE);
        articleArrayList.clear();
        String categoryURL ="https://newsapi.org/v2/top-headlines?country=us&category="+category+"&apiKey=65f35bc9ef764efe9326fe1c33912289";
        String newsUrl = "https://newsapi.org/v2/top-headlines?country=us&excludeDomains=stackoverflow.com&sortBy=publisheAt&language=en&apiKey=65f35bc9ef764efe9326fe1c33912289";

        String BASE_URL = "https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;

        if (category.equals("All"))
        {
            call = retrofitAPI.getAllNews(newsUrl);
        }
        else
        {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response)
            {
                NewsModel model = response.body();
                progressBar.setVisibility(View.GONE);

                ArrayList<Article> articles = model.getArticles();
                for (int i=0; i<articles.size(); i++)
                {
                    articleArrayList.add(new Article(articles.get(i).getTitle(), articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent()));
                }

                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Failed To Fetch. Try Again Later", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onCategoryClick(int position)
    {
        String category = categoriesModelArrayList.get(position).getCategory();
        getNews(category);
    }
}