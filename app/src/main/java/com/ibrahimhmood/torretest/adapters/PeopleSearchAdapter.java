package com.ibrahimhmood.torretest.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrahimhmood.torretest.R;
import com.ibrahimhmood.torretest.net.ImageTask;
import com.ibrahimhmood.torretest.net.OnResponseReceivedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class PeopleSearchAdapter extends BaseAdapter
{
    public static final String SEARCH_TYPE_PERSON = "person";
    public static final String SEARCH_TYPE_OPPORTUNITY = "opportunity";
    protected Context context;
    protected JSONArray results;
    public PeopleSearchAdapter(Context context, JSONArray results)
    {
        this.context = context;
        this.results = results;
    }
    @Override
    public int getCount()
    {
        return this.results.length();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }


    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    public static class SearchHolder
    {
        protected ImageView profileIcon;
        protected TextView profileName;
        protected TextView profileID;

        public ImageView getProfileIcon()
        {
            return profileIcon;
        }

        public TextView getProfileName()
        {
            return profileName;
        }

        public TextView getProfileID()
        {
            return profileID;
        }

    }

    Drawable icon = null;
    public Drawable fromURL(String url, SearchHolder holder) throws IOException
    {
        ImageTask task = new ImageTask(context, url, holder);
        task.execute();
        return icon;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        //Inflater for inflating layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        SearchHolder holder = null;
        if(convertView == null)
        {
            //Set convert view
            convertView = (View) inflater.inflate(R.layout.people_search_result_view, viewGroup, false);
            //Create holder
            holder = new SearchHolder();
            //Set the holder profile photo id
            holder.profileIcon = (ImageView) convertView.findViewById(R.id.people_search_icon);
            //Set the holder profile name
            holder.profileName = (TextView) convertView.findViewById(R.id.people_search_name);
            //Set the profile id
            holder.profileID = (TextView) convertView.findViewById(R.id.people_search_id);
            //Set the tag of convert view to holder
            convertView.setTag(holder);
        }
        else
            //Get the holder
            holder = (SearchHolder) convertView.getTag();
        try
        {
            //Get the json object at i
            JSONObject object = this.results.getJSONObject(i);
            //Set icon
            holder.profileIcon.setImageDrawable(fromURL(object.getString("picture"), holder));
            //Set the profile name
            holder.profileName.setText(object.getString("name"));
            //Set the id
            holder.profileID.setText(object.getString("username"));
        } catch (JSONException | IOException e)
        {
            e.printStackTrace();
        }

        //Return the convert view
        return convertView;
    }
}
