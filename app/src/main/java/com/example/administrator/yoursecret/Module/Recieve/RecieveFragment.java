package com.example.administrator.yoursecret.Module.Recieve;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.AppManager.FoundationManager;
import com.example.administrator.yoursecret.Module.Detail.DetailActivity;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.Home.FragmentsHouse;
import com.example.administrator.yoursecret.Module.Recieve.Category.CategoryActivity;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.BaseRecyclerAdapter;
import com.example.administrator.yoursecret.utils.DividerItemDecoration;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.KV;
import com.example.administrator.yoursecret.utils.PermissionUtils;


public class RecieveFragment extends Fragment implements ReceiveObserver ,PermissionUtils.PermissionGrant{

    private RecyclerView recyclerView;

    private SwipeRefreshLayout refreshLayout;

    private Context context;

    private boolean once = false;

    public RecieveFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentsHouse.getInstance().putFragment(RecieveFragment.class.getSimpleName(),this);

        App.getInstance().getRecieveDataManager().setObserver(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recieve, container, false);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh);
        setupRefreshLayout();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recieve_recyclerview);
        setupRecyclerView();

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

        execOnce();

        if(FoundationManager.needToRefresh()) {
            refresh();
        }
    }

    private void refresh(){
        App.getInstance().getPermissionHouse().putPermissionGrant(PermissionUtils.CODE_REQUEST_LOCATION,this);

        PermissionUtils.checkPermission(getActivity(),PermissionUtils.CODE_REQUEST_LOCATION,this);

    }

    public void execOnce(){
        if(!once){
            App.getInstance().getRecieveDataManager().loadCache();

            once = true;
        }
    }

    private void setupRefreshLayout(){
        refreshLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void setupRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST,0,0,0,0));

        RecieveRecyclerAdapter adapter = App.getInstance().getRecieveDataManager().getAdapter();
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, DetailActivity.class);
                KV kv = App.getInstance().getRecieveDataManager().getAdapter().getLocation(position);
                Artical artical = App.getInstance().getRecieveDataManager().getArtical(kv);
                intent.putExtra(AppContants.KEY,artical);
                context.startActivity(intent);
            }
        });
        adapter.setOnTitleClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Intent intent = new Intent(context, CategoryActivity.class);
                KV kv = App.getInstance().getRecieveDataManager().getAdapter().getLocation(position);
                intent.putExtra(AppContants.KEY,kv);
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void removeLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(context,"无法连接到服务器或无法定位-_-",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showCorrectToast() {
        Toast.makeText(context,"消息已刷到最新^.^",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionGranted(int requestCoed) {
        refreshLayout.setRefreshing(true);
        App.getInstance().getRecieveDataManager().refresh();
    }
}

