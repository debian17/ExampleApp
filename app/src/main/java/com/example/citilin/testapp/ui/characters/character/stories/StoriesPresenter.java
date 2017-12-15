package com.example.citilin.testapp.ui.characters.character.stories;

import com.example.citilin.testapp.network.NetworkManager;
import com.example.citilin.testapp.ui.BasePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Андрей on 21-Aug-17.
 */

class StoriesPresenter extends BasePresenter<Story> {

    private static final int QUANTITY_STORIES = 20;
    private int offset;

    StoriesPresenter() {
        offset = 0;
    }

    void loadStories(int characterId) {
        if (NetworkManager.isConnected()) {
            if (offset == 0) {
                view.startRefreshing();
            }
            compositeDisposable.add(unitOfWork.getCharacterRepository()
                    .getStories(characterId, QUANTITY_STORIES, offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onStoriesLoaded, this::showError));
        } else {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.showNetworkError();
        }
    }

    void reloadStories(int characterId) {
        view.startRefreshing();
        offset = 0;
        compositeDisposable.add(unitOfWork.getCharacterRepository()
                .getStories(characterId, QUANTITY_STORIES, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onStoriesReloaded, this::showError));
    }

    private void onStoriesReloaded(List<Story> stories) {
        if (view != null) {
            view.deletePagination();
            view.replaceItems(stories);
            view.stopRefreshing();
            offset += QUANTITY_STORIES;
        }
    }

    private void onStoriesLoaded(List<Story> stories) {
        if (view != null) {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.deletePagination();
            view.addItems(stories);
            offset += QUANTITY_STORIES;
        }
    }


    private void showError(Throwable t) {
        if (view != null) {
            view.loadError();
        }
    }
}
