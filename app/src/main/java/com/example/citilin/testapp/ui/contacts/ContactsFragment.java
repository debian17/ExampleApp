package com.example.citilin.testapp.ui.contacts;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citilin.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment implements ContactPresenter.View {

    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    private RecyclerView recyclerViewContacts;
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactList;
    private ContactPresenter contactPresenter;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactPresenter = new ContactPresenter();
        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewContacts = (RecyclerView) view.findViewById(R.id.rvContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewContacts.setAdapter(contactAdapter);

        contactPresenter.attachView(this);

        contactPresenter.readContacts();
    }

    @Override
    public void requestPermissionToReadContacts() {
        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED)) {
            contactPresenter.getContacts(getContext());
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            contactPresenter.getContacts(getContext());
        } else {
            contactPresenter.permissionDenied();
        }
    }

    @Override
    public void permissionDenied() {
        Toast.makeText(getContext(), R.string.read_contacts_permission_denied, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void addContacts(List<Contact> contacts) {
        for (Contact contact : contacts) {
            contactAdapter.add(contact);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), R.string.read_contacts_error, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void startReadingProgress() {
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.reading_list_contacts), true, false);
    }

    @Override
    public void stopReadingProgress() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        contactPresenter.detachView();
    }
}
