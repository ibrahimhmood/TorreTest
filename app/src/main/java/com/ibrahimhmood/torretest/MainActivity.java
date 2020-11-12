package com.ibrahimhmood.torretest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.hardware.input.InputManager;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.ibrahimhmood.torretest.net.HttpRequest;
import com.ibrahimhmood.torretest.net.OnResponseReceivedListener;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    protected EditText search;
    protected LinearLayout main;
    protected TextView logo;
    protected RelativeLayout searchBox;
    protected ListView searchResultsList;
    boolean animatedUp = false;
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
            }
        });
        Map<String, String> data = new HashMap<>();
        data.put("q", "Hi");
        try
        {
            new HttpRequest.Post(this, new OnResponseReceivedListener()
            {
                @Override
                public void onResponseReceived(Object response)
                {
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onErrorReceived(VolleyError error)
                {
                    new AlertDialog.Builder(MainActivity.this).setMessage(new String(error.networkResponse.data)).create().show();
                }
            })
                    .post(String.format(HttpRequest.PEOPLE_SEARCH, "Ibrahim"), data);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void moveUpward()
    {
        //Load the animation
        Animation toTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drag_up);
        //Animate the logo up
        logo.startAnimation(toTop);
        //Animate the search box up
        searchBox.startAnimation(toTop);
        //We have animated upwards
        animatedUp = true;
    }

    public void moveDownward()
    {

        //Check if we have already animated up
        if(animatedUp)
        {
            //Get the search box
            searchBox = findViewById(R.id.search_box);
            //Get the logo
            logo = findViewById(R.id.logo);
            //Load the downward animation
            Animation toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);
            //Animate the search box first
            searchBox.startAnimation(toBottom);
            //Then the logo
            logo.startAnimation(toBottom);
            //And reset animatedUp
            animatedUp = false;
        }
    }
}