package com.example.administrator.yoursecret.Record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yoursecret.R;

public class RecordFragment extends Fragment implements RecordContract.View{

    private View rootView;
    private RecyclerView recordsView;

    private RecordContract.Presenter presenter;

    public RecordFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RecordPresenter(this);
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
        presenter.setRecyclerViewAdapter();
        recordsView.addItemDecoration(new SpaceItemDecoration(10,10,10,50));
    }

    @Override
    public void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        recordsView.setAdapter(adapter);
    }
}
