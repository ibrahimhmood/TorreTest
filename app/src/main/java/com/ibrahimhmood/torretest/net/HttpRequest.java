package com.ibrahimhmood.torretest.net;

import android.content.Context;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class HttpRequest
{
    public static String PEOPLE_SEARCH = "https://search.torre.co/people/_search/?";
    protected OnResponseReceivedListener onResponseReceivedListener;
    protected String contentType;
    public HttpRequest(OnResponseReceivedListener onResponseReceivedListener, String contentType)
    {
        //Set the response received listener
        this.onResponseReceivedListener = onResponseReceivedListener;
        //And the content type
        this.contentType = contentType;
    }

    public void post(String url, List<BasicNameValuePair> pairs)
    {
        //Create a post task
        PostTask postTask = new PostTask(this.onResponseReceivedListener, url, pairs.get(0).getValue(), this.contentType, pairs);
        //Execute the post task
        postTask.execute();
    }
}
