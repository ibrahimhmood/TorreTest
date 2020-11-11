package com.ibrahimhmood.torretest.net;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class HttpRequest
{
    protected static Context context;
    protected static RequestQueue requestQueue;
    protected static OnResponseReceivedListener onResponseReceivedListener;
    public static class Get
    {
        protected String response;
        public Get(Context context, OnResponseReceivedListener onResponseReceivedListener)
        {
            //Setting the context
            HttpRequest.context = context;
            //Set the response listener
            HttpRequest.onResponseReceivedListener = onResponseReceivedListener;
            //Setting the request queue
            HttpRequest.requestQueue = Volley.newRequestQueue(context);
        }

        public void get(String url)
        {
            //Create a string request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            HttpRequest.onResponseReceivedListener.onResponseReceived(response);
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(context, "An error occurred, please try again. " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            HttpRequest.requestQueue.add(request);
        }

        public String getResponse()
        {
            return Get.this.response;
        }
    }

    public static class Post
    {
        protected String response;
        public Post(Context context, OnResponseReceivedListener onResponseReceivedListener)
        {
            //Setting the context
            HttpRequest.context = context;
            //Setting the request queue
            HttpRequest.requestQueue = Volley.newRequestQueue(context);
            //Set on response received
            HttpRequest.onResponseReceivedListener = onResponseReceivedListener;
        }

        public void post(String url, final Map<String, String> data)
        {
            //Create a string request
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    //We have a response-ibility (haha)
                    HttpRequest.onResponseReceivedListener.onResponseReceived(response);
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(context, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    //Return passed map
                    return data;
                }
            };
            //Post the data
            HttpRequest.requestQueue.add(request);
        }

        public String getResponse()
        {
            return Post.this.response;
        }
    }
}
