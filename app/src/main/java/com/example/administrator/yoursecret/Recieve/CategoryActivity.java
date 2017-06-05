package com.example.administrator.yoursecret.Recieve;

import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.RecieveRecyclerAdapter;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int position = getIntent().getIntExtra(AppContants.POSITION,-1);
        if(position>=0) {
            Object object = RecieveRecyclerAdapter.getInstance().getDataAt(position);
            if(object instanceof String)
                getSupportActionBar().setTitle((String)object);
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

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setDatas(RecieveData.getInstance().getDatas(),RecieveData.getInstance().getTitles());
        recyclerView.setAdapter(categoryAdapter);
    }
}
