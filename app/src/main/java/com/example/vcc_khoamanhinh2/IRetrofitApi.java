package com.example.vcc_khoamanhinh2;

import com.example.vcc_khoamanhinh2.Model.Image;
import com.example.vcc_khoamanhinh2.Model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofitApi {

    @GET("/photos")
    Call<List<Image>> getImage(@Query("client_id") String client_id, @Query("page") int page, @Query("per_page") int per_page);

    @GET("search/photos")
    Call<Search> getSearchImage(@Query("client_id") String client_id, @Query("page") int page, @Query("per_page") int per_page, @Query("query") String query);
}
