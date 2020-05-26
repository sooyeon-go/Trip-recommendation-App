package com.example.ssadola;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudioFragmentAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<StudioFragment> fragments;
    private float baseElevation;

    public StudioFragmentAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;

        for(int i = 0; i< 8; i++){
            addCardFragment(new StudioFragment());
        }
    }

   @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return StudioFragment.getInstance(position);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (StudioFragment) fragment);
        return fragment;
    }

    public void addCardFragment(StudioFragment fragment) {
        fragments.add(fragment);
    }

}
