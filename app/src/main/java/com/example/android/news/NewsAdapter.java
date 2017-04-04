package com.example.android.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private Context mContext;
    private ArrayList<News> mList;

    public class NewsHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView text;

        public NewsHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.news_item_image);
            text = (TextView) itemView.findViewById(R.id.news_item_text);
        }
    }

    public NewsAdapter(Context context , ArrayList<News> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NewsHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(NewsHolder viewHolder, int i) {
        News currentNews = mList.get(i);
        Picasso.with(mContext).load(currentNews.getImage()).into(viewHolder.image);
        viewHolder.text.setText(currentNews.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
