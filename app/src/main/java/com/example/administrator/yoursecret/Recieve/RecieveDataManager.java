package com.example.administrator.yoursecret.Recieve;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/16.
 */

public class RecieveDataManager {

    private List<String> titles;

    private Map<String,List<Artical>> datas;

    private RecieveRecyclerAdapter adapter;

    private ReceiveObserver mobserver;

    public void setObserver(ReceiveObserver observer){
        this.mobserver = observer;
    }




    public RecieveRecyclerAdapter getAdapter() {
        if(adapter==null){
            adapter = new RecieveRecyclerAdapter();

            addCatogory(AppContants.ARTICAL_CATOGORY_OUTSIDE);
            addCatogory(AppContants.ARTICAL_CATOGORY_PUSH);
            addCatogory(AppContants.ARTICAL_CATOGORY_GOOD);
            addCatogory(AppContants.ARTICAL_CATOGORY_HOT);

            adapter.setDatas(getDatas(),getTitles());

        }
        return adapter;
    }

    private List<String> getTitles() {
        if(titles==null){
            titles = new ArrayList<>();
        }
        return titles;
    }

    public Map<String, List<Artical>> getDatas() {
        if(datas==null){
            datas = new HashMap<>();
        }
        return datas;
    }

    public Artical getArtical(KV kv){
        return datas.get(kv.key).get(kv.value);
    }




    private void addCatogory(String title){
        if(getDatas().containsKey(title)){
            return;
        }
        getTitles().add(0,title);
        getDatas().put(title,new ArrayList<Artical>());
    }

    public void addArticals(Map<String, ArrayList<Artical>> stringArrayListMap) {
        for (Artical artical :
                stringArrayListMap.get(AppContants.ARTICAL_CATOGORY_OUTSIDE)) {
            artical.isOutside = 1;
        }
        datas.putAll(stringArrayListMap);
        adapter.notifyDataSetChanged();
        List<Artical> toBeCache = new ArrayList<>();
        Set<String> keys = stringArrayListMap.keySet();
        for (String key :
                keys) {
            toBeCache.addAll(stringArrayListMap.get(key));
        }
        AppDatabaseManager.addCacheArticals(toBeCache);
    }

    public void refresh(){

        final Observer<Map<String, ArrayList<Artical>>> observer= new Observer<Map<String, ArrayList<Artical>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("subscribe ", "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Map<String, ArrayList<Artical>> stringArrayListMap) {
                addArticals(stringArrayListMap);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                mobserver.removeLoading();
                mobserver.showErrorToast();
            }

            @Override
            public void onComplete() {
                Log.d("success recieve ", "onComplete: ");
                FoundationManager.setLastRreshDate(new Date());
                mobserver.removeLoading();
                mobserver.showCorrectToast();
            }
        };

        App.getInstance().getNetworkManager().getArticals(observer);
    }

    public void loadCache() {
        AppDatabaseManager.getCacheArticals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Artical>>() {
                    @Override
                    public void accept(@NonNull List<Artical> articals) throws Exception {
                        for (Artical artical :
                                articals) {
                            if (artical.isOutside != 0) {
                                datas.get(AppContants.ARTICAL_CATOGORY_OUTSIDE).add(artical);
                                continue;
                            }
                            if (artical.articalType.equals(AppContants.ARTICLE_TYPE_HOT)) {
                                datas.get(AppContants.ARTICAL_CATOGORY_HOT).add(artical);
                                continue;
                            }
                            if (artical.articalType.equals(AppContants.ARTICLE_TYPE_ARTICLE)) {
                                datas.get(AppContants.ARTICAL_CATOGORY_GOOD).add(artical);
                                continue;
                            }
                            if (artical.articalType.equals(AppContants.ARTICLE_TYPE_NOTICE)) {
                                datas.get(AppContants.ARTICAL_CATOGORY_PUSH).add(artical);
                                continue;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
