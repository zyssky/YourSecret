package com.example.administrator.yoursecret.Recieve;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecievePresenter implements RecieveContract.Presenter {

    private RecieveContract.View view;
    private RecieveContract.Model model;

    public RecievePresenter(RecieveContract.View view){
        this.view = view;
        RecieveData.initData();
        model = RecieveData.getInstance();
    }

    @Override
    public void setRecyclerViewAdapter() {
        RecieveRecyclerAdapter.init(model.getDatas(),model.getTitles());
        view.setRecyclerViewAdapter();
    }
}
