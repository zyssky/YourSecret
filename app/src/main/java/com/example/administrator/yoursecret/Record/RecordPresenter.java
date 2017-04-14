package com.example.administrator.yoursecret.Record;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordPresenter implements RecordContract.Presenter {
    private RecordContract.View view;
    private RecordContract.Model model;

    public RecordPresenter(RecordContract.View view){
        this.view = view;
        model = new RecordTestData();
    }

    @Override
    public void setRecyclerViewAdapter() {
        RecordsAdapter adapter = new RecordsAdapter(model.getInitDatas());
        view.setRecyclerViewAdapter(adapter);
    }
}
