package com.example.deepin.myapplication.main;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.deepin.myapplication.R;
import com.example.deepin.myapplication.base.BaseActivity;
import com.example.deepin.myapplication.main.mvp.MainContract;
import com.example.deepin.myapplication.main.mvp.MainPresenter;
import com.example.deepin.myapplication.model.bean.BookBean;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {


    private Button button;

    @Override
    protected void init() {
        presenter = new MainPresenter(this);
        button = findViewById(R.id.getdata_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData();
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void showResult(BookBean bookBean) {
        Toast.makeText(this,"书名:"+bookBean.getTitle()+ "出版社："+bookBean.getPublisher(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFail(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
