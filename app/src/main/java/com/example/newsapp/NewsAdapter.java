package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<ModelClass> newsList;
    Context context;

    public NewsAdapter(Context context, List<ModelClass> newsList){
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {

        holder.tvTitle.setText(newsList.get(position).getTitle());

        Picasso.get().load(newsList.get(position).getUrlToImage())
                .into(holder.ivImageNews);


        holder.newsItem.setOnClickListener(view -> {
            Intent intent = new Intent(context.getApplicationContext(), NewsStory.class);
            intent.putExtra("url", newsList.get(position).getUrl());
            context.startActivity(intent);
        });

        holder.tvSource.setText("Published on "+newsList.get(position).getSource());

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout newsItem;
        TextView tvTitle, tvSource;
        ImageView ivImageNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsItem = itemView.findViewById(R.id.newsItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImageNews = itemView.findViewById(R.id.ivImageNews);
            tvSource = itemView.findViewById(R.id.tvSource);
        }
    }
}
