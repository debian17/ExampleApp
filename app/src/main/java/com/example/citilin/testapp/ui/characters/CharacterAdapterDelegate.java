package com.example.citilin.testapp.ui.characters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.citilin.testapp.R;
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;

import java.util.List;

/**
 * Created by Andrey on 03-Sep-17.
 */

class CharacterAdapterDelegate extends AdapterDelegate<List<Object>> {

    private LayoutInflater layoutInflater;
    private OnIconClick onIconClick;

    interface OnIconClick {
        void onClick(View v, Character character);
    }

    CharacterAdapterDelegate(Activity activity, OnIconClick onIconClick) {
        layoutInflater = activity.getLayoutInflater();
        this.onIconClick = onIconClick;
    }

    @Override
    protected boolean isForViewType(@NonNull List<Object> items, int position) {
        return items.get(position) instanceof Character;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new CharacterViewHolder(layoutInflater.inflate(R.layout.heroes_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<Object> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder,
                                    @NonNull List<Object> payloads) {

        CharacterViewHolder characterViewHolder = (CharacterViewHolder) holder;
        Character character = (Character) items.get(position);

        Glide.with(holder.itemView)
                .load(character.getImagePath())
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.stub)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(characterViewHolder.characterImage);

        characterViewHolder.characterName.setText(character.name);

        characterViewHolder.characterImage.setOnClickListener(v -> onIconClick.onClick(v, character));
    }

    private class CharacterViewHolder extends RecyclerView.ViewHolder {

        TextView characterName;
        ImageView characterImage;

        CharacterViewHolder(View itemView) {
            super(itemView);
            characterName = (TextView) itemView.findViewById(R.id.tvHeroName);
            characterImage = (ImageView) itemView.findViewById(R.id.imgHeroIcon);
        }
    }
}
