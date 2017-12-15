package com.example.citilin.testapp.network;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

    public static Retrofit.Builder create() {

        return new Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
}
