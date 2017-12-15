package com.example.citilin.testapp.ui.mychracters;

import android.content.Intent;

import com.example.citilin.testapp.database.MyCharactersSqlSpecification;
import com.example.citilin.testapp.ui.UnitOfWork;
import com.example.citilin.testapp.ui.mychracters.updatemycharacter.UpdateMyCharacterActivity;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Андрей on 23-Aug-17.
 */

class MyCharacterPresenter implements MyCharacterAdapter.OnClick {

    private UnitOfWork unitOfWork;
    private View view;
    private CompositeDisposable compositeDisposable;
    private MyCharacter deletedMyCharacter;
    private int deletedPosition;

    MyCharacterPresenter() {
        unitOfWork = new UnitOfWork();
        compositeDisposable = new CompositeDisposable();
    }

    interface View {

        void onItemsLoaded(List<MyCharacter> items);

        void onLoadEmptyData();

        void onDeleted(int position);

        void onDeletedFail();

        void onItemLoadFail();

        void onRestored(int position, MyCharacter myCharacter);

        void onRestoreFail();
    }

    public void attachView(View view) {
        this.view = view;
    }

    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }

    void getAllMyCharacters() {
        compositeDisposable.add(unitOfWork.getMyCharactersRepository()
                .query(new MyCharactersSqlSpecification())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMyCharacterLoaded, this::showError));
    }

    private void onMyCharacterLoaded(List<MyCharacter> myCharacters) {
        if (myCharacters.isEmpty()) {
            view.onLoadEmptyData();
        } else {
            view.onItemsLoaded(myCharacters);
        }
    }

    private void showError(Throwable t) {
        view.onItemLoadFail();
    }

    void restoreMyCharacter() {
        view.onRestored(deletedPosition, deletedMyCharacter);
    }

    void cleanMyCharacterCache() {
        deletedMyCharacter = null;
    }

    @Override
    public void onEditClick(android.view.View v, MyCharacter myCharacter) {
        Intent intent = UpdateMyCharacterActivity.getIntent(v.getContext(), myCharacter);
        v.getContext().startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position, MyCharacter myCharacter) {
        if (deletedMyCharacter != null) {
            unitOfWork.getMyCharactersRepository().remove(deletedMyCharacter);
        }
        deletedMyCharacter = myCharacter;
        deletedPosition = position;
        view.onDeleted(position);
    }

    void deleteItem() {
        if (deletedMyCharacter != null) {
            unitOfWork.getMyCharactersRepository().remove(deletedMyCharacter);
            deletedMyCharacter = null;
        }
    }

}
