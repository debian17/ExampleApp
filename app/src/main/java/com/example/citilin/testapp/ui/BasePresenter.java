package com.example.citilin.testapp.ui;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Andrey on 02-Sep-17.
 */

public class BasePresenter<T> {

    protected View<T> view;
    protected UnitOfWork unitOfWork;
    protected CompositeDisposable compositeDisposable;

    public interface View<T> {
        void addItems(List<T> items);

        void replaceItems(List<T> items);

        void loadError();

        void deletePagination();

        void startRefreshing();

        void stopRefreshing();

        void showNetworkError();
    }

    protected BasePresenter() {
        unitOfWork = new UnitOfWork();
        compositeDisposable = new CompositeDisposable();
    }

    public void attachView(@NonNull View<T> view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

}
