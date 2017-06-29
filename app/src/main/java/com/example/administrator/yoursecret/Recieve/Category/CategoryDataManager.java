package com.example.administrator.yoursecret.Recieve.Category;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Recieve.OnRefreshChangeListener;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.KV;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
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

    private OnRefreshChangeListener listener;

    private String newestArticalHref;

    public void setListener(OnRefreshChangeListener listener){
        this.listener = listener;
    }

    public void setCategoryType(String categoryType){
        this.categoryType = categoryType;
        List<Artical> list = ApplicationDataManager.getInstance().getRecieveDataManager().getDatas().get(categoryType);
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

    public void loadMore(Observer<ArrayList<Artical>> observer){
        ApplicationDataManager.getInstance().getNetworkManager().getArticalsOnType(observer,categoryType, pageNo++);
    }

    public void refresh(Observer<ArrayList<Artical>> observer){
        ApplicationDataManager.getInstance().getNetworkManager().getArticalsOnType(observer,categoryType, 0);
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
    }

    private boolean loading = false;

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean status){
        loading = status;
    }

    public void addNewArticals(ArrayList<Artical> articals) {

        for (int i = 0; i < articals.size(); i++) {
            if(articals.get(i).articalHref.equals(newestArticalHref)){
                addArticalList(articals.subList(0,i));
                break;
            }
        }
        newestArticalHref = articals.get(0).articalHref;
    }
}
