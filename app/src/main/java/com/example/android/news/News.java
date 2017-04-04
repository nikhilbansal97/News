package com.example.android.news;


public class News {

    private String imageUrl;
    private String text;

    public News(String title , String image)
    {
        text = title;
        imageUrl = image;
    }
    public String getImage(){return imageUrl;}
    public String getTitle(){return text;}
}
