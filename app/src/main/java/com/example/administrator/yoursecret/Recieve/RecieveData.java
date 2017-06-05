package com.example.administrator.yoursecret.Recieve;

import com.example.administrator.yoursecret.MetaData.PushMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveData implements RecieveContract.Model {

    private List<String> titles;
    private Map<String,List<PushMessage>> datas;
    private static RecieveData instance;
    private RecieveData(){
        titles = new ArrayList<>();
        titles.add("热点");
        titles.add("风景");
        titles.add("事物");
        titles.add("人物");
        titles.add("感情");
        titles.add("附近");
        datas = new HashMap<>();
        List<PushMessage> list = new ArrayList<>();
        list.add(new PushMessage());
        list.add(new PushMessage());
        datas.put("热点",list);
        datas.put("风景",list);
        datas.put("事物",list);
        datas.put("人物",list);
        datas.put("感情",list);
        datas.put("附近",list);
    }
    public static RecieveData getInstance(){
        return instance;
    }

    public static void initData(){
        instance = new RecieveData();
    }

    @Override
    public List<String> getTitles() {
        return titles;
    }

    @Override
    public Map<String, List<PushMessage>> getDatas() {
        return datas;
    }
}
