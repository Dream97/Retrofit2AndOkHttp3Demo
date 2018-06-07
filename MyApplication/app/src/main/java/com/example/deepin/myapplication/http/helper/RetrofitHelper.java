package com.example.deepin.myapplication.http.helper;

import com.example.deepin.myapplication.http.RetrofitService;
import com.example.deepin.myapplication.model.bean.BookBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private RetrofitService retrofitService;

    public RetrofitHelper(String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public Call<BookBean> getBook(String id){
        return retrofitService.getBook(id);
    }

    public OkHttpClient getOkHttpClient() {
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
