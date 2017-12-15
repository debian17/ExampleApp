package com.example.citilin.testapp.ui.characters;

import android.content.Intent;

import com.example.citilin.testapp.network.NetworkManager;
import com.example.citilin.testapp.ui.BasePresenter;
import com.example.citilin.testapp.ui.characters.character.CharacterTabInfoActivity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class CharactersPresenter extends BasePresenter<Character>
        implements CharacterAdapterDelegate.OnIconClick {

    private static final int QUANTITY_CHARACTERS = 20;
    private int offset;
    private boolean isLoading;

    private Integer value;
    private PublishSubject<Integer> subjectValue;

    Observable<Integer> getObservableProperty() {
        return subjectValue;
    }

    void setProperty(Integer i) {
        value = i;
        subjectValue.onNext(value);
    }

    CharactersPresenter() {
        offset = 0;
        value = 0;
        subjectValue = PublishSubject.create();
    }

    void closeConnection() {
        isLoading = false;
        compositeDisposable.clear();
    }

    void loadCharacters() {
        compositeDisposable.add(unitOfWork.getCharacterRepository()
                .getCharacters(QUANTITY_CHARACTERS, offset)
                .subscribe(this::onCharactersLoaded, this::showError));
        if (NetworkManager.isConnected()) {
            if (offset == 0) {
                view.startRefreshing();
            }
            if (!isLoading) {
                isLoading = true;
            }
        } else {
            view.showNetworkError();
        }
    }

    void reloadCharacters() {
        view.startRefreshing();
        if (!isLoading) {
            isLoading = true;
            offset = 0;
            compositeDisposable.add(unitOfWork.getCharacterRepository()
                    .getCharacters(QUANTITY_CHARACTERS, offset)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onCharactersReloaded, this::showError));
        }
    }

    private void onCharactersLoaded(List<Character> characters) {
        isLoading = false;
        if (view != null) {
            if (offset == 0) {
                view.stopRefreshing();
            }
            view.deletePagination();
            view.addItems(characters);
            offset += characters.size();
        }
    }

    private void onCharactersReloaded(List<Character> characters) {
        isLoading = false;
        if (view != null) {
            view.deletePagination();
            view.replaceItems(characters);
            view.stopRefreshing();
            offset += characters.size();
        }
    }

    private void showError(Throwable t) {
        isLoading = false;
        if (view != null) {
            view.loadError();
        }
    }

    @Override
    public void onClick(android.view.View view, Character character) {
        Intent intent = CharacterTabInfoActivity.getIntent(view.getContext(), character);
        view.getContext().startActivity(intent);
    }
}
