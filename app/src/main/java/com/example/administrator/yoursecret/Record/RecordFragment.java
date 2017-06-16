package com.example.administrator.yoursecret.Record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Editor.EditorActivity;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;

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
        recordsView.setLayoutManager(new LinearLayoutManager(getContext()));
        final RecordsAdapter adapter = ApplicationDataManager.getInstance().getRecordDataManager().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra(AppContants.FROM_RECORD,adapter.getLocation(position));
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
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
