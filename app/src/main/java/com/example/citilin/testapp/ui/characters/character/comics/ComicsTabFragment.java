package com.example.citilin.testapp.ui.characters.character.comics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ui.EndlessRecyclerViewScrollListener;
import com.example.citilin.testapp.ui.characters.character.CharacterTabInfoActivity;

import java.util.List;

public class ComicsTabFragment extends Fragment implements ComicsPresenter.View<Comic> {

    private RecyclerView comicsRecyclerView;
    private ComicsAdapter comicsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    private int characterId;

    private ComicsPresenter comicsPresenter;

    public ComicsTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comics_tab, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicsPresenter = new ComicsPresenter();
        comicsAdapter = new ComicsAdapter(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            characterId = bundle.getInt(CharacterTabInfoActivity.CHARACTER_ID);
        } else {
            characterId = 0;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        comicsRecyclerView = (RecyclerView) view.findViewById(R.id.rvComics);
        comicsRecyclerView.setLayoutManager(linearLayoutManager);
        comicsRecyclerView.setAdapter(comicsAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vgRefreshComics);
        swipeRefreshLayout.setOnRefreshListener(() -> comicsPresenter.reloadComics(characterId));

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                comicsPresenter.loadComics(characterId);
            }
        };

        comicsRecyclerView.addOnScrollListener(scrollListener);

        comicsPresenter.attachView(this);

        comicsPresenter.loadComics(characterId);
    }

    @Override
    public void addItems(List<Comic> items) {
        comicsAdapter.addItems(items);
    }

    @Override
    public void replaceItems(List<Comic> items) {
        comicsAdapter.replaceAllItems(items);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadError() {
        Toast.makeText(getContext(), R.string.error_loading_data, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void deletePagination() {

    }

    @Override
    public void startRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comicsRecyclerView.setAdapter(null);
        comicsPresenter.detachView();
    }
}
