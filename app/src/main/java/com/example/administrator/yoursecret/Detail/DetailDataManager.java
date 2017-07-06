package com.example.administrator.yoursecret.Detail;

import android.util.Log;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/16.
 */

public class DetailDataManager {
    public String TAG = DetailDataManager.class.getSimpleName();
    private static DetailDataManager instance;

    private DetailDataManager(){}

    public static DetailDataManager getInstance(){
        if(instance==null){
            instance = new DetailDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }


    private List<Comment> list;
    private CommentRecyclerAdapter adapter;
    private DetailObserver mObserver;

    public void setObserver(DetailObserver observer){
        mObserver = observer;
    }

    public Artical artical;

    private int commentPage = 1;

    public CommentRecyclerAdapter getAdapter(){
        if(adapter == null){
            adapter = new CommentRecyclerAdapter();
            adapter.setmDatas(getList());
        }
        return adapter;
    }

    private List<Comment> getList(){
        if(list == null){
            list = new ArrayList<>();
        }
        return list;
    }

    public void test(){
        list.add(new Comment());
        adapter.notifyItemInserted(list.size()-1);
    }



    public void getComments() {
        Observer<List<Comment>> observer = new Observer<List<Comment>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull List<Comment> comments) {
                list.addAll(comments);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onComplete: ");
            }
        };

        App.getInstance().getNetworkManager().getComments(0,artical.articalHref)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public String getArticalShareDesc() {
        return artical.title+"\n链接："+artical.articalHref;
    }

    public void sendComment(String content) {
        final Comment comment = new Comment();
        comment.articalHref = artical.articalHref;
        comment.authorId = artical.authorId;
        comment.content = content;
        comment.iconPath = App.getInstance().getUserManager().getIconPath();
        comment.nickName = App.getInstance().getUserManager().getNickName();
        comment.date = new Date().getTime();
        App.getInstance().getNetworkManager().putComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        String result = "";
                        try{
                            result = responseBody.string();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        if(result.equals("success")){
                            list.add(0,comment);
                            adapter.notifyItemInserted(0);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void loadMoreComment() {

        App.getInstance().getNetworkManager().getComments(commentPage++,artical.articalHref)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mObserver.setLoading();
                    }

                    @Override
                    public void onNext(@NonNull List<Comment> list) {
                        addDataList(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mObserver.setNormal();
                    }

                    @Override
                    public void onComplete() {
                        mObserver.setNormal();
                    }
                });
    }

    private void addDataList(List<Comment> list){
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
