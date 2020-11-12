package com.ibrahimhmood.torretest.net;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest
{
    public static String USER_BIO_URL = "https://torre.bio/bios/%s";
    public static String JOB_INFORMATION_URL = "https://torre.co/api/opportunities/%s";
    public static String JOB_OPPORTUNITIES_SEARCH = "https://search.torre.co/opportunities/_search/?q=job:%s";
    public static String PEOPLE_SEARCH = "https://search.torre.co/people/_search/\\?size=3&lang=en&aggregate=false&offset=0";
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
            String params = url.split("\\?")[1];
            url = url + "?" + Uri.encode(params);
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
        protected List<String> keys = new ArrayList<>();
        protected List<String> values = new ArrayList<>();
        public Post(Context context, OnResponseReceivedListener onResponseReceivedListener)
        {
            //Setting the context
            HttpRequest.context = context;
            //Setting the request queue
            HttpRequest.requestQueue = Volley.newRequestQueue(context);
            //Set on response received
            HttpRequest.onResponseReceivedListener = onResponseReceivedListener;
        }

        public void post(String url, final Map<String, String> data) throws JSONException
        {
            String params = "";
            url = url + "?" + Uri.encode(params);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("q", "Android Volley Demo");
            final String requestBody = jsonBody.toString();
            //Create a string request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
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
                    HttpRequest.onResponseReceivedListener.onErrorReceived(error);
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json;charset=utf-8");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    return data;
                }
            };
            HttpRequest.requestQueue.add(request);
            HttpRequest.requestQueue.start();
        }

    }
}
