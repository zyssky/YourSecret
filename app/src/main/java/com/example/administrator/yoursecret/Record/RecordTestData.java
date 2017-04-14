package com.example.administrator.yoursecret.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordTestData implements RecordContract.Model {
    @Override
    public List<Record> getInitDatas() {
        List<Record> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Record("today's mood "+i));
        }
        return list;
    }
}
