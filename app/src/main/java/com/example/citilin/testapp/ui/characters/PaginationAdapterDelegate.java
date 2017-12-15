package com.example.citilin.testapp.ui.characters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.citilin.testapp.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Andrey on 03-Sep-17.
 */

class PaginationAdapterDelegate extends AdapterDelegate<List<Object>> {

    private LayoutInflater layoutInflater;

    PaginationAdapterDelegate(Activity activity) {
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof Pagination;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PaginationViewHolder(layoutInflater.inflate(R.layout.item_loading, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {
        
        PaginationViewHolder paginationViewHolder = (PaginationViewHolder) holder;
        paginationViewHolder.progressBar.setIndeterminate(true);
    }

    private static class PaginationViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        PaginationViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadingProgressBar);
        }
    }
}
