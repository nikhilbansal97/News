package com.example.android.news;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIKHIL on 29-03-2017.
 */

public class QueryUtils {

    private static String QUERY_URL = "https://content.guardianapis.com/search?&section=world&page-size=30&show-fields=thumbnail,trailText&show-tags=contributor&api-key=6de1a8cd-9a9d-4016-8273-26de99416430";


    private QueryUtils(){

    }

    public static ArrayList<News> getNews(){
        ArrayList<News> news_list = null;
        URL url = createUrl(QUERY_URL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        news_list = getNewsList(jsonResponse);
        return news_list;
    }

    private static ArrayList<News> getNewsList(String responseString){
        ArrayList<News> list = new ArrayList<>();
        try {
            JSONObject base = new JSONObject(responseString);
            JSONObject response = base.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for(int i=0;i < results.length();i++)
            {
                JSONObject news_object = results.getJSONObject(i);
                String title = news_object.getString("webTitle");
                JSONObject fields = news_object.getJSONObject("fields");
                String image_url = fields.getString("thumbnail");
                list.add(new News(title,image_url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static URL createUrl(String url){
        URL query_url = null;
        try {
            query_url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return query_url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        HttpURLConnection connection = null;
        InputStream stream = null;
        String response = "";
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.connect();
            if(connection.getResponseCode() == 200)
            {
                stream = connection.getInputStream();
                response = getJsonResponse(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (stream != null) {
                stream.close();
            }
        }

        return response;
    }

    private static String getJsonResponse(InputStream stream){

        StringBuilder output = new StringBuilder();
        if(stream != null)
        {
            InputStreamReader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader(reader);
            try {
                String line = buffer.readLine();
                while(line != null)
                {
                    output.append(line);
                    line = buffer.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }

}
