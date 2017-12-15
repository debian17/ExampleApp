package com.example.citilin.testapp.ui.characters.character.event;

import com.example.citilin.testapp.network.NetworkManager;
import com.example.citilin.testapp.ui.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Андрей on 21-Aug-17.
 */

class EventPresenter extends BasePresenter<Event> {

    private static final int QUANTITY_EVENTS = 20;
    private int offset;

    EventPresenter() {
        offset = 0;
    }

    void loadEvents(int characterId) {
        if (NetworkManager.isConnected()) {
            if (offset == 0) {
                view.startRefreshing();
            }
            compositeDisposable.add(unitOfWork.getCharacterRepository()
                    .getEvents(characterId, QUANTITY_EVENTS, offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onEventsLoaded, this::showError));
        } else {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.showNetworkError();
        }
    }

    void reloadEvents(int characterId) {
        view.startRefreshing();
        offset = 0;
        compositeDisposable.add(unitOfWork.getCharacterRepository()
                .getEvents(characterId, QUANTITY_EVENTS, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onComicsReloaded, this::showError));
    }

    private void onEventsLoaded(List<Event> events) {
        if (view != null) {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.deletePagination();
            view.addItems(events);
            offset += QUANTITY_EVENTS;
        }
    }

    private void onComicsReloaded(List<Event> events) {
        if (view != null) {
            view.deletePagination();
            view.replaceItems(events);
            view.stopRefreshing();
            offset += QUANTITY_EVENTS;
        }
    }

    private void showError(Throwable t) {
        if (view != null) {
            view.loadError();
        }
    }
}
