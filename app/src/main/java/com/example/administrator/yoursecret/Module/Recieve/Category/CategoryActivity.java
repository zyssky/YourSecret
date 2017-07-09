package com.example.administrator.yoursecret.Module.Recieve.Category;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.yoursecret.Module.Detail.DetailActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.KV;

public class CategoryActivity extends AppCompatActivity implements CategoryObserver{

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private View footerView;

    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.category_refresh);
        setupRefreshLayout();

        recyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);
        setupRecyclerView();

        CategoryDataManager.getInstance().setObserver(this);


        Bundle bundle = getIntent().getExtras();
        if(null!=bundle) {
            KV kv = bundle.getParcelable(AppContants.KEY);

            getSupportActionBar().setTitle(kv.key);

            CategoryDataManager.getInstance().setCategoryType(kv.key);

            setDateColor(kv.key);

            CategoryDataManager.getInstance().loadMore();
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
    }

    private void setupRefreshLayout() {
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CategoryDataManager.getInstance().refresh();
            }
        });
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


    public View getFooterView(){
        footerView = getLayoutInflater().inflate(R.layout.footer_more_article,recyclerView,false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDataManager.getInstance().loadMore();
            }
        });
        return footerView;
    }

    private final static int NORMAL = 0;
    private final static int LOADING = 1;
    private void setFooter(int status){
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

    @Override
    public void removeLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void removeFooterLoading() {
        setFooter(NORMAL);
    }

    @Override
    public void setFooterLoading() {
        setFooter(LOADING);
    }

    @Override
    public void showNoMsgToast() {
        Toast.makeText(activity,"没有更多内容了^_^",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNewestToast() {
        Toast.makeText(activity,"消息已刷到最新^.^",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(activity,"无法连接到服务器或无法定位-_-",Toast.LENGTH_SHORT).show();
    }
}
