package com.example.administrator.yoursecret.Recieve.Category;

import com.example.administrator.yoursecret.Entity.Artical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16.
 */

public class CategoryDataManager {

    private static CategoryDataManager instance;

    private CategoryDataManager(){}

    public static CategoryDataManager getInstance(){
        if(instance==null){
            instance = new CategoryDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }


    private CategoryAdapter adapter;
    private List<String> titles;
    private Map<String,List<Artical>> datas;

    private String categoryType;

    public void setCategoryType(String categoryType){
        this.categoryType = categoryType;
    }

    public CategoryAdapter getAdapter() {
        if(adapter==null){
            adapter = new CategoryAdapter();
            adapter.setDatas(getDatas(),getTitles());

        }
        return adapter;
    }

    public void loadMore(){

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
}
