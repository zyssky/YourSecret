package com.example.administrator.yoursecret.Record;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordPresenter implements RecordContract.Presenter {
    private RecordContract.View view;
    private RecordContract.Model model;

    public RecordPresenter(RecordContract.View view){
        this.view = view;
        model = new RecordData();
    }

    @Override
    public void setRecyclerViewAdapter() {
        RecordsAdapter adapter = new RecordsAdapter();
        adapter.setDatas(model.getDatas(),model.getTitles());
        view.setRecyclerViewAdapter(adapter);
    }
}
