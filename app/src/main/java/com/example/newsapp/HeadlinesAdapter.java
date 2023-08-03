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

import java.util.ArrayList;

public class HeadlinesAdapter extends RecyclerView.Adapter<HeadlinesAdapter.ViewHolder>{
    ArrayList<ModelClass> news;
    Context context;

    public HeadlinesAdapter(Context context, ArrayList<ModelClass> news){
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public HeadlinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadlinesAdapter.ViewHolder holder, int position) {

        Picasso.get().load(news.get(position).getUrlToImage())
                .into(holder.ivImageNews);

        holder.tvTitle.setText(news.get(position).getTitle());

        holder.tvSource.setText("Published on " + news.get(position).getSource());

        holder.newsItem.setOnClickListener(view -> {
            Intent intent = new Intent(context.getApplicationContext(), NewsStory.class);
            intent.putExtra("url", news.get(position).getUrl());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvSource;
        ImageView ivImageNews;
        ConstraintLayout newsItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImageNews = itemView.findViewById(R.id.ivImageNews);
            newsItem = itemView.findViewById(R.id.newsItem);
            tvSource = itemView.findViewById(R.id.tvSource);


        }
    }
}
