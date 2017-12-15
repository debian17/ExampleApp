package com.example.citilin.testapp.ui.about;


import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class AboutPresenter {

    private View view;
    private Context context;
    private CompositeDisposable compositeDisposable;
    private ShareImageCacheManager shareImageCacheManager;

    interface View {
        void startProgress();

        void stopProgress();

        void cacheCleaned();

        void cacheCleanFailed();

        void cacheCleanError();

        void calculateCacheSizeSuccess(String cacheSize);

        void calculateCacheSizeFailed();
    }

    AboutPresenter(Context context) {
        this.context = context;
        compositeDisposable = new CompositeDisposable();
        shareImageCacheManager = new ShareImageCacheManager();
    }

    void attachView(@NonNull View view) {
        this.view = view;
    }

    void detachView() {
        view = null;
        compositeDisposable.clear();
    }

    void clearCache() {
        view.startProgress();
        compositeDisposable.add(shareImageCacheManager.cleanCache(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::cacheCleaned, this::cacheCleanError));
    }

    private void cacheCleaned(Boolean result) {
        if (view != null) {
            view.stopProgress();
            if (result) {
                view.cacheCleaned();
            } else {
                view.cacheCleanFailed();
            }
        }
    }

    private void cacheCleanError(Throwable t) {
        if (view != null) {
            view.stopProgress();
            view.cacheCleanError();
        }
    }

    void calculateCacheSize() {
        view.startProgress();
        compositeDisposable.add(shareImageCacheManager.calculateCacheSize(context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::calculateCacheSizeSuccess, this::calculateCacheSizeFailed));
    }

    private void calculateCacheSizeSuccess(String cacheSize) {
        if (view != null) {
            view.stopProgress();
            view.calculateCacheSizeSuccess(cacheSize);
        }
    }

    private void calculateCacheSizeFailed(Throwable t) {
        if (view != null) {
            view.stopProgress();
            view.calculateCacheSizeFailed();
        }
    }
}
