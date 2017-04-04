package com.example.android.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView news_list_view;
    private NewsAdapter myAdapter;
    private ArrayList<News> news_list;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news_list_view = (RecyclerView) findViewById(R.id.list_view);
        manager = new LinearLayoutManager(getApplicationContext());
        news_list_view.setLayoutManager(manager);
        news_list_view.setItemAnimator(new DefaultItemAnimator());
        news_list = new ArrayList<>();
        myAdapter = new NewsAdapter(this, news_list);
        news_list_view.setAdapter(myAdapter);
        new NewsAsyncTask().execute();
    }

    private class NewsAsyncTask extends AsyncTask<Void,Void,ArrayList<News>>{

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            return QueryUtils.getNews();
        }

        @Override
        protected void onPostExecute(ArrayList<News> list) {
            news_list = list;
            myAdapter.notifyDataSetChanged();
            super.onPostExecute(list);
        }
    }

}
