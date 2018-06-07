package com.example.deepin.myapplication.model;

import com.example.deepin.myapplication.http.Api;
import com.example.deepin.myapplication.http.helper.RetrofitHelper;
import com.example.deepin.myapplication.listener.MainListener;
import com.example.deepin.myapplication.model.bean.BookBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel {
    public void getData(String s, final MainListener mainListener){
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        mainListener.onSuccess("获取成功");
        RetrofitHelper retrofitHelper = new RetrofitHelper(Api.DOUBAN_BASE_HOST);
        retrofitHelper.getBook(s).enqueue(new Callback<BookBean>() {
            @Override
            public void onResponse(Call<BookBean> call, Response<BookBean> response) {
                mainListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BookBean> call, Throwable t) {
                mainListener.onfail();
            }
        });
    }
}
