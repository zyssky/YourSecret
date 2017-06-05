package com.example.administrator.yoursecret.Recieve;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yoursecret.MetaData.PushMessage;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.MultiRecyclerAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/16.
 */

public class RecieveRecyclerAdapter extends MultiRecyclerAdapter<PushMessage> {

    private static RecieveRecyclerAdapter instance;
    private RecieveRecyclerAdapter(){};
    public static void init(Map<String,List<PushMessage>> datas , List<String> titles){
        instance = new RecieveRecyclerAdapter();
        instance.setDatas(datas,titles);
    }
    public static RecieveRecyclerAdapter getInstance(){
        return instance;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_title,parent,false);
        return new RecieveRecyclerAdapter.TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message2,parent,false);
        return new RecieveRecyclerAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindHeader(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindFooter(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onBindTitle(RecyclerView.ViewHolder holder, String title) {
        if(holder instanceof RecieveRecyclerAdapter.TitleViewHolder){
            RecieveRecyclerAdapter.TitleViewHolder titleViewHolder = (RecieveRecyclerAdapter.TitleViewHolder) holder;
            if(title.equals("热点")){
                titleViewHolder.title_divider.setVisibility(View.GONE);
            }
            else {
                titleViewHolder.title_divider.setVisibility(View.VISIBLE);
            }
            titleViewHolder.textView.setText(title);
        }
    }

    @Override
    public void onBindItem(RecyclerView.ViewHolder holder, PushMessage data) {
        if(holder instanceof RecieveRecyclerAdapter.ItemViewHolder){
            RecieveRecyclerAdapter.ItemViewHolder itemViewHolder = (RecieveRecyclerAdapter.ItemViewHolder) holder;

        }
    }


    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView textView ;
        View title_divider;
        public TitleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.record_title);
            title_divider = itemView.findViewById(R.id.title_divider);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
