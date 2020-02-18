package com.example.vcc_khoamanhinh2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class RetrofitAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Image> imageList;
    private OnItemClickListener listener;

    public RetrofitAdapter(Context context, List<Image> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    public void setData(List<Image> images) {
        if (images != null && images.size() > 0) {
            imageList.clear();
            imageList.addAll(images);
            notifyDataSetChanged();
        }
    }

    public void addData(List<Image> images) {
        if (images != null && images.size() > 0) {
            int oldSize = imageList.size();
            imageList.addAll(images);
            notifyItemRangeChanged(oldSize, images.size());
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constains.ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_fm_recycler, parent, false);
            return new ImageHolder(view);
        } else if (viewType == Constains.LOAD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_load, parent, false);
            return new Loadholder(view);
        } else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageHolder) {
            final Image pos = imageList.get(position);
            Glide.with(context).load(pos.getUrls().getSmall()).into(((ImageHolder) holder).img_lock);
            ((ImageHolder) holder).img_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mposition = position;
                    listener.OnItemClick(pos.getUrls().getRegular(), pos.getUrls().getFull(), mposition);
                }
            });
        } else if (holder instanceof Loadholder) {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (imageList.get(position) == null) {
            return Constains.LOAD;
        } else return Constains.ITEM;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addLoadMore() {
        imageList.add(null);
    }

    public void removeLoadMore() {
        if (imageList == null || imageList.size() == 0)
            return;
        if (imageList.get(imageList.size() - 1) == null) {
            imageList.remove(imageList.size() - 1);
            notifyItemRemoved(imageList.size() - 1);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(String displayurl, String downloadurl, int position);
    }

    public class ImageHolder extends RecyclerView.ViewHolder  {
        public ImageView img_lock;
        public ImageHolder(View itemView) {
            super(itemView);
            img_lock = itemView.findViewById(R.id.retrofit_image_view);
        }
    }

    public class Loadholder  extends RecyclerView.ViewHolder  {
        ProgressBar proLoad;
        public Loadholder(@NonNull View itemView) {
            super(itemView);
            proLoad = itemView.findViewById(R.id.progress_loading);
        }
    }
}



