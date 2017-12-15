package com.example.citilin.testapp.ui.characters.character.stories;

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

class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    private ArrayList<Story> items;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView storyTitle;
        private TextView storyDescription;
        private ImageView storyImage;


        ViewHolder(View itemView) {
            super(itemView);
            storyTitle = (TextView) itemView.findViewById(R.id.tvStoryTitle);
            storyDescription = (TextView) itemView.findViewById(R.id.tvStoryDescription);
            storyImage = (ImageView) itemView.findViewById(R.id.imgStory);
        }
    }

    StoriesAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public void replaceAllItems(List<Story> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Story> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
        notifyItemRangeInserted(this.items.size(), items.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new StoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Story item = items.get(position);
        ImageManager.setImage(holder.storyImage, false, item.getImagePath());
        TextUtil.setText(holder.storyTitle, item.title, context.getString(R.string.unknown_story));
        TextUtil.setText(holder.storyDescription, item.description, context.getString(R.string.no_description));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
