package com.example.citilin.testapp.ui.characters.character.series;


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

public class SeriesTabFragment extends Fragment implements SeriesPresenter.View<Series> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView seriesRecyclerView;
    private SeriesAdapter seriesAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    private int characterId;

    private SeriesPresenter seriesPresenter;

    public SeriesTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seriesPresenter = new SeriesPresenter();
        seriesAdapter = new SeriesAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series_tab, container, false);
        return view;
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

        seriesRecyclerView = (RecyclerView) view.findViewById(R.id.rvSeries);
        seriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        seriesRecyclerView.setAdapter(seriesAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vgSwipeRefreshSeries);
        swipeRefreshLayout.setOnRefreshListener(() -> seriesPresenter.reloadSeries(characterId));

        scrollListener = new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                seriesPresenter.loadSeries(characterId);
            }
        };

        seriesRecyclerView.addOnScrollListener(scrollListener);

        seriesPresenter.attachView(this);

        seriesPresenter.loadSeries(characterId);
    }

    @Override
    public void addItems(List<Series> items) {
        seriesAdapter.addItems(items);
    }

    @Override
    public void replaceItems(List<Series> items) {
        seriesAdapter.replaceAllItems(items);
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
        seriesRecyclerView.setAdapter(null);
        seriesPresenter.detachView();
    }
}
