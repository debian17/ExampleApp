package com.example.citilin.testapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.citilin.testapp.ui.characters.CharactersPresenter;

import java.util.List;

public class RetainedFragment extends Fragment {

    private List<Object> items;
    private CharactersPresenter charactersPresenter;

    public CharactersPresenter getCharactersPresenter() {
        return charactersPresenter;
    }

    public void setCharactersPresenter(CharactersPresenter charactersPresenter) {
        this.charactersPresenter = charactersPresenter;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
