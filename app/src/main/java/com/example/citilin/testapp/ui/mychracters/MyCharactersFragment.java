package com.example.citilin.testapp.ui.mychracters;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.mychracters.addmycharacter.AddMyCharactersActivity;

import java.util.List;

public class MyCharactersFragment extends Fragment implements MyCharacterPresenter.View {

    private RecyclerView myCharactersRecyclerView;
    private MyCharacterAdapter myCharacterAdapter;
    private FloatingActionButton addFloatingButton;
    private TextView myCharacterEmptyList;
    private Snackbar snackbar;

    private MyCharacterPresenter myCharacterPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myCharacterPresenter = new MyCharacterPresenter();
        myCharacterAdapter = new MyCharacterAdapter(getContext(), myCharacterPresenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_characters, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myCharactersRecyclerView = (RecyclerView) view.findViewById(R.id.rvMyCharacters);
        myCharactersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myCharactersRecyclerView.setAdapter(myCharacterAdapter);

        addFloatingButton = (FloatingActionButton) view.findViewById(R.id.btnAddMyCharacter);
        addFloatingButton.setOnClickListener(v -> startActivity(new Intent(getContext(),
                AddMyCharactersActivity.class)));

        Drawable vectorDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_plus);

        addFloatingButton.setImageDrawable(vectorDrawable);

        myCharacterEmptyList = (TextView) view.findViewById(R.id.tvNoMyCharacter);

        myCharactersRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    addFloatingButton.hide();
                else if (dy < 0)
                    addFloatingButton.show();
            }
        });

        snackbar = Snackbar.make(view, R.string.my_character_deleted, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, v -> myCharacterPresenter.restoreMyCharacter());

        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                myCharacterPresenter.deleteItem();
            }
        });

        myCharacterPresenter.attachView(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        myCharacterPresenter.getAllMyCharacters();
        changeEmptyTextView();
    }

    @Override
    public void onPause() {
        super.onPause();
        myCharacterPresenter.deleteItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myCharactersRecyclerView.setAdapter(null);
        myCharacterPresenter.detachView();
    }

    @Override
    public void onItemsLoaded(List<MyCharacter> items) {
        myCharacterAdapter.replaceItems(items);
    }

    @Override
    public void onLoadEmptyData() {
        myCharacterAdapter.cleanAllItems();
    }

    @Override
    public void onDeleted(int position) {
        myCharacterAdapter.deleteItem(position);
        snackbar.show();
        changeEmptyTextView();
    }

    @Override
    public void onDeletedFail() {
        Toast.makeText(getContext(), R.string.my_character_delete_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onItemLoadFail() {
        Toast.makeText(getContext(), R.string.data_base_load_fail, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onRestored(int position, MyCharacter myCharacter) {
        myCharacterAdapter.insertItem(position, myCharacter);
        myCharacterPresenter.cleanMyCharacterCache();
    }

    @Override
    public void onRestoreFail() {
        Toast.makeText(getContext(), R.string.restore_my_character_fail, Toast.LENGTH_SHORT)
                .show();
    }

    private void changeEmptyTextView() {
        if (myCharacterAdapter.getItemCount() == 0) {
            myCharacterEmptyList.setVisibility(View.VISIBLE);
        } else {
            myCharacterEmptyList.setVisibility(View.INVISIBLE);
        }
    }
}
