package com.example.citilin.testapp.ui.characters.character.series;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.citilin.testapp.ImageManager;
import com.example.citilin.testapp.R;
import com.example.citilin.testapp.TextUtil;

import java.util.ArrayList;
import java.util.List;


class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {


    private ArrayList<Series> items;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView seriesImage;
        private TextView seriesTitle;
        private TextView seriesDescription;

        ViewHolder(View itemView) {
            super(itemView);

            seriesImage = (ImageView) itemView.findViewById(R.id.imgSeries);
            seriesTitle = (TextView) itemView.findViewById(R.id.tvSeriesTitle);
            seriesDescription = (TextView) itemView.findViewById(R.id.tvSeriesDescription);
        }
    }

    SeriesAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void replaceAllItems(List<Series> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Series> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Series item = items.get(position);

        ImageManager.setImage(holder.seriesImage, false, item.getImagePath());
        TextUtil.setText(holder.seriesTitle, item.title, context.getString(R.string.unknown_series));
        TextUtil.setText(holder.seriesDescription, item.description, context.getString(R.string.no_description));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
