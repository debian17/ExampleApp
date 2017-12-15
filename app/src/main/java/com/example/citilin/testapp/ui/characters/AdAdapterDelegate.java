package com.example.citilin.testapp.ui.characters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.citilin.testapp.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Andrey on 03-Sep-17.
 */

class AdAdapterDelegate extends AdapterDelegate<List<Object>> {

    private LayoutInflater layoutInflater;

    AdAdapterDelegate(Activity activity) {
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof Ad;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new AdViewHolder(layoutInflater.inflate(R.layout.item_ad, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {

        AdViewHolder adViewHolder = (AdViewHolder) holder;
        Ad ad = (Ad) items.get(position);

        adViewHolder.title.setText(ad.getTitle());
        adViewHolder.description.setText(ad.getDescription());
    }

    private static class AdViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;

        AdViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvAdTitle);
            description = (TextView) itemView.findViewById(R.id.tvAdDescription);
        }
    }
}