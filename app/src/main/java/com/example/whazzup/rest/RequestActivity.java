package com.example.whazzup.rest;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.whazzup.R;
import com.example.whazzup.WhazzupApp;
import com.felipecsl.gifimageview.library.GifImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestActivity extends AppCompatActivity {

    private final int SEARCHBAR_MOVEMENT_DURATION_MS = 700;
    private final int MOVEMENT_DURATION_MS = 1000;
    private static final String baseUrl = "https://expasoft.com:8462/";
    private SearchView searchView;
    private ProgressBar pb;
    private LinearLayout categories;
    private TextView featured;
    private GifImageView resultImage;
    private RelativeLayout article;
    private TextView articleTitle, articleText;


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
        setContentView(R.layout.activity_request);

        searchView = (SearchView) findViewById(R.id.search);
        categories = (LinearLayout) findViewById(R.id.categories);
        featured = (TextView) findViewById(R.id.featured);

        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);

        View searchPlateView = searchView.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.WHITE);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pb.setVisibility(View.VISIBLE);

                //TODO: request
                switch (getIntent().getIntExtra("button_id", -1)) {
                    case R.id.button_giphy:
                    {
                        requestGif(query);
                        break;
                    }
                    case R.id.button_wikipedia:
                    {
                        requestWiki(query);
                        break;
                    }
                    case R.id.button_advice:
                    {
                        requestAdvice(query);
                        break;
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO: maybe filter results
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pb == null)
                    pb = (ProgressBar) findViewById(R.id.giphy_progress);
                switch (getIntent().getIntExtra("button_id",-1)) {
                    case R.id.button_giphy: {
                        if (resultImage == null)
                            resultImage = (GifImageView) findViewById(R.id.giphy_image);
                        else
                            returnLeft(resultImage);

                        break;
                    }
                    case R.id.button_wikipedia: {
                        if (article == null) {
                            article = (RelativeLayout) findViewById(R.id.article);

                            DisplayMetrics metrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(metrics);
                            RelativeLayout.LayoutParams layoutParams =
                                    new RelativeLayout.LayoutParams(metrics.widthPixels*3/4,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                            layoutParams.addRule(RelativeLayout.BELOW, R.id.search);
                            article.setLayoutParams(layoutParams);

                            articleTitle = (TextView) findViewById(R.id.article_title);
                            articleText = (TextView) findViewById(R.id.article_text);
                        }
                        else
                            returnLeft(article);

                        break;
                    }
                }

                collapse(categories);
                collapse(featured);

                searchView.animate()
                        .setDuration(SEARCHBAR_MOVEMENT_DURATION_MS)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                Animator.AnimatorListener emptyListener = new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // remove image view from framlayout
                                    }
                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                };

                                if (article != null) {
                                    article.animate()
                                            .translationY(-(getResources().getDimension(R.dimen.searchbar_margin_top) / 2))
                                            .setDuration(MOVEMENT_DURATION_MS)
                                            .setListener(emptyListener)
                                            .start();
                                }
                                searchView.animate()
                                        .translationY(-(getResources().getDimension(R.dimen.searchbar_margin_top) / 2))
                                        .setDuration(MOVEMENT_DURATION_MS)
                                        .setListener(emptyListener)
                                        .start();

                                pb.setY(pb.getY() - (getResources().getDimension(R.dimen.searchbar_margin_top)/2));
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                pb.setVisibility(View.GONE);

                expand(featured);
                expand(categories);

                switch (getIntent().getIntExtra("button_id", -1)) {
                    case R.id.button_giphy:
                    {
                        movRight(resultImage);
                        break;
                    }
                    case R.id.button_wikipedia:
                    {
                        movRight(article);
                        break;
                    }
                }



                searchView.animate()
                        .translationY(0)
                        .setStartDelay(MOVEMENT_DURATION_MS)
                        .setDuration(SEARCHBAR_MOVEMENT_DURATION_MS)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                pb.animate()
                                        .translationY(0)
                                        .start();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).start();

                return false;
            }
        });
    }



    public void expand(final View v) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetWidth = width; //v.getMeasuredWidth();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().width = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().width = (interpolatedTime == 1)?
                        ViewGroup.LayoutParams.MATCH_PARENT :
                        (int)(targetWidth * interpolatedTime);
                v.requestLayout();
            }



            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };


        // 1dp/ms
        a.setStartOffset(1000);
        a.setDuration((int)(width /  v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void collapse(final View v) {
        final int initialWidth = v.getMeasuredWidth();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().width = initialWidth - (int)(initialWidth * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialWidth / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void movRight(final View v) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        v.animate()
                .translationX(v.getWidth() + metrics.widthPixels - v.getRight())
                .setDuration(MOVEMENT_DURATION_MS)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    private void returnLeft(View v) {
        v.animate()
                .translationX(0)
                .start();
    }

    private void requestGif(String query) {
        if (resultImage.getDrawable() != null)
            resultImage.setImageDrawable(null);

        resultImage.setVisibility(View.VISIBLE);

        Call<String> call = WhazzupApp.getService().loadGiphy(new ObjectRequest(1, query));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String giphyUrl = response.body();
                Glide.with(RequestActivity.this)
                        .asGif()
                        .load(giphyUrl)
                        .listener(new RequestListener<GifDrawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                pb.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                pb.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(resultImage);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pb.setVisibility(View.GONE);

                Toast.makeText(RequestActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestWiki(String query) {
        if (!articleTitle.getText().toString().isEmpty()
                && !articleText.getText().toString().isEmpty() ) {
            articleTitle.setText("");
            articleText.setText("");
        }

        article.setVisibility(View.GONE);

        Call<ObjectResponseWiki> call = WhazzupApp.getService().loadWiki(new ObjectRequest(1, query));
        call.enqueue(new Callback<ObjectResponseWiki>() {
            @Override
            public void onResponse(Call<ObjectResponseWiki> call, Response<ObjectResponseWiki> response) {
                pb.setVisibility(View.GONE);

                ObjectResponseWiki wikiArticle = response.body();
                if (wikiArticle != null) {
                    articleTitle.setText(wikiArticle.title);
                    articleText.setText(wikiArticle.summary);
                    article.setVisibility(View.VISIBLE);
                }
                else
                    Toast.makeText(RequestActivity.this, "No results for this search", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ObjectResponseWiki> call, Throwable t) {
                pb.setVisibility(View.GONE);

                Toast.makeText(RequestActivity.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
                Toast.makeText(RequestActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestAdvice(String query) {

    }
}
