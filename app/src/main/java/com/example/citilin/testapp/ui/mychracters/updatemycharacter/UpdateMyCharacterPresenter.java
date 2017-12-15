package com.example.citilin.testapp.ui.mychracters.updatemycharacter;

import com.example.citilin.testapp.ui.UnitOfWork;
import com.example.citilin.testapp.ui.mychracters.MyCharacter;

/**
 * Created by Андрей on 23-Aug-17.
 */

public class UpdateMyCharacterPresenter {

    private UnitOfWork unitOfWork;
    private View view;

    interface View {

        void onSuccess();

        void onFailure();

        void onValidateNameFail();

        void onValidateSuperPowerFail();

        void hideErrors();

        void galleryPermissionDenied();

        void cameraPermissionDenied();
    }

    UpdateMyCharacterPresenter() {
        unitOfWork = new UnitOfWork();
    }

    public void attachView(View view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    void updateMyCharacter(MyCharacter myCharacter) {
        boolean validateError = false;

        view.hideErrors();

        if (!isEmptyString(myCharacter.getName())) {
            view.onValidateNameFail();
            validateError = true;
        }

        if (!isEmptyString(myCharacter.getSuperPower())) {
            view.onValidateSuperPowerFail();
            validateError = true;
        }

        if (validateError) {
            return;
        }

        unitOfWork.getMyCharactersRepository().update(myCharacter);
        view.onSuccess();
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
