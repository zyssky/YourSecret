package com.example.administrator.yoursecret.Record;

import com.example.administrator.yoursecret.MetaData.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/14.
 */

public class RecordData implements RecordContract.Model {
    private List<String> titles;
    private Map<String,List<Record>> datas;

    public RecordData(){
        titles = new ArrayList<>();
        titles.add("暂存");
        titles.add("记录");
        datas = new HashMap<>();
        List<Record> list = new ArrayList<>();
        list.add(new Record());
        list.add(new Record());
        datas.put("暂存",list);
        datas.put("记录",list);

    }

    @Override
    public List<String> getTitles() {
        return titles;
    }

    @Override
    public Map<String, List<Record>> getDatas() {
        return datas;
    }
}
