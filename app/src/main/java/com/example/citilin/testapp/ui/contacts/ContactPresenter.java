package com.example.citilin.testapp.ui.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

class ContactPresenter {

    private static String UNKNOWN_NUMBER = "Unknown number";

    private View view;
    private CompositeDisposable compositeDisposable;

    ContactPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    interface View {

        void requestPermissionToReadContacts();

        void permissionDenied();

        void addContacts(List<Contact> contacts);

        void showError();

        void startReadingProgress();

        void stopReadingProgress();
    }

    public void attachView(@NonNull View view) {
        this.view = view;
    }

    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }

    void readContacts() {
        view.requestPermissionToReadContacts();
    }

    void getContacts(Context context) {
        if (view != null) {
            view.startReadingProgress();
            compositeDisposable.add(loadContacts(context)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onContactsLoaded, this::showError));
        }
    }

    private Single<List<Contact>> loadContacts(Context context) {
        return Single.fromCallable(() -> {
            String name;
            String phoneNumber;
            String contactId;
            int hasPhoneNumber;
            Cursor contactCursor;
            Cursor phoneCursor;
            List<Contact> contactsList = new ArrayList<>();

            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            Uri PHONE_CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

            ContentResolver contentResolver = context.getContentResolver();
            contactCursor = contentResolver.query(CONTENT_URI, null, null, null, null);

            if (contactCursor != null && contactCursor.getCount() > 0) {
                while (contactCursor.moveToNext()) {
                    contactId = contactCursor.getString(contactCursor.getColumnIndex(ID));
                    name = contactCursor.getString(contactCursor.getColumnIndex(DISPLAY_NAME));
                    hasPhoneNumber = Integer.parseInt(contactCursor.getString(contactCursor.getColumnIndex(HAS_PHONE_NUMBER)));
                    if (hasPhoneNumber > 0) {
                        phoneCursor = contentResolver.query(PHONE_CONTENT_URI, null, PHONE_CONTACT_ID + " = ?", new String[]{contactId}, null);
                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                            phoneCursor.close();
                        } else {
                            phoneNumber = UNKNOWN_NUMBER;
                        }
                        contactsList.add(new Contact(name, phoneNumber));
                    } else {
                        phoneNumber = UNKNOWN_NUMBER;
                        contactsList.add(new Contact(name, phoneNumber));
                    }
                }
                contactCursor.close();
            } else {
                return Collections.emptyList();
            }
            return contactsList;
        });
    }

    private void onContactsLoaded(List<Contact> contacts) {
        if (view != null) {
            view.addContacts(contacts);
            view.stopReadingProgress();
        }
    }

    private void showError(Throwable throwable) {
        if (view != null) {
            view.stopReadingProgress();
            view.showError();
        }
    }

    void permissionDenied() {
        view.permissionDenied();
    }
}
