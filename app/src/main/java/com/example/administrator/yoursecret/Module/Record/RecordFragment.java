package com.example.administrator.yoursecret.Module.Record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Module.Comment.CommentActivity;
import com.example.administrator.yoursecret.Module.Detail.DetailActivity;
import com.example.administrator.yoursecret.Module.Editor.EditorActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Home.FragmentsHouse;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.ItemTouchCallback;
import com.example.administrator.yoursecret.utils.KV;

public class RecordFragment extends Fragment implements RecordObserver{

    private Context context;

    private RecyclerView recordsView;

    private MenuItem commentMsg;


    public RecordFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        FragmentsHouse.getInstance().putFragment(RecordFragment.class.getSimpleName(),this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);

        recordsView = (RecyclerView) rootView.findViewById(R.id.records_recyclerview);
        setupRecordsView();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        App.getInstance().getRecordDataManager().setObserver(this);

        App.getInstance().getRecordDataManager().refresh();
    }

    private void setupRecordsView(){
        recordsView.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecordsAdapter adapter = App.getInstance().getRecordDataManager().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context,DetailActivity.class);
                KV kv= adapter.getLocation(position);
                Artical artical = App.getInstance().getRecordDataManager().getArtical(kv);
                intent.putExtra(AppContants.KEY,artical);
                context.startActivity(intent);
            }
        });

        adapter.setOnTempItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context,EditorActivity.class);
                intent.putExtra(AppContants.FROM_RECORD,adapter.getLocation(position));
                context.startActivity(intent);
            }
        });

        recordsView.setAdapter(adapter);

        recordsView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));
        ItemTouchCallback callback = new ItemTouchCallback(App.getInstance().getRecordDataManager().getOnSwipeListener());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recordsView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.record_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);

        commentMsg = menu.findItem(R.id.comments);

        App.getInstance().getRecordDataManager().checkNewComment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.comments:
                notifyNoMsg();
                Intent intent = new Intent(context, CommentActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void notifyNoMsg() {
        commentMsg.setIcon(R.drawable.ic_comment_1);
    }

    @Override
    public void notifyNewMsg() {
        commentMsg.setIcon(R.drawable.ic_new_comment_1);
    }
}
