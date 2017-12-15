package com.example.citilin.testapp.ui.time;


import android.support.annotation.NonNull;

public class TimePresenter {

    private View view;

    interface View{

    }

    void attachView(@NonNull View view) {
        this.view = view;
    }

    void detachView() {
        view = null;
    }

}
