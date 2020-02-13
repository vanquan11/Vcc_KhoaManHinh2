package com.example.vcc_khoamanhinh2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitData {

    public static IRetrofitApi lockImage;
    public static IRetrofitApi getInstance(){
        if (lockImage == null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.unsplash.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            lockImage = retrofit.create(IRetrofitApi.class);
        }
        return lockImage;
    }
}
