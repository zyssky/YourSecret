package com.example.administrator.yoursecret.Recieve;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.yoursecret.AppManager.ApplicationDataManager;
import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Home.FragmentsHouse;
import com.example.administrator.yoursecret.Recieve.Category.CategoryActivity;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;
import com.example.administrator.yoursecret.utils.KV;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RecieveFragment extends Fragment {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

//    private View footerView;

    private View rootView;

    private Context context;

    private LinearLayoutManager linearLayoutManager;



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
        FragmentsHouse.getInstance().putFragment(RecieveFragment.class.getSimpleName(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_recieve, container, false);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recieve_recyclerview);

        refreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2017/5/27 get new items
                ApplicationDataManager.getInstance().getRecieveDataManager().refresh();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST,0,0,0,0));

        RecieveRecyclerAdapter adapter = ApplicationDataManager.getInstance().getRecieveDataManager().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, DetailActivity.class);
                KV kv = ApplicationDataManager.getInstance().getRecieveDataManager().getAdapter().getLocation(position);
                Artical artical = ApplicationDataManager.getInstance().getRecieveDataManager().getArtical(kv);
                intent.putExtra(AppContants.KEY,artical);
                context.startActivity(intent);
            }
        });
        adapter.setOnTitleClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, CategoryActivity.class);
                KV kv = ApplicationDataManager.getInstance().getRecieveDataManager().getAdapter().getLocation(position);
                intent.putExtra(AppContants.KEY,kv);
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        ApplicationDataManager.getInstance().getRecieveDataManager().setListener(new OnRefreshChangeListener() {
            @Override
            public void changeRefreshStatus(boolean[] status) {
                refreshLayout.setRefreshing(status[0]);
            }

        });

        ApplicationDataManager.getInstance().getRecieveDataManager().refresh();

    }

}

