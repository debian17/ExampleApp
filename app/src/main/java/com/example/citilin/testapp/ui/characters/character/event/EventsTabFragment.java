package com.example.citilin.testapp.ui.characters.character.event;


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

public class EventsTabFragment extends Fragment implements EventPresenter.View<Event> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView eventRecyclerView;
    private EventsAdapter eventsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    private int characterId;

    EventPresenter eventPresenter;

    public EventsTabFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventPresenter = new EventPresenter();
        eventsAdapter = new EventsAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_tab, container, false);
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

        eventRecyclerView = (RecyclerView) view.findViewById(R.id.rvEvents);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerView.setAdapter(eventsAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vgSwipeRefreshEvents);
        swipeRefreshLayout.setOnRefreshListener(() -> eventPresenter.reloadEvents(characterId));

        scrollListener = new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                eventPresenter.loadEvents(characterId);
            }
        };

        eventRecyclerView.addOnScrollListener(scrollListener);

        eventPresenter.attachView(this);

        eventPresenter.loadEvents(characterId);

    }

    @Override
    public void addItems(List<Event> items) {
        eventsAdapter.addItems(items);
    }

    @Override
    public void replaceItems(List<Event> items) {
        eventsAdapter.replaceAllItems(items);
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
        eventRecyclerView.setAdapter(null);
        eventPresenter.detachView();
    }
}
