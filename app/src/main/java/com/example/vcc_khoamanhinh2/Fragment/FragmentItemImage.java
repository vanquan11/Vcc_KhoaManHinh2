package com.example.vcc_khoamanhinh2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.vcc_khoamanhinh2.R;

public class FragmentItemImage extends Fragment {

    ImageView imageView;
    String str;

    public FragmentItemImage(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_type_one, container, false);
        imageView = view.findViewById(R.id.imgGallery);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Glide.with(getContext()).load(this.getStr()).into(imageView);
    }
}
