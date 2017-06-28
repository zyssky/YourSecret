package com.example.administrator.yoursecret.Discover;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by inj3ct0r on 2017/6/26.
 */

//Define Multiple Ways to get Artical from Remote Server.

public class ArticlalGetter {

    public ArrayList<Artical> Articals;
    public ArticlalGetter(){

        //Stretch Articles from Network Service
        NetworkManager networkManager = ApplicationDataManager.getInstance().getNetworkManager();//Acquire a network manager.
        Observer<ArrayList<Artical>> article_observer = new Observer<ArrayList<Artical>>() {
            //Implement an Observer to get data.
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> articals) {
                Articals = articals;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.print(e);
                System.out.print("Error Here!");
            }

            @Override
            public void onComplete() {
                System.out.println("Transmit complete.");
                for(Artical artical : Articals){
                    System.out.println(artical.articalHref);
                }
            }
        };
        networkManager.getArticalsOnMap(article_observer,0);
    }

}


