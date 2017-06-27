package com.example.administrator.yoursecret.Discover;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;

/**
 * Created by inj3ct0r on 2017/6/26.
 */

//Define Multiple Ways to get Artical from Remote Server.


public class ArticlalGetter {

    Map<String,ArrayList<Artical>> ArticleMapper;
    public ArticlalGetter(){

        //Stretch Articles from Network Service
        NetworkManager networkManager = ApplicationDataManager.getInstance().getNetworkManager();//Acquire a network manager.
        Observable<Map<String,ArrayList<Artical>>> article_observer = networkManager.getArticals();
        //Implement an Observer to get data.

    }
}
