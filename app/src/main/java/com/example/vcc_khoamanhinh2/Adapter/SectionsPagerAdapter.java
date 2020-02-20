package com.example.vcc_khoamanhinh2.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList;
    public SectionsPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentList.get(0);
            case 1:
                return fragmentList.get(1);
            default:
                return fragmentList.get(0);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
