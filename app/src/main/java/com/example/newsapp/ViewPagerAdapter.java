package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new NationalNews();
                break;
            case 1:
                fragment = new WorldFragment();
                break;
            case 2:
                fragment = new BusinessNews();

        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
