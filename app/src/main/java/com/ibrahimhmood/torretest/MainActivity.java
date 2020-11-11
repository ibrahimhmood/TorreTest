package com.ibrahimhmood.torretest;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ibrahimhmood.torretest.net.HttpRequest;
import com.ibrahimhmood.torretest.net.OnResponseReceivedListener;

public class MainActivity extends AppCompatActivity
{
    protected EditText search;
    protected LinearLayout main;
    protected TextView logo;
    protected RelativeLayout searchBox;
    boolean animatedUp = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the main page
        main = findViewById(R.id.main_page);
        //Get teh search box
        search = findViewById(R.id.search);

        //listen for touches on search box
        search.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                //Move up
                moveUpward();
                return false;
            }
        });

        //listen for the same on main page
        main.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                //Move up
                moveDownward();
                return false;
            }
        });
    }

    public void moveUpward()
    {
        //Get the searchbox containing search editor
        searchBox = findViewById(R.id.search_box);
        //Get the logo
        logo = findViewById(R.id.logo);
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
            //No more focus on search
            search.clearFocus();
            //Hide the keyboard
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(search.getWindowToken(), 0);
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