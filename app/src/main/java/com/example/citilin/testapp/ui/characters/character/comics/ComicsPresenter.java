package com.example.citilin.testapp.ui.characters.character.comics;

import com.example.citilin.testapp.network.NetworkManager;
import com.example.citilin.testapp.ui.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Андрей on 21-Aug-17.
 */

class ComicsPresenter extends BasePresenter<Comic> {

    private static final int QUANTITY_COMICS = 20;
    private int offset;

    ComicsPresenter() {
        offset = 0;
    }

    void loadComics(int characterId) {
        if (NetworkManager.isConnected()) {
            if (offset == 0) {
                view.startRefreshing();
            }
            compositeDisposable.add(unitOfWork.getCharacterRepository()
                    .getComics(characterId, QUANTITY_COMICS, offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onComicsLoaded, this::showError));
        } else {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.showNetworkError();
        }

    }

    void reloadComics(int characterId) {
        view.startRefreshing();
        offset = 0;
        compositeDisposable.add(unitOfWork.getCharacterRepository()
                .getComics(characterId, QUANTITY_COMICS, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onComicsReloaded, this::showError));
    }

    private void onComicsLoaded(List<Comic> comics) {
        if (view != null) {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.deletePagination();
            view.addItems(comics);
            offset += QUANTITY_COMICS;
        }
    }

    private void onComicsReloaded(List<Comic> comics) {
        if (view != null) {
            view.deletePagination();
            view.replaceItems(comics);
            view.stopRefreshing();
            offset += QUANTITY_COMICS;
        }
    }

    private void showError(Throwable t) {
        if (view != null) {
            view.loadError();
        }
    }
}
