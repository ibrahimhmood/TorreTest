package com.ibrahimhmood.torretest.net;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.ibrahimhmood.torretest.R;
import com.ibrahimhmood.torretest.adapters.PeopleSearchAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageTask extends AsyncTask<Drawable, Void, Drawable>
{
    protected Context context;
    private String url;
    private PeopleSearchAdapter.SearchHolder holder;
    public ImageTask(Context context, String url, PeopleSearchAdapter.SearchHolder holder)
    {
        this.context = context;
        this.url = url;
        this.holder = holder;
    }
    @Override
    protected Drawable doInBackground(Drawable... inputStreams)
    {

        //Connect
        try
        {
            //Make a connection
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();
            //Read
            RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), connection.getInputStream());
            bitmapDrawable.setCornerRadius(80.f);
            //Return
            return bitmapDrawable;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable bitmapDrawable)
    {
        if(bitmapDrawable == null)
            bitmapDrawable = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_baseline_person_24));
        //Set the profile icon
        holder.getProfileIcon().setImageDrawable(bitmapDrawable);
        super.onPostExecute(bitmapDrawable);
    }
}
