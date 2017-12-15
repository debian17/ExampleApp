package com.example.citilin.testapp.ui.characters.character;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CustomPagerAdapter extends PagerAdapter {

    private Context context;
    private CustomPagerEnum customPagerEnum;

    public CustomPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), container, false);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        customPagerEnum = CustomPagerEnum.values()[position];
        return context.getString(customPagerEnum.getTitleResId());
    }
}
