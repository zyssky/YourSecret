package com.example.administrator.yoursecret.Recieve;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Service.LocationService;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/16.
 */

public class RecieveDataManager {

    private List<String> titles;
    private Map<String,List<Artical>> datas;

    private RecieveRecyclerAdapter adapter;

    public RecieveRecyclerAdapter getAdapter() {
        if(adapter==null){
            adapter = new RecieveRecyclerAdapter();
            addCatogory(AppContants.ARTICAL_CATOGORY_HOT);
            addCatogory(AppContants.ARTICAL_CATOGORY_GOOD);
            addCatogory(AppContants.ARTICAL_CATOGORY_PUSH);
            addCatogory(AppContants.ARTICAL_CATOGORY_OUTSIDE);
            adapter.setDatas(getDatas(),getTitles());

        }
        return adapter;
    }

    private OnRefreshChangeListener listener;

    public void setListener(OnRefreshChangeListener listener){
        this.listener = listener;
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
        if(getDatas().containsKey(title)){
            return;
        }
        getTitles().add(title);
        getDatas().put(title,new ArrayList<Artical>());
    }

    public Artical getArtical(KV kv){
        return datas.get(kv.key).get(kv.value);
    }

    public String getArticalHref(KV kv){
        Artical artical = getArtical(kv);
        return artical.articalHref;
    }

    public void addArtical(String title,Artical artical){
        addCatogory(title);
        getDatas().get(title).add(artical);
        adapter.notifyDataSetChanged();
    }

    public void addArticals(Map<String, ArrayList<Artical>> stringArrayListMap) {
//        datas.get(AppContants.ARTICAL_CATOGORY_HOT).addAll(stringArrayListMap.get());
        datas.putAll(stringArrayListMap);
        adapter.notifyDataSetChanged();
    }

    public void refresh(){
        Observable<Map<String, ArrayList<Artical>>> observable = ApplicationDataManager.getInstance().getNetworkManager().getArticals();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, ArrayList<Artical>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("subscribe ", "onSubscribe: ");
                        listener.changeRefreshStatus(true);
                    }

                    @Override
                    public void onNext(@NonNull Map<String, ArrayList<Artical>> stringArrayListMap) {
                        addArticals(stringArrayListMap);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        listener.changeRefreshStatus(false);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("success recieve ", "onComplete: ");
                        listener.changeRefreshStatus(false);

                    }
                });
    }

    private void getLocation(){
//        final LocationService.MyBinder myBinder = null;
//        ServiceConnection connection = new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName name, IBinder service) {
//                myBinder = (LocationService.MyBinder) service;
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName name) {
//            }
//        };
    }

}
