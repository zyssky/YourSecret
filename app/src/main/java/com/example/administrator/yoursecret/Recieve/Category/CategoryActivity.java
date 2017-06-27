package com.example.administrator.yoursecret.Recieve.Category;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Detail.DetailDataManager;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.OnRefreshChangeListener;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;
import com.example.administrator.yoursecret.utils.KV;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private View footerView;

    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Activity activity = this;


        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.category_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));

        recyclerView.addOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                Log.d("RecieveFragment", "onLoadMore: ");
                if(!CategoryDataManager.getInstance().isLoading()) {
                    CategoryDataManager.getInstance().setLoading(true);
                    CategoryDataManager.getInstance().loadMore();
                }
            }


        });


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

        footerView = getLayoutInflater().inflate(R.layout.footer_loading,recyclerView,false);
        adapter.addFooter(footerView);
        recyclerView.setAdapter(adapter);

        CategoryDataManager.getInstance().setListener(new OnRefreshChangeListener() {
            @Override
            public void changeRefreshStatus(boolean[] status) {
                refreshLayout.setRefreshing(status[0]);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });




        Bundle bundle = getIntent().getExtras();
        if(null!=bundle) {
            KV kv = bundle.getParcelable(AppContants.KEY);

            getSupportActionBar().setTitle(kv.key);

            CategoryDataManager.getInstance().setCategoryType(kv.key);
            CategoryDataManager.getInstance().loadMore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CategoryDataManager.onDestroy();
    }
}
