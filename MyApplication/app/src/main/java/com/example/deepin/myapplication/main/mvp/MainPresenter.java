package com.example.deepin.myapplication.main.mvp;

import com.example.deepin.myapplication.base.BasePresenter;
import com.example.deepin.myapplication.listener.MainListener;
import com.example.deepin.myapplication.main.MainActivity;
import com.example.deepin.myapplication.model.MainModel;
import com.example.deepin.myapplication.model.bean.BookBean;

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.Presenter,MainListener{

    private MainModel mainModel;
    public MainPresenter(MainActivity mainActivity){
        this.view = mainActivity;
        mainModel = new MainModel();
    }
    @Override
    public void getData() {
        mainModel.getData("1200840",this);
    }

    @Override
    public void onSuccess(BookBean bookBean) {
        view.showResult(bookBean);

    }

    @Override
    public void onfail() {
        view.showFail("获取数据失败");
    }
}
