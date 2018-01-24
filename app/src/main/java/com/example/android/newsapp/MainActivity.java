package com.example.android.newsapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable backgroundAnimation;
    RelativeLayout welcomeLayout;
    TextView continueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeLayout = findViewById(R.id.welcome_layout);
        backgroundAnimation =(AnimationDrawable) welcomeLayout.getBackground();

        backgroundAnimation.setEnterFadeDuration(6000);
        backgroundAnimation.setExitFadeDuration(3000);

        continueTextView = (TextView) findViewById(R.id.continue_text);

        continueTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent newsIntent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(newsIntent);
            }

        });
    }

    @Override
    protected void onResume(){

        super.onResume();
        backgroundAnimation.start();
    }
}
