package com.example.vcc_khoamanhinh2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vcc_khoamanhinh2.Fragment.FragmentItemImage;
import com.example.vcc_khoamanhinh2.Model.Image;

import java.util.ArrayList;

public class AdapterViewPagerImage  extends FragmentPagerAdapter {
    private  ArrayList<Image> urls;

    public AdapterViewPagerImage(@NonNull FragmentManager fm, ArrayList<Image> urls) {
        super(fm);
        this.urls = urls;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new FragmentItemImage(urls.get(position).getUrls().getRegular());
    }

    @Override
    public int getCount() {
        return urls.size();
    }
}
