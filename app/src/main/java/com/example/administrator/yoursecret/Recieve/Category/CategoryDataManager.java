package com.example.administrator.yoursecret.Recieve.Category;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.utils.KV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/6/16.
 */

public class CategoryDataManager {
    public String TAG = CategoryDataManager.class.getSimpleName();

    private static CategoryDataManager instance;

    private CategoryDataManager(){}

    public static CategoryDataManager getInstance(){
        if(instance==null){
            instance = new CategoryDataManager();
        }
        return instance;
    }

    public static void onDestroy(){
        instance = null;
    }


    private CategoryAdapter adapter;
    private List<String> titles;
    private Map<String,List<Artical>> datas;

    private int pageNo = 1;

    private String categoryType;

    private String newestArticalHref;

    private CategoryObserver mObserver;

    public void setObserver(CategoryObserver observer){
        mObserver = observer;
    }


    public void setCategoryType(String categoryType){
        this.categoryType = categoryType;
        List<Artical> list = App.getInstance().getRecieveDataManager().getDatas().get(categoryType);
        addArticalList(list);
    }

    public CategoryAdapter getAdapter() {
        if(adapter==null){
            adapter = new CategoryAdapter();
            adapter.setDatas(getDatas(),getTitles());
        }
        return adapter;
    }

    public void setDateBackgroungColor(int color){
        getAdapter().setDividerColor(color);
    }



    public Artical getArtical(KV kv){
        return datas.get(kv.key).get(kv.value);
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




    public void loadMore(){
        Observer<ArrayList<Artical>> observer = new Observer<ArrayList<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mObserver.setFooterLoading();
            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> list) {
                CategoryDataManager.getInstance().addArticalList(list);
                if(list.isEmpty()){
                    mObserver.showNoMsgToast();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                mObserver.removeFooterLoading();
            }

            @Override
            public void onComplete() {
                mObserver.removeFooterLoading();
            }
        };
        App.getInstance().getNetworkManager().getArticalsOnType(observer,categoryType, pageNo++);
    }

    public void refresh(){
        Observer<ArrayList<Artical>> observer = new Observer<ArrayList<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> articals) {
                CategoryDataManager.getInstance().addNewArticals(articals);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mObserver.removeLoading();
                mObserver.showErrorToast();
            }

            @Override
            public void onComplete() {
                mObserver.removeLoading();
                mObserver.showNewestToast();
            }
        };

        App.getInstance().getNetworkManager().getArticalsOnType(observer,categoryType, 0);
    }



    private void addCatogory(String title){
        getTitles().add(title);
        getDatas().put(title,new ArrayList<Artical>());
    }

    public void addArticalList(List<Artical> list){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM月dd日");

		for (Artical artical : list) {
            if(newestArticalHref == null){
                newestArticalHref = artical.articalHref;
            }
			String date = dateFormat.format(new Date(artical.date));
			if(!datas.containsKey(date)){
				addCatogory(date);
			}
			datas.get(date).add(artical);
		}

		adapter.notifyDataSetChanged();

    }

    public void addNewArticals(ArrayList<Artical> articals) {

        for (int i = 0; i < articals.size(); i++) {
            if(articals.get(i).articalHref.equals(newestArticalHref)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM月dd日");
                List<Artical> list = articals.subList(0,i);
                for (int k = list.size()-1; k>-1 ; k--) {
                    Artical artical = list.get(k);
                    String date = dateFormat.format(new Date(artical.date));
                    if(!datas.containsKey(date)){
                        titles.add(0,date);
                        datas.put(date,new ArrayList<Artical>());
                    }
                    datas.get(date).add(0,artical);
                }

                break;
            }
        }
        newestArticalHref = articals.get(0).articalHref;

        adapter.notifyDataSetChanged();
    }
}
