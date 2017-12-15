package com.example.citilin.testapp.ui.characters.character.stories;


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

public class StoriesTabFragment extends Fragment implements StoriesPresenter.View<Story> {

    private RecyclerView storiesRecyclerView;
    private StoriesAdapter storiesAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int characterId;

    private StoriesPresenter storiesPresenter;

    public StoriesTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storiesPresenter = new StoriesPresenter();
        storiesAdapter = new StoriesAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories_tab, container, false);
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

        storiesRecyclerView = (RecyclerView) view.findViewById(R.id.rvStories);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storiesRecyclerView.setAdapter(storiesAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vgSwipeRefreshStories);
        swipeRefreshLayout.setOnRefreshListener(() -> storiesPresenter.reloadStories(characterId));

        scrollListener = new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                storiesPresenter.loadStories(characterId);
            }
        };

        storiesRecyclerView.addOnScrollListener(scrollListener);

        storiesPresenter.attachView(this);

        storiesPresenter.loadStories(characterId);
    }

    @Override
    public void addItems(List<Story> items) {
        storiesAdapter.addItems(items);
    }

    @Override
    public void replaceItems(List<Story> items) {
        storiesAdapter.replaceAllItems(items);
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
        storiesRecyclerView.setAdapter(null);
        storiesPresenter.detachView();
    }
}
