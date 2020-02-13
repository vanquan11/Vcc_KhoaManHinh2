package com.example.vcc_khoamanhinh2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMain  extends Fragment implements RetrofitAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener{
    public RecyclerView rvListImage;
    public static final String client_id = "d0922c4e1ab6e3b4b611103f7c318c9bd3ebd9db42cc06cdd7afe7edea350078";
    private int page = 1;
    private int per_page = 10;
    private boolean loading;
    private boolean search;
    private String query;
    public List<Image> imageList;
    private RetrofitAdapter adapter;
    public GridLayoutManager gridLayoutManager;
    private boolean isLoadMore;
    private ArrayList<Image> imgList = new ArrayList<>();

    public boolean isSearch() {
        return search;
    }

    public void setSearch(boolean search) {
        this.search = search;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        initView(view);
        imageList = new ArrayList<>();
        getImage(client_id, page, per_page);
        adapter = new RetrofitAdapter(getContext(), imageList);
        adapter.setListener(this);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case 2:
                        return 1;
                    case 1:
                        return 2;
                    default:
                        return -1;
                }
            }
        });
        rvListImage.setLayoutManager(gridLayoutManager);
        rvListImage.setAdapter(adapter);

        rvListImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (loading)
                    return;

                int currentPosition = gridLayoutManager.findLastVisibleItemPosition();
                int size = adapter.getItemCount();
                if (currentPosition < size - 2) {
                    page++;
                    loading = true;
                    adapter.addLoadMore();
                    loading = true;
                    if (search){
                        loadMoreSearch();
                    }else {
                        loadMore();
                    }
                }
            }
        });

        return view;
    }


    private void loadMore() {
        isLoadMore = true;
        getImage(client_id, page, per_page);
    }

    private void loadMoreSearch() {
        search(this.getQuery());
    }

    private void initView(View view) {
        rvListImage = view.findViewById(R.id.recyclerBackground);
    }


    private void getImage(String client_id, int page, int per_page) {
        RetrofitData.getInstance().getImage(client_id, page, per_page).enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, final Response<List<Image>> response) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadMore();
                        if (isLoadMore) {
                            imgList.addAll(response.body());
                            isLoadMore = false;
                            adapter.addData(response.body());
                        } else {
                            imgList.clear();
                            imgList.addAll(response.body());
                            adapter.setData(response.body());
                        }
                        loading = false;
                    }
                }, 100);
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                    loading = false;
            }
        });
    }

    public void search(String query) {
        if (this.getQuery() != null){
            RetrofitData.getInstance().getSearchImage(client_id, page, per_page, query).enqueue(new Callback<Search>() {
                @Override
                public void onResponse(Call<Search> call, final Response<Search> response) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.removeLoadMore();
//                            imgList = convertToCommonDats(response.body().getResults());
//                            adapter.addData(imgList);
                            if (isLoadMore) {
                                imgList.addAll(convertToCommonDats(response.body().getResults()));
                                isLoadMore = false;
                                adapter.addData(imgList);
                            } else {
                                imgList.clear();
                                imgList.addAll(convertToCommonDats(response.body().getResults()));
                                adapter.setData(imgList);
                            }
                            loading = false;
                        }
                    }, 100);
                }

                @Override
                public void onFailure(Call<Search> call, Throwable t) {
                    loading = false;
                }
            });
            this.setQuery(null);
            this.setSearch(false);
        }
    }

    @Override
    public void OnItemClick(String displayurl, String downloadurl, int position) {
        initPremission();
        Intent intent = new Intent(getContext(), DetailsImageActivity.class);
        intent.putExtra("show", displayurl);
        intent.putExtra("down", downloadurl);
        intent.putExtra("position", position);
        intent.putExtra("arrList",imgList);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void initPremission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                }else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1000);
                }
            }
        }
    }

    private ArrayList<Image> convertToCommonDats(List<Result> list) {
        ArrayList<Image> cOmmonImages = new ArrayList<>();
        for (Result image : list) {
            Image cOmmonImage = new Image();
            cOmmonImage.setUrls(image.getUrls());
            cOmmonImages.add(cOmmonImage);
        }
        return cOmmonImages;
    }
}
