package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{
    private ArrayList<CategoriesModel> categoriesModelArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoriesModel> categoriesModelArrayList, Context context, CategoryClickInterface categoryClickInterface)
    {
        this.categoriesModelArrayList = categoriesModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_layout, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        CategoriesModel model = categoriesModelArrayList.get(position);
        Picasso.get().load(model.getCategoryImageUrl()).into(holder.categoryImg);
        holder.categoryName.setText(model.getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return categoriesModelArrayList.size();
    }

    public interface CategoryClickInterface
    {
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView categoryImg;
        private TextView categoryName;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.category_img);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
