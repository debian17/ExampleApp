package com.example.citilin.testapp;

import android.content.Context;

import com.example.citilin.testapp.network.API;

import retrofit2.Retrofit;

/**
 * Created by Andrey on 31-Aug-17.
 */

public class App extends VKApp {

    private static API api;
    private Retrofit retrofit;
    private static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DependencyManager.initLibraries(getApplicationContext());
        retrofit = DependencyManager.getRetrofit();
        api = retrofit.create(API.class);
    }

    public static Context getAppContext(){
        return instance;
    }

    public static API getApi(){
        return api;
    }

}
