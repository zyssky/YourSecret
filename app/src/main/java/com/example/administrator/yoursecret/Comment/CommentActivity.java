package com.example.administrator.yoursecret.Comment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Entity.Comment;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = (RecyclerView) findViewById(R.id.user_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        CommentsAdapter adapter = CommentDataManager.getInstance().getAdapter();
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);
        CommentDataManager.getInstance().loadComments();
    }
}
