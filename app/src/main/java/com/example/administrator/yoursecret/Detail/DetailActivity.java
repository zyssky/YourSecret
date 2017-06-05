package com.example.administrator.yoursecret.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Recieve.RecieveRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.SpaceItemDecoration;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private RecyclerView recyclerView;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

//        header = LayoutInflater.from(this).inflate(R.layout.content_detail,null);



        recyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        recyclerView.setNestedScrollingEnabled(false);


        DetailPresenter.setView(this);
        DetailPresenter.getInstance().setCommentAdapter();

//        setContentView(header);
    }

    @Override
    public void setCommentAdapter(RecyclerView.Adapter adapter) {

        if(adapter instanceof CommentRecyclerAdapter) {
            CommentRecyclerAdapter commentRecyclerAdapter = (CommentRecyclerAdapter) adapter;
            commentRecyclerAdapter.setHeaderView(header);
            commentRecyclerAdapter.setScaleHeaderView(true);
            recyclerView.setAdapter(commentRecyclerAdapter);
        }
        else{
            recyclerView.setAdapter(adapter);
        }
    }
}
