package com.example.android.newsapp;

/**
 * Created by Addi_ola on 17/01/2018.
 */

public class News {

    //News Url
    private String mUrl;

    //News headline
    private String mHeadLine;
    /**
     * Image for visual understanding of word
     */
//    private int mImageResourceId = NO_IMAGE_PROVIDED;
//
//    private static final int NO_IMAGE_PROVIDED = -1;

    private String mImageUrl;

    //Constructor
    public News(String url, String headLine, String imgUrl){
        mUrl = url;
        mHeadLine = headLine;
        mImageUrl = imgUrl;
    }

    //Getter Methods
    public String getUrl(){
        return mUrl;
    }

    public String getHeadLine(){
        return mHeadLine;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

}
