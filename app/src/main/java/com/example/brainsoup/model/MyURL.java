package com.example.brainsoup.model;

public class MyURL {
    private static String BASE_URL="http://192.168.1.70:5000/";
    private static String IMAGE_URL="http://192.168.1.70:5000/static/";

    public static String getBASE_URL()
    {
        return BASE_URL;
    }
    public static String getImageUrl()
    {
        return IMAGE_URL;
    }
}
