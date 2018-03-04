package com.example.whazzup;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    LoginManager mManager;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();

        View.OnClickListener facebookLoginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager = LoginManager.getInstance();
                mManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken at =  loginResult.getAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(at, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //Toast.makeText(LoginActivity.this, response.getJSONObject().toString(), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                if (Build.VERSION.SDK_INT >= 21) {
                                    overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
                                }
                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "age_range,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "CANCELLED", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
                mManager.logInWithReadPermissions(LoginActivity.this,
                        Arrays.asList("public_profile"));



            }
        };
        View.OnClickListener simpleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                if (Build.VERSION.SDK_INT >= 21) {
                    overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
                }
            }
        };

        Button button = (Button) findViewById(R.id.button_login);
        button.setOnClickListener(simpleListener);//facebookLoginListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

