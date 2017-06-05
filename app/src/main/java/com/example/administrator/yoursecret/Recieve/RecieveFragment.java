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

import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.EndlessOnScrollListener;


public class RecieveFragment extends Fragment implements RecieveContract.View {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

//    private View footerView;

    private View rootView;

    private RecieveContract.Presenter presenter;

    private Context context;


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
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recieve_recyclerview);

//        refreshLayout.setColorSchemeColors(Color.GREEN,Color.RED,Color.YELLOW,Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2017/5/27 get new items
                refreshLayout.setRefreshing(false);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(10,10,10,10));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST,0,0,0,0));
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_message,null);
//        footerView = LayoutInflater.from(getContext()).inflate(R.layout.footer_loading,null);

//        refreshLayout.setRefreshing(true);

        recyclerView.addOnScrollListener(new EndlessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d("RecieveFragment", "onLoadMore: ");

            }
        });


        presenter.setRecyclerViewAdapter();
//        ((RecieveRecyclerAdapter)recyclerView.getAdapter()).setHeaderView(headerView);
//        ((RecieveRecyclerAdapter)recyclerView.getAdapter()).setFooterView(footerView);
    }

    @Override
    public void setRecyclerViewAdapter() {
        RecieveRecyclerAdapter recieveRecyclerAdapter = RecieveRecyclerAdapter.getInstance();
        recyclerView.setAdapter(recieveRecyclerAdapter);
        recieveRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(AppContants.COMMENT_POSITION,position);
                context.startActivity(intent);
            }
        });

        recieveRecyclerAdapter.setOnTitleClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.putExtra(AppContants.POSITION,position);
                context.startActivity(intent);
            }
        });
    }
}
