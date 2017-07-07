package com.example.administrator.yoursecret.Module.Comment;

import android.util.Log;

import com.example.administrator.yoursecret.Database.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.Comment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CommentDataManager {
    private String TAG = CommentDataManager.class.getSimpleName();
    private static CommentDataManager instance;

    private CommentDataManager(){}

    public static CommentDataManager getInstance(){
        if(instance == null){
            instance = new CommentDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }


    private List<Comment> datas;

    private CommentsAdapter adapter;

    private CommentObserver mObserver;


    public void setObserver(CommentObserver observer){
        mObserver = observer;
    }

    public CommentsAdapter getAdapter(){
        if(adapter == null){
            adapter = new CommentsAdapter();
            adapter.setDatas(getDatas());
        }
        return adapter;
    }

    private List<Comment> getDatas(){
        if(datas==null){
            datas = new ArrayList<>();
        }
        return datas;
    }


    private void updateHint(){
        if(datas.isEmpty()){
            mObserver.showNoCommentHint();
        }
        else{
            mObserver.hideNoCommentHint();
        }
    }

    public void loadComments(){

        final Observer<List<Comment>> observerNetwork = new Observer<List<Comment>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Comment> comments) {
                datas.addAll(0,comments);
                if(datas.size()>0){
                    AppDatabaseManager.addComments(comments);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                updateHint();

                Log.d(TAG, "onError: ");

            }

            @Override
            public void onComplete() {

                updateHint();

                adapter.notifyDataSetChanged();
                Log.d(TAG, "onComplete: ");
            }
        };

        Observer<List<Comment>> observerDatabase = new Observer<List<Comment>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Comment> comments) {
                datas.addAll(comments);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                updateHint();
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                updateHint();

                String lastDate = "0";
                if(!datas.isEmpty()){
                    lastDate = ""+datas.get(0).date;
                }
                App.getInstance().getNetworkManager().getUserComments(lastDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observerNetwork);
            }
        };


        if(App.getInstance().getUserManager().hasLogin()) {
            String authorId = App.getInstance().getUserManager().getPhoneNum();
            AppDatabaseManager.getComments(authorId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observerDatabase);
        }
        else{
            updateHint();
        }
    }


}
