package com.example.vcc_khoamanhinh2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;


public class FragmentGallery extends Fragment {

    ArrayList<String> f = new ArrayList<String>();
    File[] listFile;
    private RecyclerView rvListdownload;
    private AdapterGallery adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initView(view);
        getFromSdcard();

        adapter = new AdapterGallery(f, getContext());
        rvListdownload.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvListdownload.setAdapter(adapter);

        return view;
    }

    public void getFromSdcard() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                f = new ArrayList<>();
                File file = new File(Environment.getExternalStorageDirectory(), "KhoaManHinh");
                if (file.isDirectory()) {
                    listFile = file.listFiles();
                    for (int i = 0; i < listFile.length; i++) {
                        f.add(listFile[i].getAbsolutePath());
                    }
                }
                adapter = new AdapterGallery(f, getContext());
                rvListdownload.setLayoutManager(new GridLayoutManager(getContext(), 2));
                rvListdownload.setAdapter(adapter);
            }
        }, 300);

    }

    private void initView(View view) {
        rvListdownload = view.findViewById(R.id.recyclerMine);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getFromSdcard();
    }
}
