package com.example.citilin.testapp.ui.characters.character.event;

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
 * Created by Андрей on 18-Aug-17.
 */

class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {


    private ArrayList<Event> items;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView eventImage;
        private TextView eventTitle;
        private TextView eventDescription;

        ViewHolder(View itemView) {
            super(itemView);

            eventImage = (ImageView) itemView.findViewById(R.id.imgEvent);
            eventTitle = (TextView) itemView.findViewById(R.id.tvEventTitle);
            eventDescription = (TextView) itemView.findViewById(R.id.tvEventDescription);
        }
    }

    EventsAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void replaceAllItems(List<Event> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void addItems(List<Event> items) {
        for (int i = 0; i < items.size(); i++) {
            this.items.add(items.get(i));
        }
        notifyItemRangeInserted(this.items.size(), items.size());
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event item = items.get(position);
        ImageManager.setImage(holder.eventImage, false, item.getImagePath());
        TextUtil.setText(holder.eventTitle, item.title, context.getString(R.string.unknown_event));
        TextUtil.setText(holder.eventDescription, item.description, context.getString(R.string.no_description));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
