package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>
{
    private ArrayList<Article> articleArrayList;
    private Context context;

    public NewsAdapter(ArrayList<Article> articleArrayList, Context context)
    {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position)
    {
        Article article = articleArrayList.get(position);
        holder.newsHeading.setText(article.getTitle());
        holder.subHeading.setText(article.getDescription());
        Picasso.get().load(article.getUrlToImage()).into(holder.newsBanner);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent newsIntent = new Intent(context, NewsDetailsActivity.class);
                newsIntent.putExtra("heading", article.getTitle());
                newsIntent.putExtra("subHeading", article.getDescription());
                newsIntent.putExtra("content", article.getContent());
                newsIntent.putExtra("imageUrl", article.getUrlToImage());
                newsIntent.putExtra("url", article.getUrl());
                context.startActivity(newsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView newsBanner;
        private TextView newsHeading, subHeading;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            newsBanner = itemView.findViewById(R.id.news_banner);
            newsHeading = itemView.findViewById(R.id.news_heading);
            subHeading = itemView.findViewById(R.id.news_subheading);
        }
    }
}
