package com.example.administrator.yoursecret.Comment;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
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
            datas.add(new Comment());
        }
        return datas;
    }

    public void loadComments(){

        ApplicationDataManager.getInstance().getNetworkManager().getUserComments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
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
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }


}
