package com.example.citilin.testapp.ui.about;

import android.content.Context;

import com.example.citilin.testapp.ui.characters.ImageCacheManager;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Андрей on 29-Aug-17.
 */

class ShareImageCacheManager {

    Single<String> calculateCacheSize(Context context) {
        return Single.fromCallable(() -> ImageCacheManager.calculateCacheSize(context))
                .subscribeOn(Schedulers.io());
    }

    Single<Boolean> cleanCache(Context context) {
        return Single.fromCallable(() -> ImageCacheManager.cleanCache(context))
                .subscribeOn(Schedulers.io());
    }
}
