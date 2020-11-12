package com.ibrahimhmood.torretest;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ibrahimhmood.torretest.adapters.PeopleSearchAdapter;
import com.ibrahimhmood.torretest.net.HttpRequest;
import com.ibrahimhmood.torretest.net.OnResponseReceivedListener;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    protected EditText search;
    protected LinearLayout main;
    protected TextView logo;
    protected RelativeLayout searchBox;
    protected GridView searchResultsList;
    protected HttpRequest peopleSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the logo
        logo = findViewById(R.id.logo);
        //Get the searchbox containing search editor
        searchBox = findViewById(R.id.search_box);
        //Get the main page
        main = findViewById(R.id.main_page);
        //Get teh search box
        search = findViewById(R.id.search);
        //Get the results pager
        searchResultsList = findViewById(R.id.results_list);
        //Send the text over to the website for people search
        peopleSearch = new HttpRequest(new OnResponseReceivedListener()
        {
            @Override
            public void onResponseReceived(Object response)
            {
                try
                {
                    //Parse the response as JSON
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray result = jsonResponse.getJSONArray("results");
                    searchResultsList.setAdapter(new PeopleSearchAdapter(getApplicationContext(), result));
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onErrorReceived(String error)
            {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        }, "application/json");
        //listen for touches on search box
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //Create a name value pair list
                List<BasicNameValuePair> pairs = new ArrayList<>();
                //Add query to list
                pairs.add(new BasicNameValuePair("name", editable.toString()));
                //Search for people
                peopleSearch.post(HttpRequest.PEOPLE_SEARCH, pairs);
                //Check if editable is nothing
                if(editable.toString().isEmpty())
                    //Hide the results
                    searchResultsList.setVisibility(View.GONE);
                else
                {
                    //Show the results list
                    searchResultsList.setVisibility(View.VISIBLE);
                }
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
            {
                //Create a name value pair list
                List<BasicNameValuePair> pairs = new ArrayList<>();
                //Add query to list
                pairs.add(new BasicNameValuePair("name", search.getText().toString()));
                //Search for people
                peopleSearch.post(HttpRequest.PEOPLE_SEARCH, pairs);
                //Check if editable is nothing
                if(search.getText().toString().isEmpty())
                    //Hide the results
                    searchResultsList.setVisibility(View.GONE);
                else
                {
                    //Show the results list
                    searchResultsList.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }
}