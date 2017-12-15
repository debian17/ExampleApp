package com.example.citilin.testapp.ui.characters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.citilin.testapp.R;
import com.example.citilin.testapp.ScreenUtils;
import com.example.citilin.testapp.ui.EndlessRecyclerViewScrollListener;
import com.example.citilin.testapp.ui.RetainedFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.ResourceObserver;

public class CharactersFragment extends Fragment implements CharactersPresenter.View<Character> {

    private RecyclerView heroesRecyclerView;
    private SwipeRefreshLayout heroSwipeRefreshLayout;
    private ItemsAdapter itemsAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private RetainedFragment retainedFragment;
    private CharactersPresenter charactersPresenter;

    private final int VIEW_TYPE_CHARACTER = 0;
    private final int VIEW_TYPE_AD = 1;
    private final int VIEW_TYPE_PAGINATION = 2;
    private final static String RETAINED_FRAGMENT = "RETAINED_FRAGMENT";

    private CompositeDisposable compositeDisposable;

    private ResourceObserver<Integer> firstObserver = new ResourceObserver<Integer>() {

        @Override
        protected void onStart() {
            Log.e("firstObserver", "onStart");
        }

        @Override
        public void onNext(@NonNull Integer integer) {
            Log.e("firstObserver", String.valueOf(integer));
        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.e("firstObserver", "onError");
        }

        @Override
        public void onComplete() {
            Log.e("firstObserver", "onComplete");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compositeDisposable = new CompositeDisposable();
        charactersPresenter = new CharactersPresenter();
        itemsAdapter = new ItemsAdapter(new ArrayList<>());
        itemsAdapter.getDelegatesManager()
                .addDelegate(VIEW_TYPE_CHARACTER, new CharacterAdapterDelegate(getActivity(), charactersPresenter))
                .addDelegate(VIEW_TYPE_AD, new AdAdapterDelegate(getActivity()))
                .addDelegate(VIEW_TYPE_PAGINATION, new PaginationAdapterDelegate(getActivity()));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        retainedFragment = (RetainedFragment) fragmentManager.findFragmentByTag(RETAINED_FRAGMENT);
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            fragmentManager.beginTransaction()
                    .add(retainedFragment, RETAINED_FRAGMENT)
                    .commit();
        } else {
            itemsAdapter.setItems(retainedFragment.getItems());
            charactersPresenter = retainedFragment.getCharactersPresenter();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_heroes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreLoadingLayoutManager layoutManager = new PreLoadingLayoutManager(getContext(),
                ScreenUtils.getScreenHeight(getContext()) * 2);

//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//
//                switch (itemsAdapter.getItemViewType(position)) {
//                    case VIEW_TYPE_CHARACTER: {
//                        return 1;
//                    }
//                    case VIEW_TYPE_AD: {
//                        return 2;
//                    }
//                    case VIEW_TYPE_PAGINATION: {
//                        return 2;
//                    }
//                    default: {
//                        return -1;
//                    }
//                }
//            }
//        });

        heroesRecyclerView = (RecyclerView) view.findViewById(R.id.rvHeroes);
        heroesRecyclerView.setLayoutManager(layoutManager);
        heroesRecyclerView.setAdapter(itemsAdapter);

        heroSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vgSwipeRefreshHeroes);
        heroSwipeRefreshLayout.setOnRefreshListener(() -> charactersPresenter.reloadCharacters());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                charactersPresenter.loadCharacters();
            }
        };

        heroesRecyclerView.addOnScrollListener(scrollListener);

        charactersPresenter.attachView(this);

        if (itemsAdapter.getItemCount() == 0) {
            charactersPresenter.loadCharacters();
        }

        compositeDisposable.add(charactersPresenter.getObservableProperty()
                .subscribeWith(firstObserver)
        );

    }

    @Override
    public void addItems(List<Character> items) {
        charactersPresenter.setProperty(items.size());
        itemsAdapter.addItems(items);
        itemsAdapter.add(new Ad());
        itemsAdapter.add(new Pagination());
    }

    @Override
    public void replaceItems(List<Character> items) {
        charactersPresenter.setProperty(2);
        itemsAdapter.replaceAllItems(items);
        itemsAdapter.add(new Ad());
        itemsAdapter.add(new Pagination());
        heroSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadError() {
        Toast.makeText(getContext(), R.string.error_loading_data, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void deletePagination() {
        itemsAdapter.deletePagination();
    }

    @Override
    public void startRefreshing() {
        heroSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopRefreshing() {
        heroSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        heroesRecyclerView.setAdapter(null);
        charactersPresenter.detachView();
        Log.e("TAG", "ОТПИСКА");
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        retainedFragment.setItems(itemsAdapter.getItems());
        retainedFragment.setCharactersPresenter(charactersPresenter);
        if (!getActivity().isChangingConfigurations()) {
            charactersPresenter.closeConnection();
        }
    }
}
