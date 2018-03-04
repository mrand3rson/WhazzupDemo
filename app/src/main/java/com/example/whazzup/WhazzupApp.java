package com.example.whazzup;


import android.app.Application;

import com.example.whazzup.rest.ContentService;
import com.example.whazzup.security.CustomTrust;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WhazzupApp extends Application {

    private static final String baseUrl = "https://expasoft.com:8462/";
    private static ContentService service;
    private static Retrofit retrofit;
    //private static PushContentService pushService;

    @Override
    public void onCreate() {
        super.onCreate();

        initRetrofit();
    }

    private void initRetrofit() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.getCookieStore().getCookies();
        CookieHandler.setDefault(cookieManager);

        //CustomTrust trust = new CustomTrust();
        OkHttpClient client = CustomTrust.getUnsafeOkHttpClient();//trust.create(); //возвращает client для https
        client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("content-type", "application/json").build();
                return chain.proceed(request);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ContentService getService() {
        if (service == null)
            service = retrofit.create(ContentService.class);
        return service;
    }
}
