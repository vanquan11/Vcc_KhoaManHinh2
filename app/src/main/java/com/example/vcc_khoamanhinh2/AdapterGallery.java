package com.example.vcc_khoamanhinh2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AdapterGallery extends RecyclerView.Adapter<AdapterGallery.ViewHolder> {

    ArrayList<String> f;
    Context context;

    public AdapterGallery(ArrayList<String> f, Context context) {
        this.f = f;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fm_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
        holder.img_local.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        return f.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_local;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_local = itemView.findViewById(R.id.retrofit_image_view);
        }
    }

}
