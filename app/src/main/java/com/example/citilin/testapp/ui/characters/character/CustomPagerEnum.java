package com.example.citilin.testapp.ui.characters.character;

import com.example.citilin.testapp.R;

enum CustomPagerEnum {

    RED(R.string.red, R.layout.view_red),
    BLACK(R.string.black, R.layout.view_black),
    GREEN(R.string.green, R.layout.view_green);

    private int titleResId;
    private int layoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        this.titleResId = titleResId;
        this.layoutResId = layoutResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }
}
