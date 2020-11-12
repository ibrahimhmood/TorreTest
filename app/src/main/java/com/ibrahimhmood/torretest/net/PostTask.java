package com.ibrahimhmood.torretest.net;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class PostTask extends AsyncTask<String, Void, String>
{
    protected OnResponseReceivedListener onResponseReceivedListener;
    protected String url;
    protected String contentType;
    protected String query;
    protected List<BasicNameValuePair> pairs;

    public PostTask(OnResponseReceivedListener onResponseReceivedListener, String url, String query, String contentType, List<BasicNameValuePair> pairs)
    {
        this.onResponseReceivedListener = onResponseReceivedListener;
        this.url = url;
        this.contentType = contentType;
        this.query = query;
        this.pairs = pairs;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        try
        {
            //Create a default client
            HttpClient client = HttpClientBuilder.create().build();
            //Now create HttpPost
            HttpPost post = new HttpPost(this.url);
            //Accept json
            post.setHeader(HttpHeaders.ACCEPT, this.contentType);
            //Set content type
            post.setHeader(HttpHeaders.CONTENT_TYPE, this.contentType);
            //Set the post's entity
            post.setEntity(new PostEntity(this.pairs, this.contentType));
            //Execute the pairs and post
            HttpEntity entity = client.execute(post).getEntity();
            //Read the content into this stream
            BufferedInputStream inputStream = new BufferedInputStream(entity.getContent());
            //Buffer for reading
            byte[] buffer = new byte[90096];
            //Read
            inputStream.read(buffer);
            //Get results as string
            String results = new String(buffer);
            //Load into JSON Object
            JSONObject object = new JSONObject(results);
            //Get first result array
            JSONArray jsonResults = object.getJSONArray("results");
            //Go through all names
            for(int idx = 0; idx < jsonResults.length(); idx++)
            {
                //Get object at idx
                JSONObject personObject = jsonResults.getJSONObject(idx);
                //Get name
                if(!personObject.getString("name").contains(this.query))
                {
                    //Remove this person object
                    jsonResults.remove(idx);
                }
            }
            //Return buffer
            return object.toString();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return "An unknown error occurred";
    }

    @Override
    protected void onPostExecute(String response)
    {
        if(!response.equals("An unknown error occurred"))
        {
            this.onResponseReceivedListener.onResponseReceived(response);
        }
        else
            this.onResponseReceivedListener.onErrorReceived(response);
        super.onPostExecute(response);
    }
}
