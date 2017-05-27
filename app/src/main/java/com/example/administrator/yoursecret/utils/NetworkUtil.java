package com.example.administrator.yoursecret.utils;

import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/11.
 */

public class NetworkUtil {
    private RxNetworkOperationListener listener;
    private static NetworkUtil mInstance;
    public static final String TAG = NetworkUtil.class.getSimpleName();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static NetworkUtil create(RxNetworkOperationListener listener){
        mInstance = new NetworkUtil(listener);
        return mInstance;
    }

    private NetworkUtil(RxNetworkOperationListener listener){
        this.listener = listener;
    }

    public void start(){
        disposables.clear();
        disposables.add(getObservable()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onComplete() {
                        Log.d(TAG, "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override public void onNext(String string) {
                        listener.onCompleteTask(string);
                    }
                }));

    }

    public Observable<String> getObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
//                SystemClock.sleep(1000);
                String result = listener.onCreateTask();
                if(result == null)
                    result = new String();

                return Observable.just(result);
            }
        });
    }

    public interface RxNetworkOperationListener{
        String onCreateTask();
        void onCompleteTask(String content);
    }
}
