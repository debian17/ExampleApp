package com.example.citilin.testapp;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.example.citilin.testapp.network.RetrofitCreator;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;

/**
 * Created by Andrey on 31-Aug-17.
 */

class DependencyManager {

    static void initLibraries(Context context){
        Fabric.with(context, new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder()
                        .build())
                .build());
    }

    static Retrofit getRetrofit() {
        return RetrofitCreator.create().build();
    }
}
