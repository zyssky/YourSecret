package com.example.administrator.yoursecret.Record;

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

import com.example.administrator.yoursecret.AppManager.AppDatabaseManager;
import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Comment.CommentActivity;
import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Editor.EditorActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.utils.ItemTouchCallback;
import com.example.administrator.yoursecret.utils.KV;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecordFragment extends Fragment{

    private Context context;

    private View rootView;

    private RecyclerView recordsView;

    public RecordFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_record, container, false);
        recordsView = (RecyclerView) rootView.findViewById(R.id.records_recyclerview);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        recordsView.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecordsAdapter adapter = ApplicationDataManager.getInstance().getRecordDataManager().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context,DetailActivity.class);
                KV kv= adapter.getLocation(position);
                Artical artical = ApplicationDataManager.getInstance().getRecordDataManager().getArtical(kv);
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
        ItemTouchCallback callback = new ItemTouchCallback(ApplicationDataManager.getInstance().getRecordDataManager().getOnSwipeListener());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recordsView);

        ApplicationDataManager.getInstance().getRecordDataManager().refresh();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.comments:
                Intent intent = new Intent(context, CommentActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
