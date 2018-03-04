package com.example.whazzup.rest;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whazzup.R;
import com.example.whazzup.WhazzupApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomResultActivity extends AppCompatActivity {

    private int ACTION_TYPE;
    ContentService service;

    private String giphyUrl;
    private RelativeLayout resultLayout, senderInfo;
    private LinearLayout layout;
    private ProgressBar pb;
    private ImageView icon;
    private TextView senderName, source;
    private TextView resultText;
    private Button btn_next;

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
        setContentView(R.layout.activity_random_result);

        ACTION_TYPE = getIntent().getIntExtra("button_id", -1);

        senderInfo = (RelativeLayout) findViewById(R.id.sender_info);
        pb = (ProgressBar) findViewById(R.id.pb_request);
        resultText = (TextView) findViewById(R.id.resultTextView);
        icon = (ImageView) findViewById(R.id.icon);

        senderName = (TextView) findViewById(R.id.name);
        source = (TextView) findViewById(R.id.source);


        service = WhazzupApp.getService();

        btn_next = (Button) findViewById(R.id.button_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadContent();
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        layout = (LinearLayout) findViewById(R.id.content_layout);

        resultLayout = (RelativeLayout) findViewById(R.id.result_layout);
        LinearLayout.LayoutParams resultParams =
                new LinearLayout.LayoutParams(resultLayout.getLayoutParams());
        resultParams.setMargins(metrics.widthPixels/8, 0, metrics.widthPixels/8, 0);
        resultLayout.setLayoutParams(resultParams);

        LinearLayout.LayoutParams btnParams =
                new LinearLayout.LayoutParams(btn_next.getLayoutParams());
        btnParams.topMargin = getResources().getDimensionPixelOffset(R.dimen.btn_next_margin_vertical);
        btnParams.setMarginEnd(metrics.widthPixels/8);
        btnParams.gravity = Gravity.END;
        btn_next.setLayoutParams(btnParams);


//        RelativeLayout.LayoutParams layoutParams =
//                new RelativeLayout.LayoutParams(senderInfo.getLayoutParams());
//        layoutParams.width = metrics.widthPixels *3/4;
//        layoutParams.addRule(RelativeLayout.BELOW, R.id.sender_info);
//        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        layout.setLayoutParams(layoutParams);

        loadContent();
    }

    public void loadContent() {
        if (ACTION_TYPE == R.id.button_twitter
                || ACTION_TYPE == R.id.button_forismatic)
            source.setVisibility(View.GONE);

        resultText.setVisibility(View.GONE);
        senderInfo.setVisibility(View.INVISIBLE);
        btn_next.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);


        Call call = null;

        switch(ACTION_TYPE) {
            case R.id.button_twitter:{
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.twitter));
                call = service.loadTwitter(new ObjectRequest.Twitter(1, "Minsk"));
                break;
            }

            case R.id.button_forismatic:{
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.forismatic));
                call = service.loadForismatic(new ObjectRequestSimple(5));
                break;
            }

            case R.id.button_chuck_norris:{
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.chuck));
                call = service.loadChuckNorris(new ObjectRequestSimple(1));
                break;
            }

            case R.id.button_yo_momma:{
                icon.setImageDrawable(getResources().getDrawable(R.mipmap.yo));
                call = service.loadJoke(new ObjectRequestSimple(1));
                break;
            }

            case R.id.button_advice:{
                break;
            }
            default:
                return;
        }

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() == null) {
                    Toast.makeText(RandomResultActivity.this, "HEADERS: " + response.headers().toMultimap().toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(RandomResultActivity.this, "MESSAGE: " + response.raw().message(), Toast.LENGTH_SHORT).show();
                    btn_next.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);

                } else {

                    switch (ACTION_TYPE) {
                        case R.id.button_forismatic: {
                            ObjectResponseForismatic result = (ObjectResponseForismatic)response.body();
                            senderName.setText(result.who);
                            resultText.setText(result.what);
                            source.setText("Forismatic");

                            source.setVisibility(View.VISIBLE);
                            break;
                        }

                        case R.id.button_twitter: {
                            ObjectResponseTwitter result = (ObjectResponseTwitter)response.body();
                            senderName.setText(result.name);
                            resultText.setText(result.text);
                            source.setText("Twitter");
                            //source.setText(result.profileUrl);

                            source.setVisibility(View.VISIBLE);
                            break;
                        }

                        default: {
                            String result = (String) response.body();
                            resultText.setText(result);
                        }
                    }

                    senderInfo.setVisibility(View.VISIBLE);
                    resultText.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(RandomResultActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
