package com.example.whazzup;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class QuestioningActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_questioning);

        ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        final Scene scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.layout_question_open, this);

        final Button button = (Button) findViewById(R.id.question);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionInflater inflater = TransitionInflater.from(QuestioningActivity.this);
                Transition transition = inflater.inflateTransition(R.transition.transition_question);
                TransitionManager.go(scene2, transition);
            }
        });
    }
}
