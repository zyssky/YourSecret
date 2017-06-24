package com.example.administrator.yoursecret.Record;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RecordDataManager {

    public static final String TAG = RecordDataManager.class.getSimpleName();

    public RecordDataManager(){}


    private List<String> titles;
    private Map<String,List<Artical>> datas;
    private RecordsAdapter adapter;

    private boolean hasRecieveFromNet = false;

    public RecordsAdapter getAdapter() {
        if(adapter==null){
            adapter = new RecordsAdapter();
            addCatogory(AppContants.RECORD_CATOGORY_TEMP);
            addCatogory(AppContants.RECORD_CATOGORY_HISTORY);
            adapter.setDatas(getDatas(),getTitles());

        }
        return adapter;
    }

    public void moveArticalToTemp(String uuid){
        List<Artical> articals = datas.get(AppContants.RECORD_CATOGORY_HISTORY);
        for (int i = 0; i < articals.size(); i++) {
            if(articals.get(i).uuid.equals(uuid)){
                datas.get(AppContants.RECORD_CATOGORY_TEMP).add(0,articals.remove(i));
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void saveTempArtical(Artical artical){
        if(adapter==null){
            getAdapter();
        }
        getDatas().get(AppContants.RECORD_CATOGORY_TEMP).add(0,artical);
        adapter.notifyDataSetChanged();

    }

    public void saveFinishArtical(Artical artical){
        if(adapter==null){
            getAdapter();
        }
        getDatas().get(AppContants.RECORD_CATOGORY_HISTORY).add(0,artical);
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

    public Artical removeArtical(KV kv){
        Artical artical = datas.get(kv.key).remove(kv.value);
        adapter.notifyDataSetChanged();
        return artical;
    }

    public Observer<List<Artical>> getObserverForTemp(){
        Observer<List<Artical>> observer = new Observer<List<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Artical> articals) {
                datas.get(AppContants.RECORD_CATOGORY_TEMP).addAll(articals);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onComplete: ");
            }
        };
        return observer;
    }

    public Observer<List<Artical>> getObserverForFinished(){
        Observer<List<Artical>> observer = new Observer<List<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Artical> articals) {
                if(articals==null || articals.isEmpty()){
                    String token = ApplicationDataManager.getInstance().getUserManager().getToken();
                    ApplicationDataManager.getInstance().getNetworkManager().getUserArticals(token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(getObserverOnNetFetch());
                }
                else {
                    datas.get(AppContants.RECORD_CATOGORY_HISTORY).addAll(articals);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onComplete: ");
            }
        };
        return observer;
    }

    public Observer<List<Artical>> getObserverOnNetFetch(){
        Observer<List<Artical>> observer = new Observer<List<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Artical> articals) {
                datas.put(AppContants.RECORD_CATOGORY_HISTORY,articals);
                AppDatabaseManager.addFinishedArticals(articals);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onComplete: ");
            }
        };
        return observer;
    }

    public void updateArtical(String imageUri, String articalHref, String articalClientUuid) {
        List<Artical> articals = datas.get(AppContants.RECORD_CATOGORY_HISTORY);
        for (int i = 0; i < articals.size(); i++) {
            if(articals.get(i).uuid.equals(articalClientUuid)) {
                Artical artical = articals.get(i);
                artical.articalHref = articalHref;
                artical.imageUri = imageUri;
            }
        }
    }

    public OnSwipeListener getOnSwipeListener(){
        return new OnSwipeListener() {
            @Override
            public void onSwipe(int position) {
                KV kv = adapter.getLocation(position);
                Artical artical = datas.get(kv.key).remove(kv.value);
                adapter.notifyItemRemoved(position);
                if(artical.finished == 0) {
                    AppDatabaseManager.deleteArtical(artical);
                    return;
                }

                if(artical.finished == 1) {
                    String token = ApplicationDataManager.getInstance().getUserManager().getToken();

                    ApplicationDataManager.getInstance().getNetworkManager().deleteArtical(token, artical.articalHref)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ApplicationDataManager.getInstance().getNetworkMonitor().getDeleteArticalObserver(artical));
                }

            }
        };
    }

    public void refresh(){
        datas.get(AppContants.RECORD_CATOGORY_TEMP).clear();
        datas.get(AppContants.RECORD_CATOGORY_TEMP).clear();

        AppDatabaseManager.getTempArticals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserverForTemp());

        AppDatabaseManager.getFinishedArticals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserverForFinished());

    }

    public interface OnSwipeListener{
        void onSwipe(int position);
    }
}
