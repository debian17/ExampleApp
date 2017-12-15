package com.example.citilin.testapp;

import android.content.Context;

import com.example.citilin.testapp.network.RetrofitCreator;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.firebase.FirebaseApp;
import com.jakewharton.threetenabp.AndroidThreeTen;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by citilin on 14.08.2017.
 */

class DependencyManager {

    static void initLibraries(Context context) {
        Stetho.initializeWithDefaults(context);
        FirebaseApp.initializeApp(context);
        AndroidThreeTen.init(context);
    }

    static Retrofit getRetrofit() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(new StethoInterceptor());

        return RetrofitCreator.create()
                .client(httpClient.build())
                .build();
    }

}

