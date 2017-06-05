package com.example.administrator.yoursecret.Record;

import android.support.v7.widget.RecyclerView;

import com.example.administrator.yoursecret.MetaData.Record;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordContract {
    interface View{
        void setRecyclerViewAdapter(RecyclerView.Adapter adapter);
    }

    interface Model{
        List<String> getTitles();
        Map<String,List<Record>> getDatas();
    }

    interface Presenter{
        void setRecyclerViewAdapter();
    }
}
