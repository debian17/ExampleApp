package com.example.citilin.testapp.ui.mychracters.addmycharacter;


import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.citilin.testapp.ui.UnitOfWork;
import com.example.citilin.testapp.ui.mychracters.MyCharacter;

class AddMyCharacterPresenter {

    private UnitOfWork unitOfWork;
    private View view;

    interface View {
        //добавил персонажа в БД
        void onAdded();

        //не удалось добавить персонажа в БД
        void onFailure();

        //сбросить интерфейс пользователя
        void resetUI();

        //закрать окно добавления
        void onAddFinish();

        void onValidateNameFail();

        void onValidateSuperPowerFail();

        void onValidatePicturePathFail();

        void hideErrors();

        void galleryPermissionDenied();

        void cameraPermissionDenied();

    }

    AddMyCharacterPresenter() {
        unitOfWork = new UnitOfWork();
    }

    public void attachView(@NonNull View view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    void addMyCharacter(String name, String superPower, Uri pictureUri, boolean moreThanOnce) {
        boolean validateError = false;
        view.hideErrors();

        if (!isEmptyString(name)) {
            view.onValidateNameFail();
            validateError = true;
        }

        if (!isEmptyString(superPower)) {
            view.onValidateSuperPowerFail();
            validateError = true;
        }

        if (pictureUri == null || !isEmptyString(pictureUri.getPath())) {
            view.onValidatePicturePathFail();
            validateError = true;
        }

        if (validateError) {
            return;
        }

        unitOfWork.getMyCharactersRepository()
                .add(new MyCharacter(name, superPower, pictureUri.getPath()));
        view.onAdded();
        if (moreThanOnce) {
            view.resetUI();
        } else {
            view.onAddFinish();
        }
    }

    void galleryPermissionDenied() {
        if (view != null) {
            view.galleryPermissionDenied();
        }
    }

    void cameraPermissionDenied() {
        if (view != null) {
            view.cameraPermissionDenied();
        }
    }

    private boolean isEmptyString(String str) {
        return str != null && !str.isEmpty();
    }

}
