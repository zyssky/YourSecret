package com.example.administrator.yoursecret.Record;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Comment;
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
    private RecordObserver mObserver;
    private boolean hasInited = false;

    public void setObserver(RecordObserver observer){
        mObserver = observer;
    }



    public RecordsAdapter getAdapter() {
        if(adapter==null){
            adapter = new RecordsAdapter();
            addCatogory(AppContants.RECORD_CATOGORY_TEMP);
            addCatogory(AppContants.RECORD_CATOGORY_HISTORY);
            adapter.setDatas(getDatas(),getTitles());
        }
        hasInited = true;
        return adapter;
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

    public Artical getArtical(KV kv){
        return datas.get(kv.key).get(kv.value);
    }



    private void addCatogory(String title){
        getTitles().add(title);
        getDatas().put(title,new ArrayList<Artical>());
    }

    public Artical removeArtical(KV kv){
        Artical artical = datas.get(kv.key).remove(kv.value);
        adapter.notifyDataSetChanged();
        return artical;
    }

//    public void deleteFinishedArtical(Artical artical){
//        datas.get(AppContants.RECORD_CATOGORY_HISTORY).remove(artical);
//    }

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



    public void checkNewComment(){
        if(!App.getInstance().getUserManager().hasUnReadMessage()) {
            String lastDate = App.getInstance().getUserManager().getLastCommentDate();
            App.getInstance().getNetworkManager().getUserComments(lastDate)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Comment>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull List<Comment> list) {
                            if(!list.isEmpty()){
                                AppDatabaseManager.addComments(list);
                                String date = ""+list.get(0).date;
                                App.getInstance().getUserManager().sethasUnReadMessage(true);
                                App.getInstance().getUserManager().setLastCommentDate(date);
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            if(App.getInstance().getUserManager().hasUnReadMessage()){
                                mObserver.notifyNewMsg();
                            }
                            else{
                                mObserver.notifyNoMsg();
                            }
                        }
                    });
        }
        else{
            mObserver.notifyNewMsg();
        }
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
            if(articals.get(i).uuid != null && articals.get(i).uuid.equals(articalClientUuid)) {
                Artical artical = articals.get(i);
                artical.articalHref = articalHref;
                artical.imageUri = imageUri;
                return;
            }
        }
    }

    public OnSwipeListener getOnSwipeListener(){
        return new OnSwipeListener() {
            @Override
            public void onSwipe(int position) {
                KV kv = adapter.getLocation(position);
                Artical artical = datas.get(kv.key).remove(kv.value);
                adapter.notifyItemRangeRemoved(position,5);

                if(artical.finished == 0) {
                    AppDatabaseManager.deleteArtical(artical.uuid);
                    return;
                }

                if(artical.finished == 1) {
                    String token = App.getInstance().getUserManager().getToken();

                    App.getInstance().getNetworkManager().deleteArtical(token, artical.articalHref)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(App.getInstance().getNetworkMonitor().getDeleteArticalObserver(artical));
                }

            }
        };
    }

    public void refresh(){
        if(!hasInited){
            return;
        }
        datas.get(AppContants.RECORD_CATOGORY_TEMP).clear();
        datas.get(AppContants.RECORD_CATOGORY_HISTORY).clear();

        Observer<List<Artical>> observerTemp = new Observer<List<Artical>>() {
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

        Observer<List<Artical>> observerFinished = new Observer<List<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Artical> articals) {
                if(articals==null || articals.isEmpty()){
                    String token = App.getInstance().getUserManager().getToken();
                    App.getInstance().getNetworkManager().getUserArticals(token)
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

        AppDatabaseManager.getTempArticals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerTemp);

        AppDatabaseManager.getFinishedArticals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observerFinished);

    }

    public interface OnSwipeListener{
        void onSwipe(int position);
    }
}
