package com.example.administrator.yoursecret.Recieve;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecievePresenter implements RecieveContract.Presenter {

    private RecieveContract.View view;
    private RecieveContract.Model model;

    public RecievePresenter(RecieveContract.View view){
        this.view = view;
        model = new RecieveTestData();
    }

    @Override
    public void setRecyclerViewAdapter() {
        RecieveRecyclerAdapter adapter = new RecieveRecyclerAdapter();
        adapter.addDatas(model.getInitDatas());
        view.setRecyclerViewAdapter(adapter);
    }
}
