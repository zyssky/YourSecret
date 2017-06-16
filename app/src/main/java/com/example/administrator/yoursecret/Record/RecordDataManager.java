package com.example.administrator.yoursecret.Record;

import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RecordDataManager {

    public RecordDataManager(){}


    private List<String> titles;
    private Map<String,List<Artical>> datas;
    private RecordsAdapter adapter;

    public RecordsAdapter getAdapter() {
        if(adapter==null){
            adapter = new RecordsAdapter();
            addCatogory(AppContants.RECORD_CATOGORY_TEMP);
            addCatogory(AppContants.RECORD_CATOGORY_HISTORY);
            adapter.setDatas(getDatas(),getTitles());

        }
        return adapter;
    }

    public void saveTempArtical(Artical artical){
        if(adapter==null){
            getAdapter();
        }
        getDatas().get(AppContants.RECORD_CATOGORY_TEMP).add(artical);
        adapter.notifyDataSetChanged();

    }

    public void saveFinishArtical(Artical artical){
        if(adapter==null){
            getAdapter();
        }
        getDatas().get(AppContants.RECORD_CATOGORY_HISTORY).add(artical);
        adapter.notifyDataSetChanged();
    }

    private List<String> getTitles() {
        if(titles==null){
            titles = new ArrayList<>();
        }
        return titles;
    }

    private Map<String, List<Artical>> getDatas() {
        if(datas==null){
            datas = new HashMap<>();
        }
        return datas;
    }

    private void addCatogory(String title){
        getTitles().add(title);
        getDatas().put(title,new ArrayList<Artical>());
    }

    public Artical getArtical(KV kv){
        return datas.get(kv.key).get(kv.value);
    }

    public void removeArtical(KV kv){
        datas.get(kv.key).remove(kv.value);
    }
}
