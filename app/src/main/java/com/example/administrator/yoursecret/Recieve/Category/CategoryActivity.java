package com.example.administrator.yoursecret.Recieve.Category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Detail.DetailDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.OnRefreshChangeListener;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private View footerView;

    private LinearLayoutManager linearLayoutManager;

    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.category_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));


        CategoryAdapter adapter = CategoryDataManager.getInstance().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(activity, DetailActivity.class);
                KV kv = CategoryDataManager.getInstance().getAdapter().getLocation(position);
                Artical artical = CategoryDataManager.getInstance().getArtical(kv);
                intent.putExtra(AppContants.KEY,artical);
                activity.startActivity(intent);
            }
        });

        getFooterView();

        adapter.addFooter(footerView);

        recyclerView.setAdapter(adapter);

        CategoryDataManager.getInstance().setListener(new OnRefreshChangeListener() {
            @Override
            public void changeRefreshStatus(boolean[] status) {
                refreshLayout.setRefreshing(status[0]);
            }
        });

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
                        refreshLayout.setRefreshing(false);

                    }

                    @Override
                    public void onComplete() {
                        refreshLayout.setRefreshing(false);
                    }
                };

                CategoryDataManager.getInstance().refresh(observer);
            }
        });




        Bundle bundle = getIntent().getExtras();
        if(null!=bundle) {
            KV kv = bundle.getParcelable(AppContants.KEY);

            getSupportActionBar().setTitle(kv.key);

            CategoryDataManager.getInstance().setCategoryType(kv.key);

            setDateColor(kv.key);

            CategoryDataManager.getInstance().loadMore(getArticalObserver());
        }
    }

    private void setDateColor(String title){
        if(title.equals(AppContants.ARTICAL_CATOGORY_HOT)){
            CategoryDataManager.getInstance().setDateBackgroungColor(getResources().getColor(R.color.HOT));
        }
        if(title.equals(AppContants.ARTICAL_CATOGORY_PUSH)){
            CategoryDataManager.getInstance().setDateBackgroungColor(getResources().getColor(R.color.PUSH));
        }
        if(title.equals(AppContants.ARTICAL_CATOGORY_GOOD)){
            CategoryDataManager.getInstance().setDateBackgroungColor(getResources().getColor(R.color.ARTICLE));
        }
        if(title.equals(AppContants.ARTICAL_CATOGORY_OUTSIDE)){
            CategoryDataManager.getInstance().setDateBackgroungColor(getResources().getColor(R.color.OUTSIDE));
        }
    }

    public Observer<ArrayList<Artical>> getArticalObserver(){
        Observer<ArrayList<Artical>> observer = new Observer<ArrayList<Artical>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                setFooter(LOADING);
            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> list) {
                CategoryDataManager.getInstance().addArticalList(list);
                if(list.isEmpty()){
                    Toast.makeText(activity,"没有更多内容了^.^",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                setFooter(NORMAL);
            }

            @Override
            public void onComplete() {
                setFooter(NORMAL);
            }
        };
        return observer;
    }

    public View getFooterView(){
        footerView = getLayoutInflater().inflate(R.layout.footer_more_article,recyclerView,false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDataManager.getInstance().loadMore(getArticalObserver());
            }
        });
        return footerView;
    }

    public final static int NORMAL = 0;
    public final static int LOADING = 1;
    public void setFooter(int status){
        switch (status){
            case NORMAL:
                footerView.findViewById(R.id.normal).setVisibility(View.VISIBLE);
                footerView.findViewById(R.id.loading).setVisibility(View.GONE);
                footerView.setClickable(true);
                break;
            case LOADING:
                footerView.findViewById(R.id.normal).setVisibility(View.GONE);
                footerView.findViewById(R.id.loading).setVisibility(View.VISIBLE);
                footerView.setClickable(false);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CategoryDataManager.onDestroy();
    }
}
