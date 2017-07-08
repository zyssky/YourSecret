package com.example.administrator.yoursecret.Module.Discover;

import com.example.administrator.yoursecret.Entity.Artical;

/**
 * Created by Administrator on 2017/7/8.
 */

public class DiscoverDataManager {
    private DiscoverObserver mObserver;

    private Artical toBeShowArtical;

    public void setmObserver(DiscoverObserver observer){
        mObserver = observer;
    }

    public void tryToShowSelectedArtical(){
        if(mObserver!=null && toBeShowArtical!=null) {
            mObserver.showArtical(toBeShowArtical);
            toBeShowArtical = null;
        }
    }

    public void setToShowArtical(Artical artical) {
        toBeShowArtical = artical;
    }
}
