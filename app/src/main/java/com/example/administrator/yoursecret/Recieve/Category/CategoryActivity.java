package com.example.administrator.yoursecret.Recieve.Category;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;
import com.example.administrator.yoursecret.utils.KV;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Activity activity = this;

        Bundle bundle = getIntent().getExtras();
        if(null!=bundle) {
            KV kv = bundle.getParcelable(AppContants.KEY);

            getSupportActionBar().setTitle(kv.key);

            CategoryDataManager.getInstance().setCategoryType(kv.key);
        }

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.category_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        recyclerView.addOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d("RecieveFragment", "onLoadMore: ");

            }
        });

        CategoryAdapter adapter = CategoryDataManager.getInstance().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(AppContants.COMMENT_POSITION,position);
                activity.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CategoryDataManager.onDestroy();
    }
}
