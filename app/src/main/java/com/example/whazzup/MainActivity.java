package com.example.whazzup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.whazzup.rest.RandomResultActivity;
import com.example.whazzup.rest.RequestActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout.LayoutParams buttonLayoutParams;
    private Button twitter;
    private Button forismatic;
    private Button giphy;
    private Button wiki;
    private Button advice;
    private Button chuckNorris;
    private Button yoMommaJoke;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21) {
            overridePendingTransition(R.anim.enter_left_in, R.anim.exit_right_out);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener buttonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;

                switch(v.getId()) {
                    case R.id.button_twitter:{
                        intent = new Intent(MainActivity.this, RandomResultActivity.class);
                        intent.putExtra("button_id", R.id.button_twitter);
                        break;
                    }

                    case R.id.button_forismatic:{
                        intent = new Intent(MainActivity.this, RandomResultActivity.class);
                        intent.putExtra("button_id", R.id.button_forismatic);
                        break;
                    }

                    case R.id.button_giphy:{
                        intent = new Intent(MainActivity.this, RequestActivity.class);
                        intent.putExtra("button_id", R.id.button_giphy);
                        break;
                    }

                    case R.id.button_chuck_norris:{
                        intent = new Intent(MainActivity.this, RandomResultActivity.class);
                        intent.putExtra("button_id", R.id.button_chuck_norris);
                        break;
                    }

                    case R.id.button_wikipedia:{
                        intent = new Intent(MainActivity.this, RequestActivity.class);
                        intent.putExtra("button_id", R.id.button_wikipedia);
                        break;
                    }

                    case R.id.button_yo_momma:{
                        intent = new Intent(MainActivity.this, RandomResultActivity.class);
                        intent.putExtra("button_id", R.id.button_yo_momma);
                        break;
                    }

                    case R.id.button_advice:{
                        intent = new Intent(MainActivity.this, RequestActivity.class);
                        intent.putExtra("button_id", R.id.button_advice);
                        break;
                    }
                    default:
                        return;
                }

                startActivity(intent);
                if (Build.VERSION.SDK_INT >= 21) {
                    overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
                }
            }
        };

        buttonLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        buttonLayoutParams.weight = 1;


        twitter = (Button) findViewById(R.id.button_twitter);
        forismatic = (Button) findViewById(R.id.button_forismatic);
        giphy = (Button) findViewById(R.id.button_giphy);
        wiki = (Button) findViewById(R.id.button_wikipedia);
        advice = (Button) findViewById(R.id.button_advice);
        chuckNorris = (Button) findViewById(R.id.button_chuck_norris);
        yoMommaJoke = (Button) findViewById(R.id.button_yo_momma);

        twitter.setOnClickListener(buttonsListener);
        forismatic.setOnClickListener(buttonsListener);
        giphy.setOnClickListener(buttonsListener);
        wiki.setOnClickListener(buttonsListener);
        advice.setOnClickListener(buttonsListener);
        chuckNorris.setOnClickListener(buttonsListener);
        yoMommaJoke.setOnClickListener(buttonsListener);

        twitter.post(new Runnable() {
            @Override
            public void run() {
                buttonLayoutParams.bottomMargin = twitter.getHeight() / 3;
                twitter.setLayoutParams(buttonLayoutParams);
                forismatic.setLayoutParams(buttonLayoutParams);
                giphy.setLayoutParams(buttonLayoutParams);
                wiki.setLayoutParams(buttonLayoutParams);
                advice.setLayoutParams(buttonLayoutParams);
                chuckNorris.setLayoutParams(buttonLayoutParams);
                yoMommaJoke.setLayoutParams(buttonLayoutParams);
            }
        });
    }
}