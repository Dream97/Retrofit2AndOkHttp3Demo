package com.example.deepin.myapplication.main.mvp;

import com.example.deepin.myapplication.model.bean.BookBean;

public interface MainContract {
    interface View {
        void showResult(BookBean bookBean);
        void showFail(String s);
    }
    interface Presenter{
        void getData();
    }
}
