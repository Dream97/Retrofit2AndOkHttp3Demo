package com.example.deepin.myapplication.listener;

import com.example.deepin.myapplication.model.bean.BookBean;

public interface MainListener {
    void onSuccess(BookBean bookBean);
    void onfail();
}
