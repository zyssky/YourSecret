package com.example.administrator.yoursecret.Recieve;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.R;


public class RecieveFragment extends Fragment implements RecieveContract.View {

    private RecyclerView recyclerView;

    private View rootView;

    private RecieveContract.Presenter presenter;


    public RecieveFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecieveFragment newInstance() {
        RecieveFragment fragment = new RecieveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RecievePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recieve, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recieve_recyclerview);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new SpaceItemDecoration(10,10,10,10));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_message,null);


        presenter.setRecyclerViewAdapter();
        ((RecieveRecyclerAdapter)recyclerView.getAdapter()).setHeaderView(headerView);
    }

    @Override
    public void setRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);

    }
}
