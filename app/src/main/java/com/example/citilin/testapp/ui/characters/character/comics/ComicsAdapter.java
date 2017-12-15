package com.example.citilin.testapp.ui.characters.character.comics;

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

/**
 * Created by Андрей on 17-Aug-17.
 */

class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    private ArrayList<Comic> items;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView comicsImage;
        private TextView comicsTitle;
        private TextView comicsPages;
        private TextView comicsFormat;

        ViewHolder(View itemView) {
            super(itemView);
            comicsImage = (ImageView) itemView.findViewById(R.id.vgComicsImage);
            comicsTitle = (TextView) itemView.findViewById(R.id.vgComicsTitle);
            comicsPages = (TextView) itemView.findViewById(R.id.tvComicsPages);
            comicsFormat = (TextView) itemView.findViewById(R.id.tvComicsFormat);
        }
    }

    ComicsAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void replaceAllItems(List<Comic> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Comic> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comics_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comic item = items.get(position);
        ImageManager.setImage(holder.comicsImage, false, item.getImagePath());
        TextUtil.setText(holder.comicsTitle, item.title, context.getString(R.string.unknown_comics));
        TextUtil.setText(holder.comicsFormat, "Format: " + item.format, context.getString(R.string.unknown));
        TextUtil.setText(holder.comicsPages, "Pages: "+ String.valueOf(item.pageCount), context.getString(R.string.unknown));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
