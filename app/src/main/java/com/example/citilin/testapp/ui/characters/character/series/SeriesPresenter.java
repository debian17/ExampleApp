package com.example.citilin.testapp.ui.characters.character.series;

import com.example.citilin.testapp.network.NetworkManager;
import com.example.citilin.testapp.ui.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Андрей on 21-Aug-17.
 */

class SeriesPresenter extends BasePresenter<Series> {

    private static final int QUANTITY_SERIES = 20;
    private int offset;

    SeriesPresenter() {
        offset = 0;
    }

    void loadSeries(int characterId) {
        if (NetworkManager.isConnected()) {
            if (offset == 0) {
                view.startRefreshing();
            }
            compositeDisposable.add(unitOfWork.getCharacterRepository()
                    .getSeries(characterId, QUANTITY_SERIES, offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSeriesLoaded, this::showError));
        } else {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.showNetworkError();
        }
    }

    void reloadSeries(int characterId) {
        view.startRefreshing();
        offset = 0;
        compositeDisposable.add(unitOfWork.getCharacterRepository()
                .getSeries(characterId, QUANTITY_SERIES, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSeriesReloaded, this::showError));
    }

    private void onSeriesReloaded(List<Series> series) {
        if (view != null) {
            view.deletePagination();
            view.replaceItems(series);
            view.stopRefreshing();
            offset += QUANTITY_SERIES;
        }
    }

    private void onSeriesLoaded(List<Series> series) {
        if (view != null) {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.deletePagination();
            view.addItems(series);
            offset += QUANTITY_SERIES;
        }
    }

    private void showError(Throwable t) {
        if (view != null) {
            view.loadError();
        }
    }
}
