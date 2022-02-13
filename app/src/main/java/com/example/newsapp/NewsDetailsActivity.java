package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity
{
    private String title, desc, content, imageBanner, externalUrl;

    private ImageView newsBanner;
    private TextView heading, subHeading, newsInfo;
    private Button readMoreBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        title = getIntent().getExtras().get("heading").toString();
        desc = getIntent().getExtras().get("subHeading").toString();
        content = getIntent().getExtras().get("content").toString();
        imageBanner = getIntent().getExtras().get("imageUrl").toString();
        externalUrl = getIntent().getExtras().get("url").toString();

        newsBanner = findViewById(R.id.news_detail_banner);
        heading = findViewById(R.id.news_detail_heading);
        subHeading = findViewById(R.id.news_detail_sub_heading);
        newsInfo = findViewById(R.id.news_detail_content);
        readMoreBtn = findViewById(R.id.readmore_btn);

        Picasso.get().load(imageBanner).into(newsBanner);
        heading.setText(title);
        subHeading.setText(desc);
        newsInfo.setText(content);

        readMoreBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(externalUrl));
                startActivity(intent);
            }
        });

    }
}