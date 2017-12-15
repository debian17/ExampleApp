package com.example.citilin.testapp.ui.characters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;

import java.util.ArrayList;
import java.util.List;

class ItemsAdapter extends RecyclerView.Adapter {

    private AdapterDelegatesManager<List<Object>> delegatesManager;
    private List<Object> items;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    ItemsAdapter(List<Object> items) {
        this.items = items;
        delegatesManager = new AdapterDelegatesManager<>();
    }

    AdapterDelegatesManager<List<Object>> getDelegatesManager() {
        return delegatesManager;
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public <T> void replaceAllItems(List<T> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public <T> void addItems(List<T> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    public <T> void add(T item) {
        items.add(item);
        notifyItemRangeInserted(this.items.size(), 1);
    }

    void deletePagination() {
        if (items.size() != 0) {
            items.remove(items.size() - 1);
        }
        notifyItemRemoved(items.size() - 1);
    }
}
