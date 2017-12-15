package com.example.citilin.testapp.ui.mychracters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.citilin.testapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 22-Aug-17.
 */

public class MyCharacterAdapter extends RecyclerView.Adapter<MyCharacterAdapter.ViewHolder> {

    private ArrayList<MyCharacter> items;
    private Context context;
    private OnClick onClick;

    MyCharacterAdapter(Context context, OnClick onClick) {
        this.context = context;
        this.onClick = onClick;
        items = new ArrayList<>();
    }

    interface OnClick {
        void onEditClick(View v, MyCharacter myCharacter);

        void onDeleteClick(int position, MyCharacter myCharacter);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView myCharacterIcon;
        private TextView myCharacterName;
        private TextView myCharacterSuperPower;
        private Button editMyCharacter;
        private Button deleteMyCharacter;

        ViewHolder(View itemView) {
            super(itemView);

            myCharacterIcon = (ImageView) itemView.findViewById(R.id.imgMyCharacter);
            myCharacterName = (TextView) itemView.findViewById(R.id.tvMyCharacterName);
            myCharacterSuperPower = (TextView) itemView.findViewById(R.id.tvMyCharacterSuperPower);
            editMyCharacter = (Button) itemView.findViewById(R.id.btnEditMyCharacter);
            deleteMyCharacter = (Button) itemView.findViewById(R.id.btnDeleteMyCharacter);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_character_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.deleteMyCharacter
                .setOnClickListener(v -> onClick.onDeleteClick(viewHolder.getAdapterPosition(),
                        items.get(viewHolder.getAdapterPosition())));

        viewHolder.editMyCharacter.setOnClickListener(v -> onClick.onEditClick(v,
                items.get(viewHolder.getAdapterPosition())));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyCharacter item = items.get(position);
        File file = new File(item.getPicturePath());
        Uri uri = Uri.fromFile(file);

        Glide.with(context)
                .load(uri)
                .into(holder.myCharacterIcon);

        holder.myCharacterName.setText(item.getName());

        holder.myCharacterSuperPower.setText(item.getSuperPower());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItems(List<MyCharacter> items) {
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    void cleanAllItems() {
        this.items = new ArrayList<>();
        notifyDataSetChanged();
    }

    void deleteItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    void insertItem(int position, MyCharacter myCharacter){
        items.add(position, myCharacter);
        notifyItemInserted(position);
    }
}
