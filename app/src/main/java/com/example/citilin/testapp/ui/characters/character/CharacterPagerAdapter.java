package com.example.citilin.testapp.ui.characters.character;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16-Aug-17.
 */

class CharacterPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();

    private List<String> fragmentTitleList = new ArrayList<>();

    private int characterId;

    CharacterPagerAdapter(FragmentManager fm, int characterId) {
        super(fm);
        this.characterId = characterId;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);

        //один раз во все фрагменты
        Bundle bundle = new Bundle();
        bundle.putInt(CharacterTabInfoActivity.CHARACTER_ID, characterId);
        fragment.setArguments(bundle);
    }

}
