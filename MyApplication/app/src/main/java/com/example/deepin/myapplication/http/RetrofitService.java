package com.example.deepin.myapplication.http;

import com.example.deepin.myapplication.model.bean.BookBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    /**
     * https://api.douban.com/v2/book/{id}
     * 豆瓣图书信息api
     * get方式
     */
    @GET("v2/book/{id}")
    Call<BookBean> getBook(@Path("id") String id);

}
