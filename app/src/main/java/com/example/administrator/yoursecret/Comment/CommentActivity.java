package com.example.administrator.yoursecret.Comment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;

public class CommentActivity extends AppCompatActivity implements CommentObserver{
    private RecyclerView recyclerView;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        view = findViewById(R.id.default_picture);

        recyclerView = (RecyclerView) findViewById(R.id.user_comments);
        setupRecyclerView();

        CommentDataManager.getInstance().setObserver(this);
        CommentDataManager.getInstance().loadComments();

        App.getInstance().getUserManager().sethasUnReadMessage(false);
    }

    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        CommentsAdapter adapter = CommentDataManager.getInstance().getAdapter();
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommentDataManager.onDestroy();
    }

    @Override
    public void showNoCommentHint() {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoCommentHint() {
        view.setVisibility(View.GONE);
    }
}

